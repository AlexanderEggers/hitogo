package org.hitogo.alert.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.view.ViewAlertBuilderImpl;
import org.hitogo.button.core.Button;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Implementation for the dialog alert. This alert requires to have at least one text element and
 * one button.
 *
 * @since 1.0.0
 */
@SuppressWarnings({"unchecked"})
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
            throw new InvalidParameterException("You need to add one text element to this dialog.");
        }

        if (params.getButtons().isEmpty() && params.getCloseButton() == null) {
            throw new InvalidParameterException("This dialog needs at least one button.");
        }

        if (params.getState() == null && params.getLayoutRes() == null) {
            Log.i(ViewAlertBuilderImpl.class.getName(), "State and custom layout is null. " +
                    "This dialog will use the default implementation instead.");
        }
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(LayoutInflater inflater, @NonNull Context context,
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

        buildLayoutContent(view, builder);

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

    protected void buildLayoutContent(View view, @NonNull AlertDialog.Builder builder) {
        if (getHelper().isNotEmpty(getParams().getTitle())) {
            if (view != null && getParams().getTitleViewId() != null) {
                ((TextView) view.findViewById(getParams().getTitleViewId()))
                        .setText(getAccessor().getHtmlText(getParams().getTitle()));
            } else {
                builder.setTitle(getParams().getTitle());
            }
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

        SparseArray<Drawable> drawableMap = getParams().getDrawableMap();
        if (view != null) {
            for (int i = 0; i < drawableMap.size(); i++) {
                Integer viewId = drawableMap.keyAt(i);
                Drawable drawable = drawableMap.valueAt(i);
                setDrawable(view, viewId, drawable);
            }
        } else if (textMap.size() > 0) {
            builder.setIcon(drawableMap.valueAt(0));
        }
    }

    protected void setDrawable(@NonNull View containerView, @Nullable Integer viewId,
                               @Nullable Drawable drawable) {
        if (viewId != null) {
            View view = containerView.findViewById(viewId);
            if (view != null) {
                if (drawable != null) {
                    view.setVisibility(View.VISIBLE);

                    if (view instanceof ImageView) {
                        ((ImageView) view).setImageDrawable(drawable);
                    } else {
                        view.setBackground(drawable);
                    }
                } else {
                    view.setVisibility(View.GONE);
                }
            } else if (getController().provideIsDebugState()) {
                throw new InvalidParameterException("Did you forget to add the view to your layout?");
            }
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("View id is null.");
        }
    }

    protected void setViewString(@NonNull View containerView, @Nullable Integer viewId,
                                 @Nullable String chars) {
        if (viewId != null) {
            TextView textView = containerView.findViewById(viewId);
            if (textView != null) {
                if (getHelper().isNotEmpty(chars)) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(getAccessor().getHtmlText(chars));
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

    protected void determineButtonCreation(Button button, @Nullable View dialogView, AlertDialog.Builder builder) {
        if (button.getParams().hasButtonView()) {
            if (dialogView != null) {
                buildButton(button, dialogView);
            } else if (getController().provideIsDebugState()) {
                throw new InvalidParameterException("The button cannot be attached to a null view. " +
                        "Check your dialog or button builder.");
            }
        } else {
            buildDialogButton(button, builder);
        }
    }

    protected void buildButton(final Button button, View view) {
        final View buttonLayout = view.findViewById(button.getParams().getIconId());
        View clickLayout = view.findViewById(button.getParams().getClickId());

        if (clickLayout == null) {
            clickLayout = buttonLayout;
        }

        if (buttonLayout != null) {
            SparseArray<String> textMap = button.getParams().getTextMap();
            for (int i = 0; i < textMap.size(); i++) {
                Integer viewId = textMap.keyAt(i);
                String text = textMap.valueAt(i);
                setButtonString(buttonLayout, viewId, text);
            }

            SparseArray<Drawable> drawableMap = button.getParams().getDrawableMap();
            for (int i = 0; i < drawableMap.size(); i++) {
                Integer viewId = drawableMap.keyAt(i);
                Drawable drawable = drawableMap.valueAt(i);
                setDrawable(buttonLayout, viewId, drawable);
            }

            buttonLayout.setVisibility(View.VISIBLE);
            clickLayout.setVisibility(View.VISIBLE);
            clickLayout.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    button.getParams().getListener().onClick(DialogAlertImpl.this, button.getParams().getButtonParameter());
                    if(isAttached()) {
                        close();
                    }
                }
            });
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("Did you forget to add the button to your layout?");
        }
    }

    protected void setButtonString(@NonNull View buttonLayout, @Nullable Integer viewId,
                                   @Nullable String chars) {
        TextView textView = null;
        if (viewId != null && viewId != -1) {
            textView = buttonLayout.findViewById(viewId);
        } else if (buttonLayout instanceof TextView) {
            textView = (TextView) buttonLayout;
        }

        if (textView != null) {
            if (getHelper().isNotEmpty(chars)) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(getAccessor().getHtmlText(chars));
            } else {
                textView.setVisibility(View.GONE);
            }
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("Either your button layout is not a text view or " +
                    "the subview for the text element could not be found. Make sure your button" +
                    "layout is including the TextView.");
        }
    }

    protected void buildDialogButton(final Button button, AlertDialog.Builder builder) {
        if (getHelper().isNotEmpty(button.getParams().getTextMap().valueAt(0))) {
            switch (dialogButtonCount) {
                case PRIMARY_BUTTON:
                    builder.setPositiveButton(button.getParams().getTextMap().valueAt(0), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            button.getParams().getListener().onClick(DialogAlertImpl.this, button.getParams().getButtonParameter());
                            if(isAttached()) {
                                close();
                            }
                        }
                    });
                    break;
                case SECONDARY_BUTTON:
                    builder.setNegativeButton(button.getParams().getTextMap().valueAt(0), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            button.getParams().getListener().onClick(DialogAlertImpl.this, button.getParams().getButtonParameter());
                            if(isAttached()) {
                                close();
                            }
                        }
                    });
                    break;
                case NEUTRAL_BUTTON:
                    builder.setNeutralButton(button.getParams().getTextMap().valueAt(0), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            button.getParams().getListener().onClick(DialogAlertImpl.this, button.getParams().getButtonParameter());
                            if(isAttached()) {
                                close();
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
            dialogButtonCount++;

            if (getController().provideIsDebugState() && dialogButtonCount >= MAX_BUILDER_BUTTON_AMOUNT) {
                throw new InvalidParameterException("Dialog only supports up to three different " +
                        "builder buttons (primary, secondary and neutral)!");
            }
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("Empty button text cannot be added to the dialog.");
        }
    }

    @Override
    protected void onShowDefault(@NonNull Context context) {
        super.onShowDefault(context);

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
