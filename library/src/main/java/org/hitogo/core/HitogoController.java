package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();

    private final List<HitogoAlert> currentViews = new ArrayList<>();
    private final List<HitogoAlert> currentDialogs = new ArrayList<>();

    public HitogoController(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    public final HitogoAlert[] validate(HitogoAlert hitogo) {
        synchronized (syncLock) {
            if(hitogo.getType() == HitogoAlertType.VIEW) {
                return validateHitogo(currentViews, hitogo);
            } else {
                return validateHitogo(currentDialogs, hitogo);
            }
        }
    }

    private HitogoAlert[] validateHitogo(List<HitogoAlert> currentObjects, HitogoAlert newHitogo) {
        HitogoAlert[] hitogoStack = new HitogoAlert[2];
        HitogoAlert currentHitogo = !currentObjects.isEmpty() ? currentObjects.get(0) : null;
        HitogoAlert lastHitogo = null;

        if (currentHitogo != null) {
            if (!currentHitogo.equals(newHitogo)) {
                if(newHitogo.isClosingOthers()) {
                    closeByType(newHitogo.getType());
                }
                lastHitogo = currentHitogo;
                currentHitogo = newHitogo;
                currentObjects.add(newHitogo);
            }
        } else {
            currentHitogo = newHitogo;
            currentObjects.add(newHitogo);
        }

        hitogoStack[0] = currentHitogo;
        hitogoStack[1] = lastHitogo;
        return hitogoStack;
    }

    public final void closeAll() {
        internalCloseAll(false);
    }

    public final void closeAll(boolean force) {
        internalCloseAll(force);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected final void closeAllOnDestroy() {
        internalCloseAll(true);
    }

    private void internalCloseAll(boolean force) {
        Iterator<HitogoAlert> it = currentViews.iterator();
        while(it.hasNext()) {
            HitogoAlert object = it.next();
            if(object != null && object.isAttached()) {
                object.makeInvisible(force);
                it.remove();
            }
        }

        it = currentDialogs.iterator();
        while(it.hasNext()) {
            HitogoAlert object = it.next();
            if(object != null && object.isAttached()) {
                object.makeInvisible(force);
                it.remove();
            }
        }
    }

    public final void closeByType(@NonNull HitogoAlertType type) {
        closeByType(type, false);
    }

    public final void closeByType(@NonNull HitogoAlertType type, boolean force) {
        synchronized (syncLock) {
            Iterator<HitogoAlert> it = currentViews.iterator();
            while(it.hasNext()) {
                HitogoAlert object = it.next();
                if(object != null && type == object.getType() && object.isAttached()) {
                    object.makeInvisible(force);
                    it.remove();
                }
            }

            it = currentDialogs.iterator();
            while(it.hasNext()) {
                HitogoAlert object = it.next();
                if(object != null && type == object.getType() && object.isAttached()) {
                    object.makeInvisible(force);
                    it.remove();
                }
            }
        }
    }

    public final void closeByTag(@NonNull String tag) {
        closeByTag(tag, false);
    }

    public final void closeByTag(@NonNull String tag, boolean force) {
        synchronized (syncLock) {
            int tagHashCode = tag.hashCode();

            Iterator<HitogoAlert> it = currentViews.iterator();
            while(it.hasNext()) {
                HitogoAlert object = it.next();
                if(object != null && object.isAttached() && object.hashCode() == tagHashCode) {
                    object.makeInvisible(force);
                    it.remove();
                }
            }

            it = currentDialogs.iterator();
            while(it.hasNext()) {
                HitogoAlert object = it.next();
                if(object != null && object.isAttached() && object.hashCode() == tagHashCode) {
                    object.makeInvisible(force);
                    it.remove();
                }
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

    public boolean shouldOverrideDebugMode() {
        return false;
    }
}
