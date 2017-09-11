package org.hitogo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import org.hitogo.core.HitogoAnimation;
import org.hitogo.core.HitogoObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoLeftAnimation extends HitogoAnimation {

    public static HitogoAnimation build() {
        return new HitogoLeftAnimation();
    }

    @Override
    public void showAnimation(@NonNull final HitogoViewParams params, @NonNull final View hitogoView,
                              @NonNull final HitogoObject object) {
        ValueAnimator anim = ValueAnimator.ofInt(0, hitogoView.getMeasuredWidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams params = hitogoView.getLayoutParams();
                params.width = animatedValue;
                params.height = hitogoView.getMeasuredHeight();
                hitogoView.setLayoutParams(params);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (params.getLayoutViewId() != null) {
                    hitogoView.findViewById(params.getLayoutViewId()).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (params.getLayoutViewId() != null) {
                    hitogoView.findViewById(params.getLayoutViewId()).setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.setDuration(getAnimationDuration());
        anim.start();
    }

    @Override
    public void hideAnimation(@NonNull final HitogoViewParams params, @NonNull final View hitogoView,
                              @NonNull final HitogoObject hitogoObject) {
        ValueAnimator anim = ValueAnimator.ofInt(hitogoView.getMeasuredWidth(), 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams params = hitogoView.getLayoutParams();
                params.width = animatedValue;
                params.height = hitogoView.getMeasuredHeight();
                hitogoView.setLayoutParams(params);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((ViewManager) hitogoView.getParent()).removeView(hitogoView);
                onFinishHide(hitogoObject);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (params.getLayoutViewId() != null) {
                    hitogoView.findViewById(params.getLayoutViewId()).setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.setDuration(getAnimationDuration());
        anim.start();
    }

    @Override
    public long getAnimationDuration() {
        return 400;
    }
}
