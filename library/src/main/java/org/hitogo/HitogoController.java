package org.hitogo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoController implements LifecycleObserver {

    private HitogoObject hitogo;

    private HitogoController() {
        throw new IllegalStateException("Cannot use this empty constructor because of the " +
                "missing lifecycle object.");
    }

    public HitogoController(@NonNull LifecycleRegistry lifecycle) {
        lifecycle.addObserver(this);
    }

    @NonNull
    public final HitogoObject validate(@NonNull HitogoObject hitogo) {
        if (this.hitogo == null || !this.hitogo.equals(hitogo)) {
            if (this.hitogo != null) {
                this.hitogo.hide();
            }
            this.hitogo = hitogo;
            return hitogo;
        }
        return this.hitogo;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void hide() {
        if (hitogo != null && hitogo.isVisible()) {
            hitogo.hide();
        }
    }

    public abstract int getDefaultState();

    @LayoutRes
    public abstract int getLayout(int state);
    @XmlRes
    public abstract int getLayoutContainerId();
    @XmlRes
    public abstract int getOverlayContainerId();
    @XmlRes
    public abstract int getDefaultTextViewId();
    @XmlRes
    public abstract int getDefaultTitleViewId();
    @XmlRes
    public abstract int getDefaultCloseIconId();
    @Nullable
    @XmlRes
    public abstract Integer getDefaultCloseClickId();

    @Nullable
    public abstract HitogoAnimation getDefaultAnimation();
}
