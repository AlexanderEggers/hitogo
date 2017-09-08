package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;

import org.hitogo.dialog.HitogoDialogBuilder;
import org.hitogo.view.HitogoViewBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();
    private HitogoObject currentCrouton;

    public HitogoController(@NonNull LifecycleRegistry lifecycle) {
        lifecycle.addObserver(this);
    }

    public final HitogoObject[] validate(HitogoObject crouton) {
        synchronized (syncLock) {
            HitogoObject[] currentStack = new HitogoObject[2];
            HitogoObject lastCrouton = null;

            if(currentCrouton != null) {
                if(!currentCrouton.equals(crouton)) {
                    currentCrouton.makeInvisible();
                    lastCrouton = currentCrouton;
                    currentCrouton = crouton;
                }
            } else {
                currentCrouton = crouton;
            }

            currentStack[0] = currentCrouton;
            currentStack[1] = lastCrouton;

            return currentStack;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void closeHitogo() {
        synchronized (syncLock) {
            if(currentCrouton != null && currentCrouton.isAttached()) {
                currentCrouton.makeInvisible();

                if(currentCrouton.hasAnimation()) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentCrouton.onGone();
                        }
                    }, currentCrouton.getAnimationDuration());
                }

                currentCrouton = null;
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
