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
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.alert.popup.PopupAlertParams;
import org.hitogo.alert.view.ViewAlertImpl;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.button.action.ActionButtonImpl;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.action.ActionButtonParams;
import org.hitogo.alert.dialog.DialogAlertBuilder;
import org.hitogo.alert.dialog.DialogAlertParams;
import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.button.core.ButtonParams;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoController implements LifecycleObserver {

    protected final Object syncLock = new Object();

    protected final LinkedList<AlertImpl> currentViews = new LinkedList<>();
    protected final LinkedList<AlertImpl> currentDialogs = new LinkedList<>();
    protected final LinkedList<AlertImpl> currentPopups = new LinkedList<>();
    protected final SparseIntArray alertCountMap = new SparseIntArray();

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

    public void showNext(final boolean force, final AlertType type) {
        synchronized (syncLock) {
            final LinkedList<AlertImpl> currentAlerts = getCurrentAlertList(type);

            if (!currentAlerts.isEmpty()) {
                AlertImpl currentAlert = currentAlerts.pollLast();
                internalMakeInvisible(currentAlert, force);

                if (!currentAlerts.isEmpty()) {
                    showNextInvisibleAlert(currentAlerts, force, currentAlert.getAnimationDuration());
                }
            }
        }
    }

    protected void showNextInvisibleAlert(final LinkedList<AlertImpl> currentAlerts,
                                        final boolean force, final long wait) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (AlertImpl alert : currentAlerts) {
                    if (!isAlertAttached(alert)) {
                        if (alert.getContainer().getLifecycle()
                                .getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                            internalMakeVisible(alert, force);
                        }
                        break;
                    }
                }
            }
        }, wait);
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
            default:
                return new LinkedList<>();
        }
    }

    protected void internalShow(final LinkedList<AlertImpl> currentObjects, final AlertImpl newAlert,
                              final boolean force, final boolean showLater) {
        currentObjects.addFirst(newAlert);

        if (showLater) {
            return;
        }

        long waitForClosing = 0;
        if (newAlert.hasPriority()) {
            int currentHighestPriority = getCurrentHighestPriority(newAlert);
            if (currentHighestPriority <= newAlert.getPriority()) {
                return;
            } else {
                waitForClosing = closeByType(newAlert.getType(), force);
            }
        }

        if (newAlert.isClosingOthers() && !currentObjects.isEmpty()) {
            long waitByType = closeByType(newAlert.getType(), force);
            if (waitByType > waitForClosing) {
                waitForClosing = waitByType;
            }
        }

        if (!force) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (newAlert.getContainer().getLifecycle().getCurrentState()
                            .isAtLeast(Lifecycle.State.CREATED)) {
                        internalMakeVisible(newAlert, false);
                    }
                }
            }, waitForClosing);
        } else {
            internalMakeVisible(newAlert, true);
        }
    }

    protected int getCurrentHighestPriority(AlertImpl newAlert) {
        int currentHighestPriority = Integer.MAX_VALUE;
        List<AlertImpl> alertList = getCurrentAlertList(newAlert.getType());

        for (AlertImpl alert : alertList) {
            if (!alert.equals(newAlert) && alert.hasPriority() && alert.getPriority() < currentHighestPriority) {
                currentHighestPriority = alert.getPriority();
            }
        }

        return currentHighestPriority;
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
            long longestClosingAnim = 0;
            internalCloseByAlert(currentViews.iterator(), alert, force, longestClosingAnim);
            internalCloseByAlert(currentDialogs.iterator(), alert, force, longestClosingAnim);
            internalCloseByAlert(currentPopups.iterator(), alert, force, longestClosingAnim);
            return longestClosingAnim;
        }
    }

    protected void internalCloseByAlert(Iterator<AlertImpl> it, @NonNull Alert alert, boolean force, long currentLongest) {
        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object.equals(alert)) {
                if (object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                internalMakeInvisible(object, force);
                it.remove();
            }
        }
    }

    protected void internalMakeVisible(AlertImpl object, boolean force) {
        int count = alertCountMap.get(object.hashCode());
        if (count == 0) {
            alertCountMap.put(object.hashCode(), 1);
            object.makeVisible(force);
        } else {
            alertCountMap.put(object.hashCode(), count + 1);
        }
    }

    protected void internalMakeInvisible(AlertImpl object, boolean force) {
        int count = alertCountMap.get(object.hashCode());
        if (count == 1) {
            alertCountMap.delete(object.hashCode());
            object.makeInvisible(force);
        } else {
            alertCountMap.put(object.hashCode(), count - 1);
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
    public Class<? extends ButtonImpl> provideDefaultButtonClass() {
        return ActionButtonImpl.class;
    }

    @NonNull
    public Class<? extends ButtonParams> provideDefaultButtonParamsClass() {
        return ActionButtonParams.class;
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
    public HitogoAnimation provideDefaultAnimation() {
        return null;
    }

    @Nullable
    public Integer provideDefaultLayoutViewId() {
        return null;
    }

    @Nullable
    public ViewAlertBuilder provideSimpleView(@NonNull ViewAlertBuilder builder) {
        return null;
    }

    @Nullable
    public DialogAlertBuilder provideSimpleDialog(@NonNull DialogAlertBuilder builder) {
        return null;
    }

    @Nullable
    public PopupAlertBuilder provideSimplePopup(@NonNull PopupAlertBuilder builder) {
        return null;
    }

    public abstract boolean provideIsDebugState();
}
