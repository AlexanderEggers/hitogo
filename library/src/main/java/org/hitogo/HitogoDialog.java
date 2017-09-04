package org.hitogo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
final class HitogoDialog extends HitogoObject {

    private AlertDialog dialog;

    private HitogoDialog(HitogoBuilder builder) {
        super(builder.controller, builder.hashCode);

        List<HitogoButton> buttonList = builder.callToActionButtons;
        Context context = builder.context;
        Integer themeResId = builder.dialogThemeResId;

        final HitogoButton positiveButton = buttonList.get(0);

        AlertDialog.Builder dialogBuilder;
        if (themeResId != null) {
            dialogBuilder = new AlertDialog.Builder(context, themeResId);
        } else {
            dialogBuilder = new AlertDialog.Builder(context);
        }

        dialogBuilder.setMessage(builder.text)
                .setTitle(builder.title)
                .setCancelable(!builder.isDismissible);

        if (positiveButton.text != null && StringUtils.isNotEmpty(positiveButton.text)) {
            dialogBuilder.setPositiveButton(positiveButton.text, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    positiveButton.listener.onClick();
                    hide();
                }
            });
        }

        if (buttonList.size() > 1) {
            final HitogoButton negativeButton = buttonList.get(1);

            if (negativeButton.text != null && StringUtils.isNotEmpty(negativeButton.text)) {
                dialogBuilder.setNegativeButton(positiveButton.text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        negativeButton.listener.onClick();
                        hide();
                    }
                });
            }
        }

        if (buttonList.size() > 2) {
            final HitogoButton neutralButton = buttonList.get(2);

            if (neutralButton.text != null && StringUtils.isNotEmpty(neutralButton.text)) {
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

    static HitogoDialog create(HitogoBuilder builder) {
        return new HitogoDialog(builder);
    }

    @Override
    protected void makeVisible(@NonNull Activity activity) {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void hide() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean isVisible() {
        return dialog.isShowing();
    }
}
