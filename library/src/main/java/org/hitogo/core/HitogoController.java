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
import org.hitogo.alert.dialog.DialogAlertImpl;
import org.hitogo.alert.popup.PopupAlertImpl;
import org.hitogo.alert.popup.PopupAlertParams;
import org.hitogo.alert.snackbar.SnackbarAlertImpl;
import org.hitogo.alert.snackbar.SnackbarAlertParams;
import org.hitogo.alert.toast.ToastAlertImpl;
import org.hitogo.alert.toast.ToastAlertParams;
import org.hitogo.alert.view.ViewAlertImpl;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.button.action.ActionButtonImpl;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.action.ActionButtonParams;
import org.hitogo.alert.dialog.DialogAlertParams;
import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonType;
import org.hitogo.button.simple.SimpleButtonImpl;
import org.hitogo.button.simple.SimpleButtonParams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();

    private final LinkedList<AlertImpl> currentViews = new LinkedList<>();
    private final LinkedList<AlertImpl> currentDialogs = new LinkedList<>();
    private final LinkedList<AlertImpl> currentPopups = new LinkedList<>();
    private final LinkedList<AlertImpl> currentOthers = new LinkedList<>();

    private final SparseIntArray alertCountMap = new SparseIntArray();
    private final List<AlertImpl> currentActiveAlerts = new ArrayList<>();

    public HitogoController(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    public void show(AlertImpl hitogo, boolean force) {
        show(hitogo, force, false);
    }

    public void show(AlertImpl alert, boolean force, boolean showLater) {
        synchronized (syncLock) {
            internalShow(getCurrentAlertList(alert.getType()), alert, force, showLater);
        }
    }

    public void showNext(final Alert alert, final boolean force) {
        synchronized (syncLock) {
            if (!alert.isClosing()) {
                closeByAlert(alert);
            }

            final LinkedList<AlertImpl> currentAlerts = getCurrentAlertList(alert.getType());
            if (!currentAlerts.isEmpty()) {
                searchForNextInvisibleAlert(currentAlerts, force, alert.getAnimationDuration());
            }
        }
    }

    protected void searchForNextInvisibleAlert(final LinkedList<AlertImpl> currentAlerts,
                                               final boolean force, final long wait) {
        HitogoPrioritySubClass priorityObject = getCurrentHighestPriority(currentAlerts);
        for (AlertImpl alert : currentAlerts) {
            if (alert.hasPriority() && !isAlertAttached(alert)) {
                if (priorityObject.isAlertVisibilityAllowed(alert.hashCode())) {
                    makeAlertVisible(alert, force, wait);
                    break;
                }
            } else if (!isAlertAttached(alert)) {
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

    protected void internalShow(final LinkedList<AlertImpl> currentObjects, final AlertImpl newAlert,
                                final boolean force, final boolean showLater) {
        currentObjects.addLast(newAlert);

        if (showLater) {
            return;
        }

        long waitForClosing = 0;
        if (newAlert.hasPriority() && !currentObjects.isEmpty()) {
            HitogoPrioritySubClass priorityObject = getCurrentHighestPriority(currentObjects);
            if (priorityObject.isAlertVisibilityAllowed(newAlert.hashCode())) {
                waitForClosing = closeByType(newAlert.getType(), force);
            } else {
                return;
            }
        }

        if (newAlert.isClosingOthers() && !currentObjects.isEmpty()) {
            long waitByType = closeByType(newAlert.getType(), force);
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
                    if (alert.getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                        internalMakeVisible(alert, false);
                    }
                }
            }, wait);
        } else {
            internalMakeVisible(alert, true);
        }
    }

    protected HitogoPrioritySubClass getCurrentHighestPriority(LinkedList<AlertImpl> currentObjects) {
        HitogoPrioritySubClass priorityObject = new HitogoPrioritySubClass();

        for (AlertImpl alert : currentObjects) {
            if (alert.hasPriority() && alert.getPriority() < priorityObject.getSubClassPriority()) {
                priorityObject = new HitogoPrioritySubClass();
                priorityObject.setSubClassPriority(alert.getPriority());
                priorityObject.addSubClassAlertHashCode(alert.hashCode());
                priorityObject.setSubClassVisible(isAlertAttached(alert));
            } else if (alert.hasPriority() && alert.getPriority() == priorityObject.getSubClassPriority()) {
                priorityObject.addSubClassAlertHashCode(alert.hashCode());
                priorityObject.setSubClassVisible(isAlertAttached(alert));
            }
        }

        return priorityObject;
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

    protected long internalCloseByType(Iterator<AlertImpl> it, @NonNull AlertType type, boolean force) {
        long longestClosingAnim = 0;

        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && type == object.getType() && object.isAttached()) {
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

    protected void internalCloseByTag(Iterator<AlertImpl> it, @NonNull String tag, boolean force, long currentLongest) {
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

    protected void internalCloseByState(Iterator<AlertImpl> it, int state, boolean force, long currentLongest) {
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
            return internalCloseByAlert(getCurrentAlertList(alert.getType()).iterator(), alert, force);
        }
    }

    protected long internalCloseByAlert(Iterator<AlertImpl> it, final @NonNull Alert alert, boolean force) {
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

    protected void internalMakeVisible(AlertImpl object, boolean force) {
        int count = alertCountMap.get(object.hashCode());
        if (count == 0) {
            alertCountMap.put(object.hashCode(), 1);
            object.makeVisible(force);
            currentActiveAlerts.add(object);
        } else {
            alertCountMap.put(object.hashCode(), count + 1);
        }
    }

    protected void internalMakeInvisible(AlertImpl object, boolean force) {
        int count = alertCountMap.get(object.hashCode());
        if (count == 1) {
            alertCountMap.delete(object.hashCode());
            internalMakeActiveAlertInvisible(object, force);
        } else {
            alertCountMap.put(object.hashCode(), count - 1);
        }
    }

    protected void internalMakeActiveAlertInvisible(AlertImpl toBeClosedAlert, final boolean force) {
        Iterator<AlertImpl> iterator = currentActiveAlerts.iterator();

        while (iterator.hasNext()){
            AlertImpl alert = iterator.next();

            if(toBeClosedAlert.equals(alert)) {
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
    public Class<? extends ButtonImpl> provideDefaultActionButtonClass() {
        return ActionButtonImpl.class;
    }

    @NonNull
    public Class<? extends ButtonParams> provideDefaultActionButtonParamsClass() {
        return ActionButtonParams.class;
    }

    @NonNull
    public Class<? extends ButtonImpl> provideDefaultSimpleButtonClass() {
        return SimpleButtonImpl.class;
    }

    @NonNull
    public Class<? extends ButtonParams> provideDefaultSimpleButtonParamsClass() {
        return SimpleButtonParams.class;
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
    public Integer provideDefaultState(AlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultLayoutContainerId() {
        return null;
    }

    @Nullable
    public Integer provideDefaultOverlayContainerId() {
        return null;
    }

    @Nullable
    public Integer provideDefaultTextViewId(AlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultTitleViewId(AlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultCloseIconId() {
        return null;
    }

    @Nullable
    public Integer provideDefaultCloseClickId() {
        return null;
    }

    @Nullable
    public <T extends HitogoAnimation> T provideDefaultAnimation() {
        return null;
    }

    @Nullable
    public Integer provideDefaultLayoutViewId() {
        return null;
    }

    @NonNull
    public <T extends HitogoHelper> T provideHelper() {
        return (T) new HitogoHelper();
    }

    public abstract boolean provideIsDebugState();
}
