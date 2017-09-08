package org.hitogo.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import org.hitogo.core.HitogoAnimation;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoUtils;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoView extends HitogoObject<HitogoViewParams> {

    private View customView;
    private ViewGroup viewGroup;
    private HitogoAnimation animation;
    private HitogoViewParams params;

    @Override
    protected void onCreate(@NonNull HitogoViewParams params, @NonNull HitogoController controller) {
        super.onCreate(params, controller);

        this.params = params;
        this.animation = params.getHitogoAnimation();
        if (animation == null) {
            this.animation = controller.getDefaultAnimation();
        }

        this.customView = params.getHitogoView();
        this.viewGroup = params.getHitogoContainer();
    }

    @Override
    protected void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup.addView(customView);
        } else {
            ViewGroup.LayoutParams layoutParams = customView.getLayoutParams();
            if (null == params) {
                layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            if (activity.isFinishing()) {
                return;
            }
            activity.addContentView(customView, layoutParams);
        }
    }

    @Override
    protected void onShowAnimation(Activity activity) {
        super.onShowAnimation(activity);
        HitogoUtils.measureView(activity, customView, viewGroup);
        animation.showAnimation(params, customView);
    }

    @Override
    protected void onDetachDefault() {
        super.onDetachDefault();
        final ViewManager manager = (ViewManager) customView.getParent();
        manager.removeView(customView);
    }

    @Override
    protected void onDetachAnimation() {
        super.onDetachAnimation();
        animation.hideAnimation(params, customView, this);
    }
}
