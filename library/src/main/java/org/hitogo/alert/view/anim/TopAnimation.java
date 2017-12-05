package org.hitogo.alert.view.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.view.ViewAlertParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TopAnimation extends Animation {

    public static Animation build() {
        return new TopAnimation();
    }

    @Override
    public void showAnimation(@NonNull final ViewAlertParams params, @NonNull final View hitogoView,
                              @NonNull final AlertImpl alert) {
        ValueAnimator anim = ValueAnimator.ofInt(0, hitogoView.getMeasuredHeight());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams params1 = hitogoView.getLayoutParams();
                params1.height = animatedValue;
                hitogoView.setLayoutParams(params1);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (params.getInnerLayoutViewId() != null) {
                    hitogoView.findViewById(params.getInnerLayoutViewId()).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (params.getInnerLayoutViewId() != null) {
                    hitogoView.findViewById(params.getInnerLayoutViewId()).setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.setDuration(getAnimationDuration());
        anim.start();
    }

    @Override
    public void hideAnimation(@NonNull final ViewAlertParams params, @NonNull final View hitogoView,
                              @NonNull final AlertImpl alert) {
        ValueAnimator anim = ValueAnimator.ofInt(hitogoView.getMeasuredHeight(), 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams params1 = hitogoView.getLayoutParams();
                params1.height = animatedValue;
                hitogoView.setLayoutParams(params1);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((ViewManager) hitogoView.getParent()).removeView(hitogoView);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (params.getInnerLayoutViewId() != null) {
                    hitogoView.findViewById(params.getInnerLayoutViewId()).setVisibility(View.INVISIBLE);
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
