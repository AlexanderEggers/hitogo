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

import org.hitogo.button.core.Button;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.core.HitogoUtils;
import org.hitogo.alert.view.ViewAlertBuilderImpl;

import java.security.InvalidParameterException;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public class DialogAlertImpl extends AlertImpl<DialogAlertParams> implements DialogAlert {

    private static final int MAX_BUILDER_BUTTON_AMOUNT = 3;
    private static final int PRIMARY_BUTTON = 0;
    private static final int SECONDARY_BUTTON = 1;
    private static final int NEUTRAL_BUTTON = 2;

    private int dialogButtonCount;

    @Override
    protected void onCheck(@NonNull DialogAlertParams params) {
        super.onCheck(params);

        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this dialog.");
        }

        if (params.getButtons().isEmpty()) {
            throw new InvalidParameterException("This dialog needs at least one button.");
        }

        if (params.getState() == null && params.getLayoutRes() == null) {
            Log.i(ViewAlertBuilderImpl.class.getName(), "State and custom layout is null. This " +
                    "dialog will use the default alert dialog implementation instead.");
        }
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(@NonNull LayoutInflater inflater, @NonNull Context context,
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
        if (getParams().getLayoutRes() != null && getParams().getLayoutRes() != 0) {
            view = inflater.inflate(getParams().getLayoutRes(), null);
        } else if (getParams().getState() != null && getController().provideDialogLayout(getParams().getState()) != null) {
            view = inflater.inflate(getController().provideDialogLayout(getParams().getState()), null);
        }
        builder.setCancelable(getParams().isDismissible());

        if (HitogoUtils.isNotEmpty(getParams().getTitle())) {
            setDialogTitle(view, builder);
        }

        SparseArray<String> textMap = getParams().getTextMap();
        if (view != null) {
            for (int i = 0; i < textMap.size(); i++) {
                Integer viewId = textMap.keyAt(i);
                String text = textMap.valueAt(i);
                setViewString(view, viewId, text);
            }
        } else if (textMap.size() > 0) {
            builder.setMessage(textMap.valueAt(0));
        }

        for (Button button : buttonList) {
            determineButtonCreation(button, view, builder);
        }

        if (getParams().getCloseButton() != null) {
            determineButtonCreation(getParams().getCloseButton(), view, builder);
        }

        if (view != null) {
            builder.setView(view);
        }

        return builder;
    }

    private void setDialogTitle(View containerView, AlertDialog.Builder builder) {
        if (containerView != null && getParams().getTitleViewId() != null) {
            ((TextView) containerView.findViewById(getParams().getTitleViewId()))
                    .setText(HitogoUtils.getHtmlText(getParams().getTitle()));
        } else {
            builder.setTitle(getParams().getTitle());
        }
    }

    private void setViewString(@NonNull View containerView, @Nullable Integer viewId,
                               @Nullable String chars) {
        if (viewId != null) {
            TextView textView = containerView.findViewById(viewId);
            if (textView != null) {
                if (HitogoUtils.isNotEmpty(chars)) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(HitogoUtils.getHtmlText(chars));
                } else {
                    textView.setVisibility(View.GONE);
                }
            } else if (getController().provideIsDebugState()) {
                throw new InvalidParameterException("Did you forget to add the view to your layout?");
            }
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("View id is null.");
        }
    }

    private void determineButtonCreation(Button button, @Nullable View dialogView, AlertDialog.Builder builder) {
        if (button.getParams().hasButtonView()) {
            if (dialogView != null) {
                buildActionButton(button, dialogView);
            } else if (getController().provideIsDebugState()) {
                throw new InvalidParameterException("The button cannot be attached to a null view. " +
                        "Check your dialog or button builder.");
            }
        } else {
            buildDialogButton(button, builder);
        }
    }

    private void buildActionButton(final Button button, View view) {
        if (button != null) {
            final View icon = view.findViewById(button.getParams().getViewIds()[0]);
            View click = view.findViewById(button.getParams().getViewIds()[1]);

            if (click == null) {
                click = icon;
            }

            if (icon != null) {
                if (icon instanceof TextView && HitogoUtils.isNotEmpty(button.getParams().getText())) {
                    ((TextView) icon).setText(HitogoUtils.getHtmlText(button.getParams().getText()));
                }

                icon.setVisibility(View.VISIBLE);
                click.setVisibility(View.VISIBLE);
                click.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        button.getParams().getListener().onClick(DialogAlertImpl.this, button.getParams().getButtonParameter());
                        close();
                    }
                });
            } else if (getController().provideIsDebugState()) {
                throw new InvalidParameterException("Did you forget to add the button to your layout?");
            }
        }
    }

    private void buildDialogButton(final Button button, AlertDialog.Builder builder) {
        if (HitogoUtils.isNotEmpty(button.getParams().getText())) {
            switch (dialogButtonCount) {
                case PRIMARY_BUTTON:
                    builder.setPositiveButton(button.getParams().getText(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            button.getParams().getListener().onClick(DialogAlertImpl.this, button.getParams().getButtonParameter());
                            close();
                        }
                    });
                    break;
                case SECONDARY_BUTTON:
                    builder.setNegativeButton(button.getParams().getText(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            button.getParams().getListener().onClick(DialogAlertImpl.this, button.getParams().getButtonParameter());
                            close();
                        }
                    });
                    break;
                case NEUTRAL_BUTTON:
                    builder.setNeutralButton(button.getParams().getText(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            button.getParams().getListener().onClick(DialogAlertImpl.this, button.getParams().getButtonParameter());
                            close();
                        }
                    });
                    break;
                default:
                    break;
            }
            dialogButtonCount++;

            if (dialogButtonCount >= MAX_BUILDER_BUTTON_AMOUNT && getController().provideIsDebugState()) {
                throw new InvalidParameterException("Dialog only supports up to three " +
                        "different builder buttons (primary, secondary and neutral)!");
            }
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("Empty button text cannot be added to the dialog.");
        }
    }

    @Override
    protected void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (getDialog() != null && !getDialog().isShowing()) {
            getDialog().show();
        }
    }

    @Override
    public void onCloseDefault(@NonNull Context context) {
        super.onCloseDefault(context);

        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }
}
