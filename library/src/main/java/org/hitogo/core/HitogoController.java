package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.dialog.DialogAlertImpl;
import org.hitogo.alert.popup.PopupAlertImpl;
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.alert.popup.PopupAlertParams;
import org.hitogo.alert.view.ViewAlertImpl;
import org.hitogo.alert.view.anim.Animation;
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

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();

    private final LinkedList<AlertImpl> currentViews = new LinkedList<>();
    private final LinkedList<AlertImpl> currentDialogs = new LinkedList<>();
    private final LinkedList<AlertImpl> currentPopups = new LinkedList<>();

    public HitogoController(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    public final void show(AlertImpl hitogo, boolean force) {
        show(hitogo, force, false);
    }

    public final void show(AlertImpl hitogo, boolean force, boolean showLater) {
        synchronized (syncLock) {
            switch (hitogo.getType()) {
                case VIEW:
                    internalShow(currentViews, hitogo, force, showLater);
                    break;
                case DIALOG:
                    internalShow(currentDialogs, hitogo, force, showLater);
                    break;
                case POPUP:
                    internalShow(currentPopups, hitogo, force, showLater);
                    break;
            }
        }
    }

    public final void showNext(final boolean force, final AlertType type) {
        synchronized (syncLock) {
            final LinkedList<AlertImpl> currentAlerts = getCurrentAlertList(type);

            if(!currentAlerts.isEmpty()) {
                AlertImpl currentAlert = currentAlerts.pollLast();
                currentAlert.makeInvisible(force);

                if(!currentAlerts.isEmpty()) {
                    showNextInvisibleAlert(currentAlerts, force, currentAlert.getAnimationDuration());
                }
            }
        }
    }

    private void showNextInvisibleAlert(final LinkedList<AlertImpl> currentAlerts,
                                        final boolean force, final long wait) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (AlertImpl alert : currentAlerts) {
                    if (!alert.isAttached()) {
                        if (alert.getContainer().getLifecycle()
                                .getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                            alert.makeVisible(force);
                            currentAlerts.remove(alert);
                        }
                        break;
                    }
                }
            }
        }, wait);
    }

    @NonNull
    private LinkedList<AlertImpl> getCurrentAlertList(AlertType type) {
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

    private void internalShow(final LinkedList<AlertImpl> currentObjects, final AlertImpl newAlert,
                              final boolean force, final boolean showLater) {

        currentObjects.addFirst(newAlert);

        if(showLater) {
            return;
        }

        long waitForClosing = 0;
        if(!currentObjects.isEmpty() && newAlert.isClosingOthers()) {
            waitForClosing = closeByType(newAlert.getType());
        }

        if(!force) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (newAlert.getContainer().getLifecycle().getCurrentState()
                            .isAtLeast(Lifecycle.State.CREATED)) {
                        newAlert.makeVisible(false);
                    }
                }
            }, waitForClosing);
        } else {
            newAlert.makeVisible(true);
        }
    }

    public final long closeAll() {
        return closeAll(false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected final long closeAllOnDestroy() {
       return closeAll(true);
    }

    public final long closeAll(boolean force) {
        synchronized (syncLock) {
            long longestClosingAnim = 0;
            internalCloseByTag(currentViews.iterator(), force, longestClosingAnim);
            internalCloseByTag(currentDialogs.iterator(), force, longestClosingAnim);
            internalCloseByTag(currentPopups.iterator(), force, longestClosingAnim);
            return longestClosingAnim;
        }
    }

    private void internalCloseByTag(Iterator<AlertImpl> it, boolean force, long currentLongest) {
        while(it.hasNext()) {
            AlertImpl object = it.next();
            if(object != null && object.isAttached()) {
                if(object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                object.makeInvisible(force);
                it.remove();
            }
        }
    }

    public final long closeByType(@NonNull AlertType type) {
        return closeByType(type, false);
    }

    public final long closeByType(@NonNull AlertType type, boolean force) {
        synchronized (syncLock) {
            if(type == AlertType.VIEW) {
                return internalCloseByType(currentViews.iterator(), type, force);
            } else if(type == AlertType.DIALOG) {
                return internalCloseByType(currentDialogs.iterator(), type, force);
            } else if(type == AlertType.POPUP) {
                return internalCloseByType(currentPopups.iterator(), type, force);
            } else {
                return 0;
            }
        }
    }

    private long internalCloseByType(Iterator<AlertImpl> it, @NonNull AlertType type, boolean force) {
        long longestClosingAnim = 0;

        while(it.hasNext()) {
            AlertImpl object = it.next();
            if(object != null && type == object.getType() && object.isAttached()) {
                if(object.getAnimationDuration() > longestClosingAnim) {
                    longestClosingAnim = object.getAnimationDuration();
                }

                object.makeInvisible(force);
                it.remove();
            }
        }

        return longestClosingAnim;
    }

    public final long closeByTag(@NonNull String tag) {
        return closeByTag(tag, false);
    }

    public final long closeByTag(@NonNull String tag, boolean force) {
        synchronized (syncLock) {
            long longestClosingAnim = 0;
            internalCloseByTag(currentViews.iterator(), tag, force, longestClosingAnim);
            internalCloseByTag(currentDialogs.iterator(), tag, force, longestClosingAnim);
            internalCloseByTag(currentPopups.iterator(), tag, force, longestClosingAnim);
            return longestClosingAnim;
        }
    }

    private void internalCloseByTag(Iterator<AlertImpl> it, @NonNull String tag, boolean force, long currentLongest) {
        int tagHashCode = tag.hashCode();

        while(it.hasNext()) {
            AlertImpl object = it.next();
            if(object != null && object.isAttached() && object.hashCode() == tagHashCode) {
                if(object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                object.makeInvisible(force);
                it.remove();
            }
        }
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
    public Animation provideDefaultAnimation() {
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

    public boolean shouldOverrideDebugMode() {
        return false;
    }
}
