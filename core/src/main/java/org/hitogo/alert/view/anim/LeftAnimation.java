package org.hitogo.alert.view.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.core.HitogoAnimation;

/**
 * Animation object which is used to play an animation that will animate the alert moving in from
 * left to the right.
 *
 * @since 1.0.0
 */
public class LeftAnimation extends HitogoAnimation {

    /**
     * Returns a new animation object that can be used.
     *
     * @return a HitogoAnimation object
     * @since 1.0.0
     */
    public static HitogoAnimation build() {
        return new LeftAnimation();
    }

    @Override
    public void showAnimation(@NonNull final ViewAlertParams params, @NonNull final View alertView,
                              @NonNull final AlertImpl alert) {
        ValueAnimator anim = ValueAnimator.ofInt(0, alertView.getMeasuredWidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = alertView.getLayoutParams();
                layoutParams.width = animatedValue;
                layoutParams.height = alertView.getMeasuredHeight();
                alertView.setLayoutParams(layoutParams);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (params.getInnerLayoutViewId() != null) {
                    alertView.findViewById(params.getInnerLayoutViewId()).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (params.getInnerLayoutViewId() != null) {
                    alertView.findViewById(params.getInnerLayoutViewId()).setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.setDuration(getAnimationDuration());
        anim.start();
    }

    @Override
    public void hideAnimation(@NonNull final ViewAlertParams params, @NonNull final View alertView,
                              @NonNull final AlertImpl alert) {
        ValueAnimator anim = ValueAnimator.ofInt(alertView.getMeasuredWidth(), 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = alertView.getLayoutParams();
                layoutParams.width = animatedValue;
                layoutParams.height = alertView.getMeasuredHeight();
                alertView.setLayoutParams(layoutParams);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((ViewManager) alertView.getParent()).removeView(alertView);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (params.getInnerLayoutViewId() != null) {
                    alertView.findViewById(params.getInnerLayoutViewId()).setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.setDuration(getAnimationDuration());
        anim.start();
    }

    @Override
    public long getAnimationDuration() {
        return 500;
    }
}
