package org.hitogo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewManager;
import android.widget.LinearLayout;

import org.hitogo.core.HitogoObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoTopAnimation extends HitogoAnimation {

    @Override
    public void showAnimation(@NonNull final View hitogoView, final HitogoObject hitogoObject) {
        ValueAnimator anim = ValueAnimator.ofInt(0, hitogoView.getMeasuredHeight());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) hitogoView.getLayoutParams();
                params.height = animatedValue;
                hitogoView.setLayoutParams(params);
                hitogoView.requestLayout();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hitogoObject.setVisibility(true);

                if(hitogoObject.getLayoutViewId() != null) {
                    hitogoView.findViewById(hitogoObject.getLayoutViewId()).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if(hitogoObject.getLayoutViewId() != null) {
                    hitogoView.findViewById(hitogoObject.getLayoutViewId()).setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.setDuration(getAnimationDuration());
        anim.start();
    }

    @Override
    protected void hideAnimation(@NonNull final View hitogoView, @NonNull final HitogoObject hitogoObject,
                                 @NonNull final ViewManager manager) {
        ValueAnimator anim = ValueAnimator.ofInt(hitogoView.getMeasuredHeight(), 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) hitogoView.getLayoutParams();
                params.height = animatedValue;
                hitogoView.setLayoutParams(params);
                hitogoView.requestLayout();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hitogoObject.setVisibility(false);
                manager.removeView(hitogoView);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if(hitogoObject.getLayoutViewId() != null) {
                    hitogoView.findViewById(hitogoObject.getLayoutViewId()).setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.setDuration(getAnimationDuration());
        anim.start();
    }

    @Override
    protected long getAnimationDuration() {
        return 400;
    }
}
