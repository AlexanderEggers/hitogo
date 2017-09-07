package org.hitogo.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import org.hitogo.core.HitogoObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoView extends HitogoObject {
    private HitogoAnimation animation;
    private final View customView;
    private ViewGroup viewGroup;
    private Integer layoutViewId;
    private boolean showAnimations;
    private boolean isVisible;
    private boolean isGone;

    HitogoView(HitogoViewParams params) {
        super(params);
        this.customView = params.getHitogoView();
        this.viewGroup = params.getHitogoContainer();
        this.showAnimations = params.shouldShowAnimation();
        this.layoutViewId = params.getLayoutViewId();

        if (animation != null) {
            this.animation = params.getHitogoAnimation();
        } else if (controller.getDefaultAnimation() != null) {
            this.animation = controller.getDefaultAnimation();
        } else {
            this.animation = new HitogoTopAnimation();
        }
    }

    @Override
    protected void makeVisible(@NonNull Activity activity) {
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup.addView(customView);
        } else {
            if (activity.isFinishing()) {
                return;
            }
            activity.addContentView(customView, null);
        }

        if (showAnimations) {
            measureView(activity);
            animation.showAnimation(customView, this);
        }
    }

    @Override
    public void makeInvisible() {
        final ViewManager manager = (ViewManager) customView.getParent();
        if (manager != null) {
            if (showAnimations) {
                animation.hideAnimation(customView, this, manager);
            } else {
                isGone = false;
                isVisible = false;
                manager.removeView(customView);
            }
        }
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

    @Override
    public boolean isGone() {
        return isGone;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisibility(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setGone(boolean isGone) {
        this.isGone = isGone;
    }

    @Override
    public long getAnimationDuration() {
        return animation.getAnimationDuration();
    }

    @Nullable
    @Override
    public Integer getLayoutViewId() {
        return layoutViewId;
    }
}
