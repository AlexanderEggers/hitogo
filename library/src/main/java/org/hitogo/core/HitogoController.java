package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.dialog.HitogoDialogBuilder;
import org.hitogo.view.HitogoViewBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();
    private HitogoObject currentView;
    private HitogoObject currentDialog;

    public HitogoController(@NonNull LifecycleRegistry lifecycle) {
        lifecycle.addObserver(this);
    }

    final HitogoObject[] validate(HitogoObject hitogo) {
        synchronized (syncLock) {
            if(hitogo.getType() == HitogoObject.TYPE_VIEW) {
                return validateHitogo(currentView, hitogo);
            } else {
                return validateHitogo(currentDialog, hitogo);
            }
        }
    }

    private HitogoObject[] validateHitogo(HitogoObject hitogoObject, HitogoObject newHitogo) {
        HitogoObject[] hitogoStack = new HitogoObject[2];
        HitogoObject currentHitogo = hitogoObject;
        HitogoObject lastCrouton = null;

        if (currentHitogo != null) {
            if (!currentHitogo.equals(newHitogo)) {
                currentHitogo.makeInvisible();
                lastCrouton = currentHitogo;
                currentHitogo = newHitogo;
            }
        } else {
            currentHitogo = newHitogo;
        }

        hitogoStack[0] = currentHitogo;
        hitogoStack[1] = lastCrouton;

        return hitogoStack;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void closeHitogo() {
        synchronized (syncLock) {
            if (currentView != null && currentView.isAttached()) {
                currentView.makeInvisible();

                if (currentView.hasAnimation()) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentView = null;
                        }
                    }, currentView.getAnimationDuration());
                } else {
                    currentView = null;
                }
            }

            if(currentDialog != null && currentDialog.isAttached()) {
                currentDialog.makeInvisible();
                currentDialog = null;
            }
        }
    }

    @LayoutRes
    public abstract int getLayout(int state);

    @Nullable
    public Integer getDefaultState() {
        return null;
    }

    @Nullable
    public Integer getDefaultLayoutContainerId() {
        return null;
    }

    @Nullable
    public Integer getDefaultOverlayContainerId() {
        return null;
    }

    @Nullable
    public Integer getDefaultTextViewId() {
        return null;
    }

    @Nullable
    public Integer getDefaultTitleViewId() {
        return null;
    }

    @Nullable
    public Integer getDefaultCallToActionId() {
        return null;
    }

    @Nullable
    public Integer getDefaultCloseIconId() {
        return null;
    }

    @Nullable
    public Integer getDefaultCloseClickId() {
        return null;
    }

    @Nullable
    public HitogoAnimation getDefaultAnimation() {
        return null;
    }

    @Nullable
    public Integer getDefaultLayoutViewId() {
        return null;
    }

    @Nullable
    public HitogoViewBuilder getSimpleView(@NonNull HitogoViewBuilder builder) {
        return null;
    }

    @Nullable
    public HitogoDialogBuilder getSimpleDialog(@NonNull HitogoDialogBuilder builder) {
        return null;
    }
}
