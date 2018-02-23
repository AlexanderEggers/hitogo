package org.hitogo.alert.toast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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

        if (view != null) {
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

        if (params.getGravity() != null) {
            toast.setGravity(params.getGravity(), params.getxOffset(), params.getyOffset());
        }

        if (params.getHorizontalMargin() != null) {
            toast.setMargin(params.getHorizontalMargin(), params.getVerticalMargin());
        }

        return toast;
    }

    protected void injectOnDismissCallback(ToastAlertParams params) {
        int realDurationInMs;

        if (params.getDuration() == Toast.LENGTH_SHORT) {
            realDurationInMs = 2000;
        } else {
            realDurationInMs = 3500;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAttached()) {
                    close();
                }
            }
        }, realDurationInMs);
    }

    protected void determineButtonCreation(Button button, View toastView, boolean forceClose) {
        if (button.getParams().hasButtonView()) {
            buildActionButton(button, toastView, forceClose);
        } else if (getController().provideIsDebugState()) {
            throw new IllegalStateException("Toast can only process buttons that have a view use " +
                    "asViewButton or asCloseButton.");
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

        SparseArray<Drawable> drawableMap = getParams().getDrawableMap();
        for (int i = 0; i < drawableMap.size(); i++) {
            Integer viewId = drawableMap.keyAt(i);
            Drawable drawable = drawableMap.valueAt(i);
            setDrawable(containerView, viewId, drawable);
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

    @SuppressWarnings("unchecked")
    protected void buildActionButton(final Button button, View view, final boolean forceClose) {
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
                    button.getParams().getListener().onClick(ToastAlertImpl.this, button.getParams().getButtonParameter());

                    if (button.getParams().isClosingAfterClick() || forceClose) {
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
