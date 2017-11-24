package org.hitogo.alert.view.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.view.HitogoViewParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoTopAnimation extends HitogoAnimation {

    public static HitogoAnimation build() {
        return new HitogoTopAnimation();
    }

    @Override
    public void showAnimation(@NonNull final HitogoViewParams params, @NonNull final View hitogoView,
                              @NonNull final HitogoAlert hitogoAlert) {
        final Integer layoutViewId = params.getLayoutViewId() != null ?
                params.getLayoutViewId() : hitogoAlert.getController().provideDefaultLayoutViewId();

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
                if (layoutViewId != null) {
                    hitogoView.findViewById(layoutViewId).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (layoutViewId != null) {
                    hitogoView.findViewById(layoutViewId).setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.setDuration(getAnimationDuration());
        anim.start();
    }

    @Override
    public void hideAnimation(@NonNull final HitogoViewParams params, @NonNull final View hitogoView,
                              @NonNull final HitogoAlert hitogoAlert) {
        final Integer layoutViewId = params.getLayoutViewId() != null ?
                params.getLayoutViewId() : hitogoAlert.getController().provideDefaultLayoutViewId();

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
                if (layoutViewId != null) {
                    hitogoView.findViewById(layoutViewId).setVisibility(View.INVISIBLE);
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
