package org.hitogo.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoView extends HitogoObject {
    private HitogoAnimation animation;
    private final View customView;
    private ViewGroup viewGroup;
    private boolean showAnimations;
    private Integer layoutViewId;

    HitogoView(HitogoViewBuilder builder, View customView, ViewGroup viewGroup) {
        super(builder.controller, builder.hashCode);
        this.customView = customView;
        this.viewGroup = viewGroup;
        this.showAnimations = builder.showAnimation;
        this.layoutViewId = builder.layoutViewId;

        if (animation != null) {
            this.animation = builder.hitogoAnimation;
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
                isVisible = false;
                manager.removeView(customView);
            }
        }
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

    @NonNull
    public static HitogoViewBuilder with(@NonNull HitogoContainer container) {
        if(container.getView() == null) {
            Log.d(HitogoView.class.getName(), "Something went wrong with the getView() method " +
                    "for this hitogo container. Are you sure that you attached the view correctly?");
        }
        return new HitogoViewBuilder(container.getActivity(), container.getView(),
                container.getController());
    }
}
