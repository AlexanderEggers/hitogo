package org.hitogo.alert.toast;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.button.core.Button;

import java.security.InvalidParameterException;

public class ToastAlertImpl extends AlertImpl<ToastAlertParams> implements ToastAlert {

    @Override
    protected void onCheck(@NonNull ToastAlertParams params) {
        super.onCheck(params);

        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this alert.");
        }

        if (params.getState() == null && params.getLayoutRes() == null) {
            Log.i(ToastAlertImpl.class.getName(), "State and custom layout is null. This " +
                    "Toast will use the default implementation instead.");
        }
    }

    @Override
    protected Object onCreateOther(LayoutInflater inflater, @NonNull Context context, @NonNull ToastAlertParams params) {
        Toast toast;
        View view = null;

        if (getParams().getLayoutRes() != null && getParams().getLayoutRes() != 0) {
            view = inflater.inflate(getParams().getLayoutRes(), null);
        } else if (getParams().getState() != null && getController().provideOtherLayout(getParams().getState()) != null) {
            view = inflater.inflate(getController().provideOtherLayout(getParams().getState()), null);
        } else if (getParams().getState() != null && getController().provideViewLayout(getParams().getState()) != null) {
            view = inflater.inflate(getController().provideViewLayout(getParams().getState()), null);
        }

        if(view != null) {
            toast = new Toast(getContext());
            buildLayoutContent(view);

            for (Button button : params.getButtons()) {
                determineButtonCreation(button, view, false);
            }

            if (params.getCloseButton() != null) {
                determineButtonCreation(params.getCloseButton(), view, true);
            }

            toast.setView(view);
        } else {
            toast = Toast.makeText(getContext(), "", params.getDuration());
            toast.setText(params.getTextMap().valueAt(0));
        }

        if(params.getGravity() != null) {
            toast.setGravity(params.getGravity(), params.getxOffset(), params.getyOffset());
        }

        if(params.getHorizontalMargin() != null) {
            toast.setMargin(params.getHorizontalMargin(), params.getVerticalMargin());
        }

        return toast;
    }

    protected void injectOnDismissCallback(ToastAlertParams params) {
        int realDurationInMs;

        if(params.getDuration() == Toast.LENGTH_SHORT) {
            realDurationInMs = 2000;
        } else {
            realDurationInMs = 3500;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isAttached()) {
                    close();
                }
            }
        }, realDurationInMs);
    }

    protected void determineButtonCreation(Button button, View toastView, boolean forceClose) {
        if (button.getParams().hasButtonView()) {
            buildActionButton(button, toastView, forceClose);
        } else if (getController().provideIsDebugState()) {
            throw new IllegalStateException("View can only process buttons that have a view (use forViewAction)");
        }
    }

    protected void buildLayoutContent(@NonNull View containerView) {
        if (getParams().getTitleViewId() != null) {
            setViewString(containerView, getParams().getTitleViewId(), getParams().getTitle());
        }

        SparseArray<String> textMap = getParams().getTextMap();
        for (int i = 0; i < textMap.size(); i++) {
            Integer viewId = textMap.keyAt(i);
            String text = textMap.valueAt(i);
            setViewString(containerView, viewId, text);
        }
    }

    protected void setViewString(@NonNull View containerView, @Nullable Integer viewId,
                                 @Nullable String chars) {
        if (viewId != null) {
            TextView textView = containerView.findViewById(viewId);
            if (textView != null) {
                if (getHelper().isNotEmpty(chars)) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(getHelper().getHtmlText(chars));
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

    @SuppressWarnings("unchecked")
    protected void buildActionButton(final Button button, View view, final boolean forceClose) {
        if (button != null && button.getParams().getViewIds().length >= 2) {
            final View icon = view.findViewById(button.getParams().getViewIds()[0]);
            View click = view.findViewById(button.getParams().getViewIds()[1]);

            if (click == null) {
                click = icon;
            }

            if (icon != null) {
                if (icon instanceof TextView && getHelper().isNotEmpty(button.getParams().getText())) {
                    ((TextView) icon).setText(getHelper().getHtmlText(button.getParams().getText()));
                }

                icon.setVisibility(View.VISIBLE);
                click.setVisibility(View.VISIBLE);
                click.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        button.getParams().getListener().onClick(ToastAlertImpl.this, button.getParams().getButtonParameter());

                        if (button.getParams().isClosingAfterClick() || forceClose) {
                            close();
                        }
                    }
                });
            } else if (getController().provideIsDebugState()) {
                throw new InvalidParameterException("Did you forget to add the button to your layout?");
            }
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("Are you using the correct button type? You can use " +
                    "ActionButton which will define your button view. Reason: View ids for the button " +
                    "view is less than two.");
        }
    }

    @Override
    protected void onShowDefault(@NonNull Context context) {
        super.onShowDefault(context);
        injectOnDismissCallback(getParams());
        ((Toast) getOther()).show();
    }

    @Override
    protected void onCloseDefault(@NonNull Context context) {
        super.onCloseDefault(context);
        ((Toast) getOther()).cancel();
    }
}
