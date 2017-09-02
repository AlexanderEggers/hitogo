package com.mordag.hitogo;

import android.view.View;
import android.view.animation.Animation;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAnimation {
    protected Animation showAnimation;
    protected Animation hideAnimation;

    protected abstract Animation buildShowAnimation(View hitogoView);
    protected abstract Animation buildHideAnimation(View hitogoView);
    protected abstract long getShowDuration();
    protected abstract long getHideDuration();
}
