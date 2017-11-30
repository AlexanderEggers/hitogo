package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.popup.HitogoPopup;
import org.hitogo.alert.popup.HitogoPopupBuilder;
import org.hitogo.alert.popup.HitogoPopupParams;
import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertType;
import org.hitogo.button.action.HitogoAction;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.button.action.HitogoActionParams;
import org.hitogo.alert.dialog.HitogoDialog;
import org.hitogo.alert.dialog.HitogoDialogBuilder;
import org.hitogo.alert.dialog.HitogoDialogParams;
import org.hitogo.alert.view.HitogoView;
import org.hitogo.alert.view.HitogoViewBuilder;
import org.hitogo.alert.view.HitogoViewParams;

import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();

    private final LinkedList<HitogoAlert> currentViews = new LinkedList<>();
    private final LinkedList<HitogoAlert> currentDialogs = new LinkedList<>();
    private final LinkedList<HitogoAlert> currentPopups = new LinkedList<>();

    public HitogoController(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    public final void show(HitogoAlert hitogo, boolean force) {
        show(hitogo, force, false);
    }

    public final void show(HitogoAlert hitogo, boolean force, boolean showLater) {
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

    public final void showNext(final boolean force, final HitogoAlertType type) {
        synchronized (syncLock) {
            final LinkedList<HitogoAlert> currentAlerts = getCurrentAlertList(type);

            if(!currentAlerts.isEmpty()) {
                HitogoAlert currentAlert = currentAlerts.pollLast();
                currentAlert.makeInvisible(force);

                if(!currentAlerts.isEmpty()) {
                    showNextInvisibleAlert(currentAlerts, force, currentAlert.getAnimationDuration());
                }
            }
        }
    }

    private void showNextInvisibleAlert(final LinkedList<HitogoAlert> currentAlerts,
                                        final boolean force, final long wait) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (HitogoAlert alert : currentAlerts) {
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
    private LinkedList<HitogoAlert> getCurrentAlertList(HitogoAlertType type) {
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

    private void internalShow(final LinkedList<HitogoAlert> currentObjects, final HitogoAlert newAlert,
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

    private void internalCloseByTag(Iterator<HitogoAlert> it, boolean force, long currentLongest) {
        while(it.hasNext()) {
            HitogoAlert object = it.next();
            if(object != null && object.isAttached()) {
                if(object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                object.makeInvisible(force);
                it.remove();
            }
        }
    }

    public final long closeByType(@NonNull HitogoAlertType type) {
        return closeByType(type, false);
    }

    public final long closeByType(@NonNull HitogoAlertType type, boolean force) {
        synchronized (syncLock) {
            if(type == HitogoAlertType.VIEW) {
                return internalCloseByType(currentViews.iterator(), type, force);
            } else if(type == HitogoAlertType.DIALOG) {
                return internalCloseByType(currentDialogs.iterator(), type, force);
            } else if(type == HitogoAlertType.POPUP) {
                return internalCloseByType(currentPopups.iterator(), type, force);
            } else {
                return 0;
            }
        }
    }

    private long internalCloseByType(Iterator<HitogoAlert> it, @NonNull HitogoAlertType type, boolean force) {
        long longestClosingAnim = 0;

        while(it.hasNext()) {
            HitogoAlert object = it.next();
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

    private void internalCloseByTag(Iterator<HitogoAlert> it, @NonNull String tag, boolean force, long currentLongest) {
        int tagHashCode = tag.hashCode();

        while(it.hasNext()) {
            HitogoAlert object = it.next();
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
    public Class<? extends HitogoAlert> provideDefaultViewClass() {
        return HitogoView.class;
    }

    @NonNull
    public Class<? extends HitogoAlertParams> provideDefaultViewParamsClass() {
        return HitogoViewParams.class;
    }

    @NonNull
    public Class<? extends HitogoAlert> provideDefaultDialogClass() {
        return HitogoDialog.class;
    }

    @NonNull
    public Class<? extends HitogoAlertParams> provideDefaultDialogParamsClass() {
        return HitogoDialogParams.class;
    }

    @NonNull
    public Class<? extends HitogoAlert> provideDefaultPopupClass() {
        return HitogoPopup.class;
    }

    @NonNull
    public Class<? extends HitogoAlertParams> provideDefaultPopupParamsClass() {
        return HitogoPopupParams.class;
    }

    @NonNull
    public Class<? extends HitogoButton> provideDefaultButtonClass() {
        return HitogoAction.class;
    }

    @NonNull
    public Class<? extends org.hitogo.button.core.HitogoButtonParams> provideDefaultButtonParamsClass() {
        return HitogoActionParams.class;
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
    public Integer provideDefaultState(HitogoAlertType type) {
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
    public Integer provideDefaultTextViewId(HitogoAlertType type) {
        return null;
    }

    @Nullable
    public Integer provideDefaultTitleViewId(HitogoAlertType type) {
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
    public HitogoViewBuilder provideSimpleView(@NonNull HitogoViewBuilder builder) {
        return null;
    }

    @Nullable
    public HitogoDialogBuilder provideSimpleDialog(@NonNull HitogoDialogBuilder builder) {
        return null;
    }

    @Nullable
    public HitogoPopupBuilder provideSimplePopup(@NonNull HitogoPopupBuilder builder) {
        return null;
    }

    public boolean shouldOverrideDebugMode() {
        return false;
    }
}
