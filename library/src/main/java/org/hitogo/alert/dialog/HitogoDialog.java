package org.hitogo.alert.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.hitogo.button.action.HitogoAction;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.core.HitogoUtils;
import org.hitogo.alert.view.HitogoViewBuilder;

import java.security.InvalidParameterException;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialog extends HitogoAlert<HitogoDialogParams> {

    private HitogoDialogParams params;

    @Override
    protected void onCheck(@NonNull HitogoDialogParams params) {
        if (params.getTitle() != null || params.getTitle().isEmpty()) {
            Log.i(HitogoViewBuilder.class.getName(), "Title parameter is empty.");
        }

        if (params.getTextMap() == null || params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this dialog.");
        }

        if (params.getButtons().isEmpty()) {
            throw new InvalidParameterException("This hitogo needs at least one button.");
        }

        if (params.getState() == null) {
            Log.i(HitogoViewBuilder.class.getName(), "State is null. This dialog will use the " +
                    "default alert dialog implementation instead.");
        }
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(@NonNull LayoutInflater inflater, @NonNull Context context,
                                    @NonNull HitogoDialogParams params) {
        this.params = params;
        List<HitogoButton> buttonList = params.getButtons();
        Integer themeResId = params.getDialogThemeResId();

        AlertDialog.Builder dialogBuilder;
        if (themeResId != null) {
            dialogBuilder = new AlertDialog.Builder(context, themeResId);
        } else {
            dialogBuilder = new AlertDialog.Builder(context);
        }

        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                close();
            }
        });

        return generateDialog(dialogBuilder, inflater, buttonList).create();
    }

    @NonNull
    private AlertDialog.Builder generateDialog(AlertDialog.Builder builder, LayoutInflater inflater,
                                               List<HitogoButton> buttonList) {
        View view = null;
        if (params.getState() != null) {
            view = inflater.inflate(getController().provideDialogLayout(params.getState()), null);
        }
        builder.setCancelable(!params.isDismissible());

        if (view != null && params.getTitleViewId() != null) {
            ((TextView) view.findViewById(params.getTitleViewId())).setText(params.getTitle());
        } else {
            builder.setTitle(params.getTitle());
        }

        SparseArray<String> textMap = params.getTextMap();
        if (view != null) {
            for(int i = 0; i < textMap.size(); i++) {
                Integer viewId = textMap.keyAt(i);
                String text = textMap.valueAt(i);
                ((TextView) view.findViewById(viewId)).setText(text);
            }
        } else {
            builder.setMessage(textMap.valueAt(0));
        }

        HitogoAction testButton = (HitogoAction) buttonList.get(0);
        if (view != null && testButton.getParams().hasActionView()) {
            buildCallToActionButtons(view);
        } else {
            generateDefaultButtons(builder, buttonList);
        }

        if (view != null) {
            builder.setView(view);
        }

        return builder;
    }

    private void buildCallToActionButtons(@NonNull View dialogView) {
        for (HitogoButton buttonObject : params.getButtons()) {
            final HitogoAction callToActionButton = (HitogoAction) buttonObject;

            View button = dialogView.findViewById(callToActionButton.getParams().getViewIds()[0]);
            if (button != null) {
                if (button instanceof TextView) {
                    ((TextView) button).setText(callToActionButton.getParams().getText() != null ?
                            callToActionButton.getParams().getText() : "");
                }

                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callToActionButton.getParams().getListener().onClick();
                        if (callToActionButton.getParams().isClosingAfterClick()) {
                            close();
                        }
                    }
                });
            } else {
                throw new InvalidParameterException("Did you forget to add the " +
                        "call-to-action button to your layout?");
            }
        }
    }

    private void generateDefaultButtons(AlertDialog.Builder builder, List<HitogoButton> buttonList) {
        final HitogoAction positiveButton = (HitogoAction) buttonList.get(0);
        if (positiveButton.getParams().getText() != null && HitogoUtils.isNotEmpty(
                positiveButton.getParams().getText())) {
            builder.setPositiveButton(positiveButton.getParams().getText(), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    positiveButton.getParams().getListener().onClick();
                    close();
                }
            });
        }

        if (buttonList.size() > 1) {
            final HitogoAction negativeButton = (HitogoAction) buttonList.get(1);
            if (negativeButton.getParams().getText() != null && HitogoUtils.isNotEmpty(
                    negativeButton.getParams().getText())) {
                builder.setNegativeButton(negativeButton.getParams().getText(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        negativeButton.getParams().getListener().onClick();
                        close();
                    }
                });
            }
        }

        if (buttonList.size() > 2) {
            final HitogoAction neutralButton = (HitogoAction) buttonList.get(2);
            if (neutralButton.getParams().getText() != null && HitogoUtils.isNotEmpty(
                    neutralButton.getParams().getText())) {
                builder.setNeutralButton(neutralButton.getParams().getText(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        neutralButton.getParams().getListener().onClick();
                        close();
                    }
                });
            }
        }
    }

    @Override
    protected void onAttach(@NonNull Context context) {
        if (getDialog() != null && !getDialog().isShowing()) {
            getDialog().show();
        }
    }

    @Override
    public void onCloseDefault(@NonNull Context context) {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }
}
