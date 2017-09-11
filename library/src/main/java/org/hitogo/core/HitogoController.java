package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.HitogoButton;
import org.hitogo.button.HitogoButtonObject;
import org.hitogo.button.HitogoButtonParams;
import org.hitogo.dialog.HitogoDialog;
import org.hitogo.dialog.HitogoDialogBuilder;
import org.hitogo.dialog.HitogoDialogParams;
import org.hitogo.view.HitogoView;
import org.hitogo.view.HitogoViewBuilder;
import org.hitogo.view.HitogoViewParams;

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
            if(hitogo.getType() == HitogoObject.HitogoType.VIEW) {
                HitogoObject[] currentStack = validateHitogo(currentView, hitogo);
                currentView = currentStack[0];
                return currentStack;
            } else {
                HitogoObject[] currentStack = validateHitogo(currentDialog, hitogo);
                currentDialog = currentStack[0];
                return currentStack;
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
        closeHitogo(null);
    }

    public final void closeHitogo(@Nullable HitogoObject.HitogoType type) {
        synchronized (syncLock) {
            if(type != null) {
                if(currentView != null && type == currentView.getType()
                        && currentView.isAttached()) {
                    currentView.makeInvisible();
                }

                if(currentDialog != null && type == currentDialog.getType()
                        && currentDialog.isAttached()) {
                    currentDialog.makeInvisible();
                }
            } else {
                if(currentView != null && currentView.isAttached()) {
                    currentView.makeInvisible();
                }

                if(currentDialog != null && currentDialog.isAttached()) {
                    currentDialog.makeInvisible();
                }
            }
        }
    }

    @NonNull
    public Class<? extends HitogoObject> provideDefaultViewClass() {
        return HitogoView.class;
    }

    @NonNull
    public Class<? extends HitogoParams> provideDefaultViewParamsClass() {
        return HitogoViewParams.class;
    }

    @NonNull
    public Class<? extends HitogoObject> provideDefaultDialogClass() {
        return HitogoDialog.class;
    }

    @NonNull
    public Class<? extends HitogoParams> provideDefaultDialogParamsClass() {
        return HitogoDialogParams.class;
    }

    @NonNull
    public Class<? extends HitogoButtonObject> provideDefaultButtonClass() {
        return HitogoButton.class;
    }

    @NonNull
    public Class<? extends org.hitogo.button.HitogoParams> provideDefaultButtonParamsClass() {
        return HitogoButtonParams.class;
    }

    @LayoutRes
    @Nullable
    public Integer provideLayout(int state) {
        return null;
    }

    @Nullable
    public Integer provideDefaultState() {
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
    public Integer provideDefaultTextViewId() {
        return null;
    }

    @Nullable
    public Integer provideDefaultTitleViewId() {
        return null;
    }

    @Nullable
    public Integer provideDefaultCallToActionId() {
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
}
