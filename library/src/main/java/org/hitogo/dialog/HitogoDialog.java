package org.hitogo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.hitogo.button.HitogoButton;
import org.hitogo.button.HitogoButtonObject;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoUtils;
import org.hitogo.view.HitogoViewBuilder;

import java.security.InvalidParameterException;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialog extends HitogoObject<HitogoDialogParams> {

    private HitogoDialogParams params;

    @Override
    protected void onCheck(@NonNull HitogoDialogParams params) {
        if (params.getText() == null) {
            throw new InvalidParameterException("Text parameter cannot be null.");
        }

        if (params.getTitle() == null) {
            throw new InvalidParameterException("Title parameter cannot be null.");
        }

        if (params.getCallToActionButtons().isEmpty()) {
            throw new InvalidParameterException("This hitogo needs at least one button.");
        }

        if (params.getState() == null) {
            Log.i(HitogoViewBuilder.class.getName(), "State is null. This dialog will use the " +
                    "default alert dialog implementation instead.");
        }

        if (params.getCallToActionButtons().size() > 3) {
            Log.d(HitogoViewBuilder.class.getName(), "The dialog can handle only up to 3 different " +
                    "buttons. The other buttons won't be used by this alert.");
        }
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(@NonNull LayoutInflater inflater, @NonNull Activity activity,
                                    @NonNull HitogoDialogParams params) {
        this.params = params;
        List<HitogoButtonObject> buttonList = params.getCallToActionButtons();
        Integer themeResId = params.getDialogThemeResId();

        AlertDialog.Builder dialogBuilder;
        if (themeResId != null) {
            dialogBuilder = new AlertDialog.Builder(activity, themeResId);
        } else {
            dialogBuilder = new AlertDialog.Builder(activity);
        }

        return generateDialog(dialogBuilder, inflater, buttonList).create();
    }

    @NonNull
    private AlertDialog.Builder generateDialog(AlertDialog.Builder builder, LayoutInflater inflater,
                                               List<HitogoButtonObject> buttonList) {
        View view = null;
        if (params.getState() != null) {
            view = inflater.inflate(getController().provideDialogLayout(params.getState()), null);
        }
        builder.setCancelable(!params.isDismissible());

        if (view != null && params.getTitleViewId() != null) {
            ((TextView) view.findViewById(params.getTitleViewId())).setText(params.getText());
        } else {
            builder.setTitle(params.getTitle());
        }

        if (view != null && params.getTextViewId() != null) {
            ((TextView) view.findViewById(params.getTextViewId())).setText(params.getText());
        } else {
            builder.setMessage(params.getText());
        }

        HitogoButton testButton = (HitogoButton) buttonList.get(0);
        if (view != null && testButton.getParams().hasButtonView()) {
            view = buildCallToActionButtons(view);
            builder.setView(view);
        } else {
            generateDefaultButtons(builder, buttonList);
        }

        return builder;
    }

    @NonNull
    private View buildCallToActionButtons(@NonNull View dialogView) {
        for (HitogoButtonObject buttonObject : params.getCallToActionButtons()) {
            final HitogoButton callToActionButton = (HitogoButton) buttonObject;

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
                        if (callToActionButton.getParams().isClosingAfterExecute()) {
                            close();
                        }
                    }
                });
            } else {
                throw new InvalidParameterException("Did you forget to add the " +
                        "call-to-action button to your layout?");
            }
        }

        return dialogView;
    }

    @NonNull
    private AlertDialog.Builder generateDefaultButtons(AlertDialog.Builder builder,
                                                       List<HitogoButtonObject> buttonList) {

        final HitogoButton positiveButton = (HitogoButton) buttonList.get(0);
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
            final HitogoButton negativeButton = (HitogoButton) buttonList.get(1);

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
            final HitogoButton neutralButton = (HitogoButton) buttonList.get(2);

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

        return builder;
    }

    @Override
    protected void onAttach(@NonNull Activity activity) {
        if (getDialog() != null && !getDialog().isShowing()) {
            getDialog().show();
        }
    }

    @Override
    public void onCloseDefault(@NonNull Activity activity) {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }
}
