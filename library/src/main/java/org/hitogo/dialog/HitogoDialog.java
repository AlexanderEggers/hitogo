package org.hitogo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.hitogo.button.HitogoButton;
import org.hitogo.button.HitogoButtonObject;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoUtils;
import org.hitogo.view.HitogoViewBuilder;

import java.security.InvalidParameterException;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialog extends HitogoObject<HitogoDialogParams> {

    @Override
    protected void onCheckStart(@NonNull HitogoDialogParams params) {
        if (params.getText() == null) {
            throw new InvalidParameterException("Text parameter cannot be null.");
        }

        if (params.getTitle() == null) {
            throw new InvalidParameterException("Title parameter cannot be null.");
        }

        if (params.getCallToActionButtons().isEmpty()) {
            throw new InvalidParameterException("This hitogo need at least one button.");
        }

        if (params.getCallToActionButtons().size() > 3) {
            Log.d(HitogoViewBuilder.class.getName(), "The dialog can handle only up to 3 different buttons.");
        }
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(@NonNull Activity activity, @NonNull HitogoDialogParams params) {
        List<HitogoButtonObject> buttonList = params.getCallToActionButtons();
        Integer themeResId = params.getDialogThemeResId();

        AlertDialog.Builder dialogBuilder;
        if (themeResId != null) {
            dialogBuilder = new AlertDialog.Builder(activity, themeResId);
        } else {
            dialogBuilder = new AlertDialog.Builder(activity);
        }

        dialogBuilder.setMessage(params.getText())
                .setTitle(params.getTitle())
                .setCancelable(!params.isDismissible());

        final HitogoButton positiveButton = (HitogoButton) buttonList.get(0);
        if (positiveButton.getParams().getText() != null && HitogoUtils.isNotEmpty(
                positiveButton.getParams().getText())) {
            dialogBuilder.setPositiveButton(positiveButton.getParams().getText(), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    positiveButton.getParams().getListener().onClick();
                    close();
                }
            });
        }

        if (buttonList.size() > 1) {
            final HitogoButton negativeButton = (HitogoButton) buttonList.get(1);

            if (negativeButton.getParams().getText() != null && HitogoUtils.isNotEmpty(
                    negativeButton.getParams().getText())) {
                dialogBuilder.setNegativeButton(negativeButton.getParams().getText(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        negativeButton.getParams().getListener().onClick();
                        close();
                    }
                });
            }
        }

        if (buttonList.size() > 2) {
            final HitogoButton neutralButton = (HitogoButton) buttonList.get(2);

            if (neutralButton.getParams().getText() != null && HitogoUtils.isNotEmpty(
                    neutralButton.getParams().getText())) {
                dialogBuilder.setNeutralButton(neutralButton.getParams().getText(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        neutralButton.getParams().getListener().onClick();
                        close();
                    }
                });
            }
        }

        return dialogBuilder.create();
    }

    @Override
    protected void onAttach(@NonNull Activity activity) {
        if (!getDialog().isShowing()) {
            getDialog().show();
        }
    }

    @Override
    public void onCloseDefault(@NonNull Activity activity) {
        if (getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }
}
