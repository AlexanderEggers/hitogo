package org.hitogo.alert.view.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import org.hitogo.R;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.core.HitogoAnimation;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TopAnimation extends HitogoAnimation {

    public static HitogoAnimation build() {
        return new TopAnimation();
    }

    @Override
    public void showAnimation(@NonNull final ViewAlertParams params, @NonNull final View alertView,
                              @NonNull final AlertImpl alert) {
        ValueAnimator anim = ValueAnimator.ofInt(0, alertView.getMeasuredHeight());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = alertView.getLayoutParams();
                layoutParams.height = animatedValue;
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
        ValueAnimator anim = ValueAnimator.ofInt(alertView.getMeasuredHeight(), 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = alertView.getLayoutParams();
                layoutParams.height = animatedValue;
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
