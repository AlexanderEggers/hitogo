package org.hitogo;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAnimation {
    protected Animation showAnimation;
    protected Animation hideAnimation;

    @NonNull protected abstract Animation buildShowAnimation(@NonNull View hitogoView);
    @NonNull protected abstract Animation buildHideAnimation(@NonNull View hitogoView);
    protected abstract long getShowDuration();
    protected abstract long getHideDuration();
}
