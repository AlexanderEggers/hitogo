package com.mordag.hitogo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
final class HitogoDialog extends HitogoObject {

    private AlertDialog dialog;
    private int hashCode;

    private HitogoDialog(Context context, String infoTitle, String infoText,
                         List<HitogoButton> buttonList, int hashCode, HitogoController controller,
                         @StyleRes Integer themeResId) {
        super(controller);
        this.hashCode = hashCode;

        final HitogoButton positiveButton = buttonList.get(0);

        AlertDialog.Builder dialogBuilder;
        if(themeResId != null) {
            dialogBuilder = new AlertDialog.Builder(context, themeResId);
        } else {
            dialogBuilder = new AlertDialog.Builder(context);
        }

        dialogBuilder.setMessage(infoText)
                .setTitle(infoTitle)
                .setCancelable(false);

        if(StringUtils.isNotEmpty(positiveButton.text)) {
            dialogBuilder.setPositiveButton(positiveButton.text, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    positiveButton.listener.onClick();
                    hide();
                }
            });
        }

        if(buttonList.size() > 1) {
            final HitogoButton negativeButton = buttonList.get(1);

            if(StringUtils.isNotEmpty(negativeButton.text)) {
                dialogBuilder.setNegativeButton(positiveButton.text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        negativeButton.listener.onClick();
                        hide();
                    }
                });
            }
        }

        if(buttonList.size() > 2) {
            final HitogoButton neutralButton = buttonList.get(2);

            if(StringUtils.isNotEmpty(neutralButton.text)) {
                dialogBuilder.setNeutralButton(positiveButton.text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        neutralButton.listener.onClick();
                        hide();
                    }
                });
            }
        }

        this.dialog = dialogBuilder.create();
    }

    public static HitogoDialog create(Context context, String infoTitle, String infoText,
                                      List<HitogoButton> buttonList, int hashCode,
                                      HitogoController controller, @StyleRes Integer themeResId) {
        return new HitogoDialog(context, infoTitle, infoText, buttonList, hashCode, controller,
                themeResId);
    }

    @Override
    protected void makeVisible(@NonNull Activity activity) {
        if(!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void hide() {
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean isVisible() {
        return dialog.isShowing();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(@NonNull Object obj) {
        return obj instanceof HitogoObject && this.hashCode == obj.hashCode();
    }
}
