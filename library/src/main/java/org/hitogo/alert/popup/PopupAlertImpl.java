package org.hitogo.alert.popup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.button.core.Button;

import java.security.InvalidParameterException;

/**
 * Implementation for the popup alert. This alert requires to have at least one text element, a
 * state or layout resource and an anchor view-id/-tag.
 *
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class PopupAlertImpl extends AlertImpl<PopupAlertParams> implements PopupAlert {

    private View anchorView;

    @Override
    protected void onCheck(@NonNull PopupAlertParams params) {
        super.onCheck(params);

        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this alert.");
        }

        if (params.getState() == null && params.getLayoutRes() == null) {
            throw new InvalidParameterException("To display non-dialog alerts you need to define " +
                    "a state which will use a specific layout. This layout needs to be defined " +
                    "inside your HitogoController. Optionally you can define a custom layout via " +
                    "setLayout.");
        }

        if (params.getAnchorViewId() == null && params.getAnchorViewTag() == null) {
            throw new InvalidParameterException("You haven't defined the anchor view. You can " +
                    "use setAnchor to define the view by viewId or viewTag.");
        }
    }

    @Override
    protected void onCreate(@NonNull PopupAlertParams params) {
        super.onCreate(params);

        if (params.getAnchorViewTag() != null) {
            anchorView = getRootView().findViewWithTag(params.getAnchorViewTag());
        } else {
            anchorView = getRootView().findViewById(params.getAnchorViewId());
        }
    }

    @Nullable
    @Override
    protected PopupWindow onCreatePopup(LayoutInflater inflater, @NonNull Context context,
                                        @NonNull PopupAlertParams params) {
        View view = null;
        if (params.getLayoutRes() != null && params.getLayoutRes() != 0) {
            view = inflater.inflate(params.getLayoutRes(), null);
        } else if (params.getState() != null && getController().providePopupLayout(params.getState()) != null) {
            view = inflater.inflate(getController().providePopupLayout(params.getState()), null);
        }

        if (view != null) {
            buildLayoutContent(view);
            buildLayoutInteractions(view);

            for (Button button : params.getButtons()) {
                determineButtonCreation(button, view, false);
            }

            if (params.getCloseButton() != null) {
                determineButtonCreation(params.getCloseButton(), view, true);
            }

            PopupWindow window;
            if (params.isFullScreen()) {
                window = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
            } else {
                window = new PopupWindow(view, params.getWidth(), params.getHeight());
            }

            return buildPopupWindow(window);
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("View is null. Is the layout existing for " +
                    "the state: '" + params.getState() + "'?");
        }

        return null;
    }

    protected void buildLayoutInteractions(@NonNull View popupView) {
        if (getParams().isDismissByLayoutClick()) {
            popupView.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    close();
                }
            });
            popupView.setClickable(true);
        }
    }

    protected void determineButtonCreation(Button button, View popupView, boolean forceClose) {
        if (button.getParams().hasButtonView()) {
            buildActionButton(button, popupView, forceClose);
        } else if (getController().provideIsDebugState()) {
            throw new IllegalStateException("PopupAlert can only process buttons that have a view " +
                    "use asViewButton or asCloseButton.");
        }
    }

    @SuppressWarnings("unchecked")
    protected PopupWindow buildPopupWindow(PopupWindow window) {
        window.setAnimationStyle(getParams().getAnimationStyle());

        if(getParams().getOnTouchListener() != null) {
            window.setTouchInterceptor(getParams().getOnTouchListener());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getParams().getEnterTransition() != null) {
            window.setEnterTransition(getParams().getEnterTransition());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getParams().getExitTransition() != null) {
            window.setExitTransition(getParams().getExitTransition());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getParams().getElevation() != null) {
            window.setElevation(getParams().getElevation());
        }

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // possible that closing was execute by button click and not via default controls
                if (isAttached()) {
                    Button button = getParams().getCloseButton();
                    if (button != null) {
                        button.getParams().getListener().onClick(PopupAlertImpl.this,
                                button.getParams().getButtonParameter());
                    }

                    close();
                }
            }
        });

        if (getParams().isDismissible()) {
            window.setBackgroundDrawable(null);
            window.setOutsideTouchable(true);
        } else if (getParams().getDrawableRes() != null) {
            window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), getParams().getDrawableRes()));
        }

        return window;
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
                throw new InvalidParameterException("Did you forget to add the " +
                        "title/text view to your layout?");
            }
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("Title or text view id is null.");
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
                    button.getParams().getListener().onClick(PopupAlertImpl.this, button.getParams().getButtonParameter());

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
        if(viewId != null && viewId != -1) {
            textView = buttonLayout.findViewById(viewId);
        } else if(buttonLayout instanceof TextView) {
            textView = (TextView) buttonLayout;
        }

        if(textView != null) {
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

        int xoff = getParams().getXOffset();
        int yoff = getParams().getYOffset();
        Integer gravity = getParams().getGravity();

        if (anchorView != null && gravity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getPopup().showAsDropDown(anchorView, xoff, yoff, gravity);
        } else if (anchorView != null) {
            getPopup().showAsDropDown(anchorView, xoff, yoff);
        }
    }

    @Override
    protected void onCloseDefault(@NonNull Context context) {
        super.onCloseDefault(context);
        getPopup().dismiss();
    }
}
