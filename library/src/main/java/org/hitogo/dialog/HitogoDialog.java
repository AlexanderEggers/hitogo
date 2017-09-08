package org.hitogo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import org.hitogo.core.button.HitogoButton;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoUtils;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialog extends HitogoObject<HitogoDialogParams> {

    private AlertDialog dialog;

    @Override
    protected void onCreate(@NonNull HitogoDialogParams params) {
        List<HitogoButton> buttonList = params.getCallToActionButtons();
        Context context = params.getContext();
        Integer themeResId = params.getDialogThemeResId();

        AlertDialog.Builder dialogBuilder;
        if (themeResId != null) {
            dialogBuilder = new AlertDialog.Builder(context, themeResId);
        } else {
            dialogBuilder = new AlertDialog.Builder(context);
        }

        dialogBuilder.setMessage(params.getText())
                .setTitle(params.getTitle())
                .setCancelable(!params.isDismissible());

        final HitogoButton positiveButton = buttonList.get(0);
        if (positiveButton.getText() != null && HitogoUtils.isNotEmpty(positiveButton.getText())) {
            dialogBuilder.setPositiveButton(positiveButton.getText(), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    positiveButton.getListener().onClick();
                    close();
                }
            });
        }

        if (buttonList.size() > 1) {
            final HitogoButton negativeButton = buttonList.get(1);

            if (negativeButton.getText() != null && HitogoUtils.isNotEmpty(negativeButton.getText())) {
                dialogBuilder.setNegativeButton(negativeButton.getText(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        negativeButton.getListener().onClick();
                        close();
                    }
                });
            }
        }

        if (buttonList.size() > 2) {
            final HitogoButton neutralButton = buttonList.get(2);

            if (neutralButton.getText() != null && HitogoUtils.isNotEmpty(neutralButton.getText())) {
                dialogBuilder.setNeutralButton(neutralButton.getText(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        neutralButton.getListener().onClick();
                        close();
                    }
                });
            }
        }

        this.dialog = dialogBuilder.create();
    }

    @Override
    protected void onAttach(@NonNull Activity activity) {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onDetachDefault() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
