package org.hitogo.examples;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.hitogo.core.HitogoObject;
import org.hitogo.view.HitogoViewParams;

public class TestView extends HitogoObject<HitogoViewParams> {

    @Nullable
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull Activity activity,
                                @NonNull HitogoViewParams params) {
        View v = inflater.inflate(getController().provideViewLayout(params.getState()), null);
        ((TextView) v.findViewById(R.id.text)).setText(params.getText());
        return v;
    }

    @Override
    protected void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        ViewGroup.LayoutParams params = getView().getLayoutParams();
        if (params == null) {
            params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        if (activity.isFinishing()) {
            return;
        }
        activity.addContentView(getView(), params);
    }
}
