package org.hitogo.examples;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.view.ViewAlertParams;

public class TestView extends AlertImpl<ViewAlertParams> {

    @Nullable
    @Override
    protected View onCreateView(LayoutInflater inflater, @NonNull Context context,
                                @NonNull ViewAlertParams params) {
        View v = inflater.inflate(getController().provideViewLayout(params.getState()), null);
        ((TextView) v.findViewById(R.id.text)).setText(params.getTextMap().valueAt(0));
        return v;
    }

    @Override
    protected void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ViewGroup.LayoutParams params = getView().getLayoutParams();
        if (params == null) {
            params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        if (((Activity) context).isFinishing()) {
            return;
        }
        ((Activity) context).addContentView(getView(), params);
    }
}
