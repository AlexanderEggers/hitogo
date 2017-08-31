package com.mordag.crouton;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.mordag.crouton.CroutonAnimation;

public final class CroutonDefaultAnimation extends CroutonAnimation {
    private int lastInAnimationHeight;
    private int lastOutAnimationHeight;

    @Override
    protected Animation buildShowAnimation(View croutonView) {
        if (!areLastMeasuredInAnimationHeightAndCurrentEqual(croutonView) || showAnimation == null) {
            showAnimation = new TranslateAnimation(
                    0, 0,                               // X: from, to
                    -croutonView.getMeasuredHeight(), 0 // Y: from, to
            );
            showAnimation.setDuration(getShowDuration());
            setLastInAnimationHeight(croutonView.getMeasuredHeight());
        }
        return showAnimation;
    }

    @Override
    protected Animation buildHideAnimation(View croutonView) {
        if (!areLastMeasuredOutAnimationHeightAndCurrentEqual(croutonView) || hideAnimation == null) {
            hideAnimation = new TranslateAnimation(
                    0, 0,                               // X: from, to
                    0, -croutonView.getMeasuredHeight() // Y: from, to
            );
            hideAnimation.setDuration(getHideDuration());
            setLastOutAnimationHeight(croutonView.getMeasuredHeight());
        }
        return hideAnimation;
    }

    private boolean areLastMeasuredInAnimationHeightAndCurrentEqual(View croutonView) {
        return areLastMeasuredAnimationHeightAndCurrentEqual(lastInAnimationHeight, croutonView);
    }

    private boolean areLastMeasuredOutAnimationHeightAndCurrentEqual(View croutonView) {
        return areLastMeasuredAnimationHeightAndCurrentEqual(lastOutAnimationHeight, croutonView);
    }

    private boolean areLastMeasuredAnimationHeightAndCurrentEqual(int lastHeight, View croutonView) {
        return lastHeight == croutonView.getMeasuredHeight();
    }

    private void setLastInAnimationHeight(int lastInAnimationHeight) {
        this.lastInAnimationHeight = lastInAnimationHeight;
    }

    private void setLastOutAnimationHeight(int lastOutAnimationHeight) {
        this.lastOutAnimationHeight = lastOutAnimationHeight;
    }

    @Override
    protected long getShowDuration() {
        return 400;
    }

    @Override
    protected long getHideDuration() {
        return 400;
    }
}
