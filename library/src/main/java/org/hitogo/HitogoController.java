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

    @LayoutRes
    public abstract int getLayout(int state);

    @Nullable
    public Integer getDefaultState() {
        return null;
    }

    @Nullable
    @XmlRes
    public Integer getDefaultLayoutContainerId() {
        return null;
    }

    @Nullable
    @XmlRes
    public Integer getDefaultOverlayContainerId() {
        return null;
    }

    @Nullable
    @XmlRes
    public Integer getDefaultTextViewId() {
        return null;
    }

    @Nullable
    @XmlRes
    public Integer getDefaultTitleViewId() {
        return null;
    }

    @Nullable
    @XmlRes
    public Integer getDefaultCallToActionId() {
        return null;
    }

    @Nullable
    @XmlRes
    public Integer getDefaultCloseIconId() {
        return null;
    }

    @Nullable
    @XmlRes
    public Integer getDefaultCloseClickId() {
        return null;
    }

    @Nullable
    public HitogoAnimation getDefaultAnimation() {
        return null;
    }

    @Nullable
    public HitogoBuilder getDefaultAsSimpleCall(@NonNull HitogoBuilder builder) {
        return null;
    }
}
