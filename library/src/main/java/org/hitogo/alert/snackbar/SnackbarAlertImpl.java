package org.hitogo.alert.snackbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.button.core.Button;

import java.security.InvalidParameterException;

@SuppressWarnings("unchecked")
public class SnackbarAlertImpl extends AlertImpl<SnackbarAlertParams> implements SnackbarAlert {

    @Override
    protected void onCheck(@NonNull SnackbarAlertParams params) {
        super.onCheck(params);

        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this snackbar.");
        }

        if (params.getTextMap().size() > 1) {
            Log.i(SnackbarAlertImpl.class.getName(),
                    "The snackbar alert only supports one text element.");
        }
    }

    @Override
    protected Object onCreateOther(@NonNull LayoutInflater inflater, @NonNull Context context, @NonNull SnackbarAlertParams params) {
        Snackbar snackbar = Snackbar.make(getRootView(),
                params.getTextMap().valueAt(0),
                params.getDuration());

        if(!params.getButtons().isEmpty()) {
            final Button button = params.getButtons().get(0);

            if(button != null) {
                snackbar.setAction(button.getParams().getText(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.getParams().getListener().onClick(SnackbarAlertImpl.this,
                                button.getParams().getButtonParameter());

                        if(button.getParams().isClosingAfterClick()) {
                            close();
                        }
                    }
                });
            }
        }

        if(params.getActionTextColor() != null) {
            snackbar.setActionTextColor(params.getActionTextColor());
        }

        if(params.getColorStates() != null) {
            snackbar.setActionTextColor(params.getActionTextColor());
        }

        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {

            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                close();
            }
        });

        Snackbar.Callback callback = getParams().getSnackbarCallback();
        if(callback != null) {
            snackbar.addCallback(callback);
        }

        return snackbar;
    }

    @Override
    protected void onShowDefault(@NonNull Context context) {
        super.onShowDefault(context);
        ((Snackbar) getOther()).show();
    }

    @Override
    protected void onCloseDefault(@NonNull Context context) {
        super.onCloseDefault(context);
        ((Snackbar) getOther()).dismiss();
    }
}
