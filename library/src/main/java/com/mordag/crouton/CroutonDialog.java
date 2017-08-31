package com.mordag.crouton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.List;

final class CroutonDialog extends CroutonObject {

    private AlertDialog dialog;
    private int hashCode;

    private CroutonDialog(Context context, String infoTitle, String infoText,
                          List<CroutonButton> buttonList, int hashCode, CroutonController controller) {
        super(controller);
        this.hashCode = hashCode;

        final CroutonButton positiveButton = buttonList.get(0);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setMessage(infoText)
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
            final CroutonButton negativeButton = buttonList.get(1);

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
            final CroutonButton neutralButton = buttonList.get(2);

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

    public static CroutonDialog create(Context context, String infoTitle, String infoText,
                                       List<CroutonButton> buttonList, int hashCode,
                                       CroutonController controller) {
        return new CroutonDialog(context, infoTitle, infoText, buttonList, hashCode, controller);
    }

    @Override
    void makeVisible(Activity activity) {
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
    public boolean equals(Object obj) {
        return obj != null && obj instanceof CroutonObject && this.hashCode == obj.hashCode();
    }
}
