package org.hitogo;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoDefaultAnimation extends HitogoAnimation {
    private int lastInAnimationHeight;
    private int lastOutAnimationHeight;

    @Override
    protected Animation buildShowAnimation(View hitogoView) {
        if (!areLastMeasuredInAnimationHeightAndCurrentEqual(hitogoView) || showAnimation == null) {
            showAnimation = new TranslateAnimation(
                    0, 0,                               // X: from, to
                    -hitogoView.getMeasuredHeight(), 0 // Y: from, to
            );
            showAnimation.setDuration(getShowDuration());
            setLastInAnimationHeight(hitogoView.getMeasuredHeight());
        }
        return showAnimation;
    }

    @Override
    protected Animation buildHideAnimation(View hitogoView) {
        if (!areLastMeasuredOutAnimationHeightAndCurrentEqual(hitogoView) || hideAnimation == null) {
            hideAnimation = new TranslateAnimation(
                    0, 0,                               // X: from, to
                    0, -hitogoView.getMeasuredHeight() // Y: from, to
            );
            hideAnimation.setDuration(getHideDuration());
            setLastOutAnimationHeight(hitogoView.getMeasuredHeight());
        }
        return hideAnimation;
    }

    private boolean areLastMeasuredInAnimationHeightAndCurrentEqual(View hitogoView) {
        return areLastMeasuredAnimationHeightAndCurrentEqual(lastInAnimationHeight, hitogoView);
    }

    private boolean areLastMeasuredOutAnimationHeightAndCurrentEqual(View hitogoView) {
        return areLastMeasuredAnimationHeightAndCurrentEqual(lastOutAnimationHeight, hitogoView);
    }

    private boolean areLastMeasuredAnimationHeightAndCurrentEqual(int lastHeight, View hitogoView) {
        return lastHeight == hitogoView.getMeasuredHeight();
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
