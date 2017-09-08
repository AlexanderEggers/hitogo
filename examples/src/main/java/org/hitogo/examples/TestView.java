package org.hitogo.examples;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoObject;
import org.hitogo.view.HitogoViewParams;

public class TestView extends HitogoObject<HitogoViewParams> {

    private View customView;

    @Override
    protected void onCreate(@NonNull HitogoViewParams params, @NonNull HitogoController controller) {
        super.onCreate(params, controller);
        this.customView = params.getHitogoView();
    }

    @Override
    protected void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

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
}
