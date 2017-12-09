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

import org.hitogo.BuildConfig;
import org.hitogo.button.action.ActionButton;
import org.hitogo.button.core.Button;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.core.HitogoUtils;
import org.hitogo.alert.view.ViewAlertBuilder;

import java.security.InvalidParameterException;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogAlertImpl extends AlertImpl<DialogAlertParams> implements DialogAlert {

    @Override
    protected void onCheck(@NonNull DialogAlertParams params) {
        if (params.getTitle() == null || params.getTitle().isEmpty()) {
            Log.i(ViewAlertBuilder.class.getName(), "Title parameter is empty.");
        }

        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this dialog.");
        }

        if (params.getButtons().isEmpty()) {
            throw new InvalidParameterException("This dialog needs at least one button.");
        }

        if (params.getState() == null && params.getLayoutRes() == null) {
            Log.i(ViewAlertBuilder.class.getName(), "State and custom layout is null. This " +
                    "dialog will use the default alert dialog implementation instead.");
        }
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(@Nullable LayoutInflater inflater, @NonNull Context context,
                                    @NonNull DialogAlertParams params) {
        List<Button> buttonList = params.getButtons();
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
                                               List<Button> buttonList) {
        View view = null;
        if(getParams().getLayoutRes() != null && getParams().getLayoutRes() != 0) {
            view = inflater.inflate(getParams().getLayoutRes(), null);
        } else if (getParams().getState() != null) {
            view = inflater.inflate(getController().provideDialogLayout(getParams().getState()), null);
        }
        builder.setCancelable(!getParams().isDismissible());

        if (view != null && getParams().getTitleViewId() != null) {
            ((TextView) view.findViewById(getParams().getTitleViewId())).setText(getParams().getTitle());
        } else {
            builder.setTitle(getParams().getTitle());
        }

        SparseArray<String> textMap = getParams().getTextMap();
        if (view != null) {
            for(int i = 0; i < textMap.size(); i++) {
                Integer viewId = textMap.keyAt(i);
                String text = textMap.valueAt(i);
                ((TextView) view.findViewById(viewId)).setText(text);
            }
        } else {
            builder.setMessage(textMap.valueAt(0));
        }

        ActionButton testButton = (ActionButton) buttonList.get(0);
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
        for (Button buttonObject : getParams().getButtons()) {
            final ActionButton callToActionButton = (ActionButton) buttonObject;

            View button = dialogView.findViewById(callToActionButton.getParams().getViewIds()[0]);
            if (button != null) {
                if (button instanceof TextView) {
                    HitogoUtils.getText(callToActionButton.getParams().getText());
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
            } else if(BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
                throw new InvalidParameterException("Did you forget to add the " +
                        "call-to-action button to your layout?");
            }
        }
    }

    private void generateDefaultButtons(AlertDialog.Builder builder, List<Button> buttonList) {
        final ActionButton positiveButton = (ActionButton) buttonList.get(0);
        if (HitogoUtils.isNotEmpty(positiveButton.getParams().getText())) {
            builder.setPositiveButton(positiveButton.getParams().getText(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    positiveButton.getParams().getListener().onClick();
                    close();
                }
            });
        }

        if (buttonList.size() > 1) {
            final ActionButton negativeButton = (ActionButton) buttonList.get(1);
            if (HitogoUtils.isNotEmpty(negativeButton.getParams().getText())) {
                builder.setNegativeButton(negativeButton.getParams().getText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        negativeButton.getParams().getListener().onClick();
                        close();
                    }
                });
            }
        }

        if (buttonList.size() > 2) {
            final ActionButton neutralButton = (ActionButton) buttonList.get(2);
            if (HitogoUtils.isNotEmpty(neutralButton.getParams().getText())) {
                builder.setNeutralButton(neutralButton.getParams().getText(), new DialogInterface.OnClickListener() {
                    @Override
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
