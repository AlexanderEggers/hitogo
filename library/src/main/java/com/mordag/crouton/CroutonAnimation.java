package com.mordag.crouton;

import android.view.View;
import android.view.animation.Animation;

public abstract class CroutonAnimation {
    protected Animation showAnimation;
    protected Animation hideAnimation;

    protected abstract Animation buildShowAnimation(View croutonView);
    protected abstract Animation buildHideAnimation(View croutonView);
    protected abstract long getShowDuration();
    protected abstract long getHideDuration();
}
