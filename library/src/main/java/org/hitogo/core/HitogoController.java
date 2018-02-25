package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.alert.dialog.DialogAlertImpl;
import org.hitogo.alert.dialog.DialogAlertParams;
import org.hitogo.alert.popup.PopupAlertImpl;
import org.hitogo.alert.popup.PopupAlertParams;
import org.hitogo.alert.snackbar.SnackbarAlertImpl;
import org.hitogo.alert.snackbar.SnackbarAlertParams;
import org.hitogo.alert.toast.ToastAlertImpl;
import org.hitogo.alert.toast.ToastAlertParams;
import org.hitogo.alert.view.ViewAlertImpl;
import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.button.close.CloseButtonParams;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonType;
import org.hitogo.button.text.TextButtonImpl;
import org.hitogo.button.text.TextButtonParams;
import org.hitogo.button.view.ViewButtonImpl;
import org.hitogo.button.view.ViewButtonParams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings({"unchecked"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();

    private final LinkedList<AlertImpl> currentViews = new LinkedList<>();
    private final LinkedList<AlertImpl> currentDialogs = new LinkedList<>();
    private final LinkedList<AlertImpl> currentPopups = new LinkedList<>();
    private final LinkedList<AlertImpl> currentOthers = new LinkedList<>();

    private final List<AlertImpl> currentActiveViews = new ArrayList<>();
    private final List<AlertImpl> currentActiveDialogs = new ArrayList<>();
    private final List<AlertImpl> currentActivePopups = new ArrayList<>();
    private final List<AlertImpl> currentActiveOthers = new ArrayList<>();

    private final SparseIntArray alertCountMap = new SparseIntArray();

    private int currentHighestPriorityForViews = Integer.MAX_VALUE;
    private int currentHighestPriorityForDialogs = Integer.MAX_VALUE;
    private int currentHighestPriorityForPopups = Integer.MAX_VALUE;
    private int currentHighestPriorityForOthers = Integer.MAX_VALUE;

    public HitogoController(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    public void show(AlertImpl hitogo, boolean force) {
        show(hitogo, force, false);
    }

    public void show(AlertImpl alert, boolean force, boolean showLater) {
        synchronized (syncLock) {
            internalShow(getCurrentAlertList(alert.getAlertType()), alert, force, showLater);
        }
    }

    public void showNext(final Alert alert) {
        showNext(alert, false);
    }

    public void showNext(final Alert alert, final boolean force) {
        synchronized (syncLock) {
            if (!alert.isClosing()) {
                closeByAlert(alert);
            }

            final LinkedList<AlertImpl> currentAlerts = getCurrentAlertList(alert.getAlertType());
            if (!currentAlerts.isEmpty()) {
                searchForNextInvisibleAlert(currentAlerts, force, alert.getAnimationDuration());
            }
        }
    }

    public void showNext(final AlertType alertType) {
        showNext(alertType, false);
    }

    public void showNext(final AlertType alertType, final boolean force) {
        synchronized (syncLock) {
            final LinkedList<AlertImpl> currentAlerts = getCurrentAlertList(alertType);
            if (!currentAlerts.isEmpty()) {
                searchForNextInvisibleAlert(currentAlerts, force, 0);
            }
        }
    }

    protected void searchForNextInvisibleAlert(final LinkedList<AlertImpl> currentAlerts,
                                               final boolean force, final long wait) {
        int highestIncludingPriority = getCurrentHighestPriority(currentAlerts);
        for (AlertImpl alert : currentAlerts) {
            if (!isAlertAttached(alert) && (!alert.hasPriority() || alert.getPriority() <= highestIncludingPriority)) {
                if (alert.hasPriority()) {
                    setCurrentHighestPriority(alert.getAlertType(), alert.getPriority());
                }
                makeAlertVisible(alert, force, wait);
                break;
            }
        }
    }

    @NonNull
    protected LinkedList<AlertImpl> getCurrentAlertList(AlertType type) {
        switch (type) {
            case VIEW:
                return currentViews;
            case DIALOG:
                return currentDialogs;
            case POPUP:
                return currentPopups;
            case OTHER:
                return currentOthers;
            default:
                return new LinkedList<>();
        }
    }

    @NonNull
    protected List<AlertImpl> getCurrentActiveList(AlertType type) {
        switch (type) {
            case VIEW:
                return currentActiveViews;
            case DIALOG:
                return currentActiveDialogs;
            case POPUP:
                return currentActivePopups;
            case OTHER:
                return currentActiveOthers;
            default:
                return new LinkedList<>();
        }
    }

    protected int getCurrentHighestPriority(AlertType type) {
        switch (type) {
            case VIEW:
                return currentHighestPriorityForViews;
            case DIALOG:
                return currentHighestPriorityForDialogs;
            case POPUP:
                return currentHighestPriorityForPopups;
            case OTHER:
                return currentHighestPriorityForOthers;
            default:
                return Integer.MAX_VALUE;
        }
    }

    protected void setCurrentHighestPriority(AlertType type, int newPriority) {
        switch (type) {
            case VIEW:
                currentHighestPriorityForViews = newPriority;
                break;
            case DIALOG:
                currentHighestPriorityForDialogs = newPriority;
                break;
            case POPUP:
                currentHighestPriorityForPopups = newPriority;
                break;
            case OTHER:
                currentHighestPriorityForOthers = newPriority;
                break;
            default:
                break;
        }
    }

    protected void internalShow(final LinkedList<AlertImpl> currentObjects, final AlertImpl newAlert,
                                final boolean force, final boolean showLater) {
        currentObjects.addLast(newAlert);

        if (showLater) {
            return;
        }

        List<AlertImpl> currentActiveObjects = getCurrentActiveList(newAlert.getAlertType());
        long waitForClosing = 0;

        if (newAlert.hasPriority()) {
            int newAlertPrio = newAlert.getPriority();
            if (newAlertPrio < getCurrentHighestPriority(newAlert.getAlertType())) {
                setCurrentHighestPriority(newAlert.getAlertType(), newAlertPrio);
                waitForClosing = closeByType(newAlert.getAlertType(), force);
            } else {
                return;
            }
        } else if (newAlert.isClosingOthers() && !currentActiveObjects.isEmpty()) {
            long waitByType = closeByType(newAlert.getAlertType(), force);
            if (waitByType > waitForClosing) {
                waitForClosing = waitByType;
            }
        }

        makeAlertVisible(newAlert, force, waitForClosing);
    }

    protected void makeAlertVisible(final AlertImpl alert, final boolean force, final long wait) {
        if (!force) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (alert.getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED) &&
                            (!alert.hasPriority() || alert.getPriority() <= getCurrentHighestPriority(alert.getAlertType()))) {
                        internalMakeVisible(alert, false);
                    }
                }
            }, wait);
        } else {
            internalMakeVisible(alert, true);
        }
    }

    protected int getCurrentHighestPriority(LinkedList<AlertImpl> currentObjects) {
        int highestIncludingPriority = Integer.MAX_VALUE;
        for (AlertImpl alert : currentObjects) {
            if (alert.hasPriority() && alert.getPriority() <= highestIncludingPriority) {
                highestIncludingPriority = alert.getPriority();
            }
        }
        return highestIncludingPriority;
    }

    public long closeAll() {
        return closeAll(false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected long closeOnDestroy() {
        return closeByType(AlertType.DIALOG, true);
    }

    public long closeAll(boolean force) {
        synchronized (syncLock) {
            long longestClosingAnim = 0;
            internalCloseAll(currentViews.iterator(), force, longestClosingAnim);
            internalCloseAll(currentDialogs.iterator(), force, longestClosingAnim);
            internalCloseAll(currentPopups.iterator(), force, longestClosingAnim);
            internalCloseAll(currentOthers.iterator(), force, longestClosingAnim);
            return longestClosingAnim;
        }
    }

    protected void internalCloseAll(Iterator<AlertImpl> it, boolean force, long currentLongest) {
        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && object.isAttached()) {
                if (object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                internalMakeInvisible(object, force);
                it.remove();
            }
        }
    }

    public long closeByType(@NonNull AlertType type) {
        return closeByType(type, false);
    }

    public long closeByType(@NonNull AlertType type, boolean force) {
        synchronized (syncLock) {
            return internalCloseByType(getCurrentAlertList(type).iterator(), type, force);
        }
    }

    private long internalCloseByType(Iterator<AlertImpl> it, @NonNull AlertType type, boolean force) {
        long longestClosingAnim = 0;

        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && type == object.getAlertType() && object.isAttached()) {
                if (object.getAnimationDuration() > longestClosingAnim) {
                    longestClosingAnim = object.getAnimationDuration();
                }

                internalMakeInvisible(object, force);
                it.remove();
            }
        }

        return longestClosingAnim;
    }

    public long closeByTag(@NonNull String tag) {
        return closeByTag(tag, false);
    }

    public long closeByTag(@NonNull String tag, boolean force) {
        synchronized (syncLock) {
            long longestClosingAnim = 0;
            internalCloseByTag(currentViews.iterator(), tag, force, longestClosingAnim);
            internalCloseByTag(currentDialogs.iterator(), tag, force, longestClosingAnim);
            internalCloseByTag(currentPopups.iterator(), tag, force, longestClosingAnim);
            internalCloseByTag(currentOthers.iterator(), tag, force, longestClosingAnim);
            return longestClosingAnim;
        }
    }

    private void internalCloseByTag(Iterator<AlertImpl> it, @NonNull String tag, boolean force, long currentLongest) {
        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && object.isAttached() && tag.equals(object.getTag())) {
                if (object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                internalMakeInvisible(object, force);
                it.remove();
            }
        }
    }

    public long closeByState(Enum state) {
        return closeByState(state.ordinal(), false);
    }

    public long closeByState(Enum state, boolean force) {
        return closeByState(state.ordinal(), force);
    }

    public long closeByState(int state) {
        return closeByState(state, false);
    }

    public long closeByState(int state, boolean force) {
        synchronized (syncLock) {
            long longestClosingAnim = 0;
            internalCloseByState(currentViews.iterator(), state, force, longestClosingAnim);
            internalCloseByState(currentDialogs.iterator(), state, force, longestClosingAnim);
            internalCloseByState(currentPopups.iterator(), state, force, longestClosingAnim);
            internalCloseByState(currentOthers.iterator(), state, force, longestClosingAnim);
            return longestClosingAnim;
        }
    }

    private void internalCloseByState(Iterator<AlertImpl> it, int state, boolean force, long currentLongest) {
        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && object.isAttached() && object.getState() == state) {
                if (object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                internalMakeInvisible(object, force);
                it.remove();
            }
        }
    }

    public long closeByAlert(@NonNull Alert alert) {
        return closeByAlert(alert, false);
    }

    public long closeByAlert(@NonNull Alert alert, boolean force) {
        synchronized (syncLock) {
            return internalCloseByAlert(getCurrentAlertList(alert.getAlertType()).iterator(), alert, force);
        }
    }

    private long internalCloseByAlert(Iterator<AlertImpl> it, final @NonNull Alert alert, boolean force) {
        long longestClosingAnim = 0;

        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object.equals(alert)) {
                if (object.getAnimationDuration() > longestClosingAnim) {
                    longestClosingAnim = object.getAnimationDuration();
                }

                internalMakeInvisible((AlertImpl) alert, force);
                it.remove();
            }
        }

        return longestClosingAnim;
    }

    private void internalMakeVisible(AlertImpl object, boolean force) {
        int count = alertCountMap.get(object.hashCode());
        if (count == 0) {
            alertCountMap.put(object.hashCode(), 1);
            object.makeVisible(force);
            getCurrentActiveList(object.getAlertType()).add(object);
        } else {
            alertCountMap.put(object.hashCode(), count + 1);
        }
    }

    private void internalMakeInvisible(AlertImpl object, boolean force) {
        int count = alertCountMap.get(object.hashCode());
        if (count == 1) {
            alertCountMap.delete(object.hashCode());
            internalMakeActiveAlertInvisible(object, force);
        } else {
            alertCountMap.put(object.hashCode(), count - 1);
        }
    }

    private void internalMakeActiveAlertInvisible(AlertImpl toBeClosedAlert, final boolean force) {
        Iterator<AlertImpl> iterator = getCurrentActiveList(toBeClosedAlert.getAlertType()).iterator();

        while (iterator.hasNext()) {
            AlertImpl alert = iterator.next();

            if (toBeClosedAlert.equals(alert)) {
                if (alert.hasPriority()) {
                    setCurrentHighestPriority(alert.getAlertType(), Integer.MAX_VALUE);
                }

                alert.makeInvisible(force);
                iterator.remove();
                break;
            }
        }
    }

    private boolean isAlertAttached(AlertImpl alert) {
        return alertCountMap.get(alert.hashCode()) != 0;
    }

    @NonNull
    public Class<? extends AlertImpl> provideDefaultViewClass() {
        return ViewAlertImpl.class;
    }

    @NonNull
    public Class<? extends AlertParams> provideDefaultViewParamsClass() {
        return ViewAlertParams.class;
    }

    @NonNull
    public Class<? extends AlertImpl> provideDefaultDialogClass() {
        return DialogAlertImpl.class;
    }

    @NonNull
    public Class<? extends AlertParams> provideDefaultDialogParamsClass() {
        return DialogAlertParams.class;
    }

    @NonNull
    public Class<? extends AlertImpl> provideDefaultPopupClass() {
        return PopupAlertImpl.class;
    }

    @NonNull
    public Class<? extends AlertParams> provideDefaultPopupParamsClass() {
        return PopupAlertParams.class;
    }

    @NonNull
    public Class<? extends ButtonImpl> provideDefaultViewButtonClass() {
        return ViewButtonImpl.class;
    }

    @NonNull
    public Class<? extends ButtonParams> provideDefaultViewButtonParamsClass() {
        return ViewButtonParams.class;
    }

    @NonNull
    public Class<? extends ButtonImpl> provideDefaultCloseButtonClass() {
        return ViewButtonImpl.class;
    }

    @NonNull
    public Class<? extends ButtonParams> provideDefaultCloseButtonParamsClass() {
        return CloseButtonParams.class;
    }

    @NonNull
    public Class<? extends ButtonImpl> provideDefaultTextButtonClass() {
        return TextButtonImpl.class;
    }

    @NonNull
    public Class<? extends ButtonParams> provideDefaultTextButtonParamsClass() {
        return TextButtonParams.class;
    }

    @NonNull
    public Class<? extends AlertImpl> provideDefaultSnackbarClass() {
        return SnackbarAlertImpl.class;
    }

    @NonNull
    public Class<? extends AlertParams> provideDefaultSnackbarParamsClass() {
        return SnackbarAlertParams.class;
    }

    @NonNull
    public Class<? extends AlertImpl> provideDefaultToastClass() {
        return ToastAlertImpl.class;
    }

    @NonNull
    public Class<? extends AlertParams> provideDefaultToastParamsClass() {
        return ToastAlertParams.class;
    }

    @NonNull
    public <T extends HitogoParamsHolder> T provideAlertParamsHolder(AlertType alertType) {
        return (T) new HitogoParamsHolder();
    }

    @NonNull
    public <T extends HitogoParamsHolder> T provideButtonParamsHolder(ButtonType buttonType) {
        return (T) new HitogoParamsHolder();
    }

    @LayoutRes
    @Nullable
    public Integer provideViewLayout(int state) {
        return null;
    }

    @LayoutRes
    @Nullable
    public Integer providePopupLayout(int state) {
        return null;
    }

    @LayoutRes
    @Nullable
    public Integer provideDialogLayout(int state) {
        return null;
    }

    @LayoutRes
    @Nullable
    public Integer provideOtherLayout(int state) {
        return null;
    }

    @Nullable
    public Integer provideDefaultViewAlertLayoutContainerId() {
        return null;
    }

    @Nullable
    public Integer provideDefaultViewAlertOverlayContainerId() {
        return null;
    }

    @Nullable
    public Integer provideDefaultAlertState(AlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultAlertTextViewId(AlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultAlertTitleViewId(AlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultAlertDrawableViewId(AlertType type) {
        return null;
    }

    @Nullable
    public <T extends HitogoAnimation> T provideDefaultAlertAnimation(AlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultAlertAnimationLayoutViewId(AlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultButtonCloseIconId(ButtonType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultButtonCloseClickId(ButtonType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultButtonTextViewId(ButtonType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultButtonDrawableViewId(ButtonType type) {
        return null;
    }

    @NonNull
    public <T extends HitogoHelper> T provideHelper() {
        return (T) new HitogoHelper();
    }

    @NonNull
    public <T extends HitogoAccessor> T provideAccessor() {
        return (T) new HitogoAccessor();
    }

    public abstract boolean provideIsDebugState();
}
