package com.mordag.crouton;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.Animation;

final class CroutonView extends CroutonObject {
    private CroutonAnimation animation;
    private final View customView;
    private ViewGroup viewGroup;
    private Animation inAnimation;
    private Animation outAnimation;
    private boolean showAnimations;
    private boolean isVisible = false;
    private int hashCode;

    private CroutonView(View customView, ViewGroup viewGroup, boolean showAnimations, int hashCode,
                        CroutonController controller, CroutonAnimation animation) {
        super(controller);
        this.customView = customView;
        this.viewGroup = viewGroup;
        this.showAnimations = showAnimations;
        this.hashCode = hashCode;

        if (animation != null) {
            this.animation = animation;
        } else {
            this.animation = new CroutonDefaultAnimation();
        }
    }

    static CroutonView make(View customView, ViewGroup viewGroup, boolean showAnimations,
                            int hashCode, CroutonController controller,
                            CroutonAnimation animation) {
        return new CroutonView(customView, viewGroup, showAnimations, hashCode, controller, animation);
    }

    @Override
    protected void makeVisible(Activity activity) {
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

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof CroutonObject && this.hashCode == obj.hashCode();
    }

    private Animation getInAnimation(Activity activity) {
        if (inAnimation == null) {
            measureCroutonView(activity);
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

    private void measureCroutonView(Activity activity) {
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
