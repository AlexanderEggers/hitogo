package com.mordag.hitogo;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.Animation;

@SuppressWarnings({"WeakerAccess", "unused"})
final class HitogoView extends HitogoObject {
    private HitogoAnimation animation;
    private final View customView;
    private ViewGroup viewGroup;
    private Animation inAnimation;
    private Animation outAnimation;
    private boolean showAnimations;
    private boolean isVisible;

    private HitogoView(View customView, ViewGroup viewGroup, boolean showAnimations, int hashCode,
                       HitogoController controller, HitogoAnimation animation) {
        super(controller, hashCode);
        this.customView = customView;
        this.viewGroup = viewGroup;
        this.showAnimations = showAnimations;

        if (animation != null) {
            this.animation = animation;
        } else if(controller.getDefaultAnimation() != null) {
            this.animation = controller.getDefaultAnimation();
        } else {
            this.animation = new HitogoDefaultAnimation();
        }
    }

    static HitogoView create(View customView, ViewGroup viewGroup, boolean showAnimations,
                           int hashCode, HitogoController controller, HitogoAnimation animation) {
        return new HitogoView(customView, viewGroup, showAnimations, hashCode, controller, animation);
    }

    @Override
    protected void makeVisible(@NonNull Activity activity) {
        isVisible = true;

        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup.addView(customView);
        } else {
            ViewGroup.LayoutParams params = customView.getLayoutParams();
            if (null == params) {
                params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            if (activity.isFinishing()) {
                return;
            }

            activity.addContentView(customView, params);
        }

        if (showAnimations) {
            customView.startAnimation(getInAnimation(activity));
        }
    }

    @Override
    public void hide() {
        isVisible = false;

        final ViewManager manager = (ViewManager) customView.getParent();
        if (manager != null) {
            if (showAnimations) {
                customView.startAnimation(getOutAnimation());
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    manager.removeView(customView);
                }
            }, animation.getHideDuration());
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    private Animation getInAnimation(@NonNull Activity activity) {
        if (inAnimation == null) {
            measureView(activity);
            this.inAnimation = animation.buildShowAnimation(customView);
        }
        return inAnimation;
    }

    private Animation getOutAnimation() {
        if (this.outAnimation == null) {
            this.outAnimation = animation.buildHideAnimation(customView);
        }
        return outAnimation;
    }

    private void measureView(@NonNull Activity activity) {
        int widthSpec;
        if (null != viewGroup) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getMeasuredWidth(), View.MeasureSpec.AT_MOST);
        } else {
            widthSpec = View.MeasureSpec.makeMeasureSpec(activity.getWindow().getDecorView().getMeasuredWidth(),
                    View.MeasureSpec.AT_MOST);
        }
        customView.measure(widthSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }
}
