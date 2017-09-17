package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();

    private final List<HitogoObject> currentViews = new ArrayList<>();
    private final List<HitogoObject> currentDialogs = new ArrayList<>();

    public HitogoController(@NonNull LifecycleRegistry lifecycle) {
        lifecycle.addObserver(this);
    }

    final HitogoObject[] validate(HitogoObject hitogo) {
        synchronized (syncLock) {
            if(hitogo.getType() == HitogoObject.HitogoType.VIEW) {
                return validateHitogo(currentViews, hitogo);
            } else {
                return validateHitogo(currentDialogs, hitogo);
            }
        }
    }

    private HitogoObject[] validateHitogo(List<HitogoObject> currentObjects, HitogoObject newHitogo) {
        HitogoObject[] hitogoStack = new HitogoObject[2];
        HitogoObject currentHitogo = !currentObjects.isEmpty() ? currentObjects.get(0) : null;
        HitogoObject lastCrouton = null;

        if (currentHitogo != null) {
            if (!currentHitogo.equals(newHitogo)) {
                if(newHitogo.isClosingOthers()) {
                    closeByType(newHitogo.getType());
                }
                lastCrouton = currentHitogo;
                currentHitogo = newHitogo;
                currentObjects.add(newHitogo);
            }
        } else {
            currentHitogo = newHitogo;
            currentObjects.add(newHitogo);
        }

        hitogoStack[0] = currentHitogo;
        hitogoStack[1] = lastCrouton;
        return hitogoStack;
    }

    public final void closeAll() {
        internalCloseAll(false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void forceCloseAll() {
        internalCloseAll(true);
    }

    private void internalCloseAll(boolean force) {
        Iterator<HitogoObject> it = currentViews.iterator();
        while(it.hasNext()) {
            HitogoObject object = it.next();
            if(object != null && object.isAttached()) {
                object.makeInvisible(force);
                it.remove();
            }
        }

        it = currentDialogs.iterator();
        while(it.hasNext()) {
            HitogoObject object = it.next();
            if(object != null && object.isAttached()) {
                object.makeInvisible(force);
                it.remove();
            }
        }
    }

    public final void closeByType(@NonNull HitogoObject.HitogoType type) {
        internalCloseByType(type, false);
    }

    public final void forceCloseByType(@NonNull HitogoObject.HitogoType type) {
        internalCloseByType(type, true);
    }

    private void internalCloseByType(@NonNull HitogoObject.HitogoType type, boolean force) {
        synchronized (syncLock) {
            Iterator<HitogoObject> it = currentViews.iterator();
            while(it.hasNext()) {
                HitogoObject object = it.next();
                if(object != null && type == object.getType() && object.isAttached()) {
                    object.makeInvisible(force);
                    it.remove();
                }
            }

            it = currentDialogs.iterator();
            while(it.hasNext()) {
                HitogoObject object = it.next();
                if(object != null && type == object.getType() && object.isAttached()) {
                    object.makeInvisible(force);
                    it.remove();
                }
            }
        }
    }

    public final void closeByTag(@NonNull String tag) {
        internalCloseByTag(tag, false);
    }

    public final void forceCloseByTag(@NonNull String tag) {
        internalCloseByTag(tag, true);
    }

    private void internalCloseByTag(@NonNull String tag, boolean force) {
        synchronized (syncLock) {
            int tagHashCode = tag.hashCode();

            Iterator<HitogoObject> it = currentViews.iterator();
            while(it.hasNext()) {
                HitogoObject object = it.next();
                if(object != null && object.isAttached() && object.hashCode() == tagHashCode) {
                    object.makeInvisible(force);
                    it.remove();
                }
            }

            it = currentDialogs.iterator();
            while(it.hasNext()) {
                HitogoObject object = it.next();
                if(object != null && object.isAttached() && object.hashCode() == tagHashCode) {
                    object.makeInvisible(force);
                    it.remove();
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
