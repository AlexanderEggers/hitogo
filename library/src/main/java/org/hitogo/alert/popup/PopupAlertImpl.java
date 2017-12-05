package org.hitogo.alert.popup;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.hitogo.BuildConfig;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.button.action.ActionButton;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoUtils;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PopupAlertImpl extends AlertImpl<PopupAlertParams> implements PopupAlert {

    private View anchorView;

    @Override
    protected void onCheck(@NonNull HitogoController controller, @NonNull PopupAlertParams params) {
        if (params.getTextMap() == null || params.getTextMap().size() == 0) {
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

        if (!params.isDismissible() && params.getButtons().isEmpty()) {
            Log.w(PopupAlertBuilder.class.getName(), "Are you sure that this alert should have " +
                    "no interaction points? If yes, make sure to close this one if it's not needed " +
                    "anymore!");
        }
    }

    @Nullable
    @Override
    protected PopupWindow onCreatePopup(@Nullable LayoutInflater inflater, @NonNull Context context,
                                        @NonNull PopupAlertParams params) {
        View view = null;
        if (params.getLayoutRes() != null && params.getLayoutRes() != 0) {
            view = inflater.inflate(params.getLayoutRes(), null);
        } else if (params.getState() != null) {
            view = inflater.inflate(getController().providePopupLayout(params.getState()), null);
        }

        if(params.getAnchorViewTag() != null) {
            anchorView = getRootView().findViewWithTag(params.getAnchorViewTag());
        } else {
            anchorView = getRootView().findViewById(params.getAnchorViewId());
        }

        if (view != null) {
            buildLayoutContent(params, view);
            buildCallToActionButtons(params, view);

            PopupWindow window = new PopupWindow(view, params.getWidth(), params.getHeight());

            if(params.getAnimationStyle() != null) {
                window.setAnimationStyle(params.getAnimationStyle());
            }

            if(params.getOnTouchListener() != null) {
                window.setTouchInterceptor(params.getOnTouchListener());
            }

            if(params.getEnterTransition() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setEnterTransition(params.getEnterTransition());
            }

            if(params.getEnterTransition() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setExitTransition(params.getExitTransition());
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && params.getElevation() != null) {
                window.setElevation(params.getElevation());
            }

            if (params.isDismissible()) {
                window.setBackgroundDrawable(null);
                window.setOutsideTouchable(true);
            } else if(params.getDrawableRes() != null) {
                window.setBackgroundDrawable(ContextCompat.getDrawable(context, params.getDrawableRes()));
            }

            return window;
        } else if (BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
            throw new InvalidParameterException("Hitogo view is null. Is the layout existing for " +
                    "the state: '" + params.getState() + "'?");
        }

        return null;
    }

    private void buildLayoutContent(@NonNull PopupAlertParams params, @NonNull View containerView) {
        if (params.getTitleViewId() != null) {
            setViewString(containerView, params.getTitleViewId(), params.getTitle());
        }

        SparseArray<String> textMap = params.getTextMap();
        for (int i = 0; i < textMap.size(); i++) {
            Integer viewId = textMap.keyAt(i);
            String text = textMap.valueAt(i);
            setViewString(containerView, viewId, text);
        }
    }

    @SuppressWarnings("deprecation")
    private void setViewString(@NonNull View containerView, @Nullable Integer viewId,
                               @Nullable String chars) {
        if (viewId != null) {
            TextView textView = containerView.findViewById(viewId);
            if (textView != null) {
                if (chars != null && HitogoUtils.isNotEmpty(chars)) {
                    textView.setVisibility(View.VISIBLE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        textView.setText(Html.fromHtml(chars, Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        textView.setText(Html.fromHtml(chars));
                    }
                } else {
                    textView.setVisibility(View.GONE);
                }
            } else if (BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
                throw new InvalidParameterException("Did you forget to add the " +
                        "title/text view to your layout?");
            }
        } else if (BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
            throw new InvalidParameterException("Title or text view id is null.");
        }
    }

    private void buildCallToActionButtons(@NonNull PopupAlertParams params, @NonNull View containerView) {
        for (Button buttonObject : params.getButtons()) {
            final ActionButton callToActionButton = (ActionButton) buttonObject;

            View button = containerView.findViewById(callToActionButton.getParams().getViewIds()[0]);
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
            } else if (BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
                throw new InvalidParameterException("Did you forget to add the " +
                        "call-to-action button to your layout?");
            }
        }
    }

    @Override
    protected void onAttach(@NonNull Context context) {
        int xoff = getParams().getXoff();
        int yoff = getParams().getYoff();

        if(anchorView != null && getParams().getGravity() != null &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getPopup().showAsDropDown(anchorView, xoff, yoff, getParams().getGravity());
        } else if (anchorView != null && xoff != 0 && yoff != 0) {
            getPopup().showAsDropDown(anchorView, xoff, yoff);
        } else if (anchorView != null) {
            getPopup().showAsDropDown(anchorView);
        }
    }

    @Override
    protected void onCloseDefault(@NonNull Context context) {
        getPopup().dismiss();
    }
}