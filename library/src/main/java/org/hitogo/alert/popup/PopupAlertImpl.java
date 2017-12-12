package org.hitogo.alert.popup;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoUtils;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PopupAlertImpl extends AlertImpl<PopupAlertParams> implements PopupAlert {

    private View anchorView;

    @Override
    protected void onCheck(@NonNull HitogoController controller, @NonNull PopupAlertParams params) {
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

    @Nullable
    @Override
    protected PopupWindow onCreatePopup(@NonNull LayoutInflater inflater, @NonNull Context context,
                                        @NonNull PopupAlertParams params) {
        View view = null;
        if (params.getLayoutRes() != null && params.getLayoutRes() != 0) {
            view = inflater.inflate(params.getLayoutRes(), null);
        } else if (params.getState() != null && getController().providePopupLayout(params.getState()) != null) {
            view = inflater.inflate(getController().providePopupLayout(params.getState()), null);
        }

        if(params.getAnchorViewTag() != null) {
            anchorView = getRootView().findViewWithTag(params.getAnchorViewTag());
        } else {
            anchorView = getRootView().findViewById(params.getAnchorViewId());
        }

        if (view != null) {
            buildLayoutContent(view);

            for(Button button : params.getButtons()) {
                determineButtonCreation(button, view, false);
            }

            if(params.getCloseButton() != null) {
                determineButtonCreation(params.getCloseButton(), view, true);
            }

            PopupWindow window = new PopupWindow(view, params.getWidth(), params.getHeight());
            return buildPopupWindow(window);
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("View is null. Is the layout existing for " +
                    "the state: '" + params.getState() + "'?");
        }

        return null;
    }

    private void determineButtonCreation(Button button, View dialogView, boolean forceClose) {
        if(button.getParams().hasButtonView()) {
            buildActionButton(button, dialogView, forceClose);
        } else if (getController().provideIsDebugState()) {
            throw new IllegalStateException("Popup can only process buttons that have a view (use forViewAction)");
        }
    }

    private PopupWindow buildPopupWindow(PopupWindow window) {
        if(getParams().getAnimationStyle() != null) {
            window.setAnimationStyle(getParams().getAnimationStyle());
        }

        if(getParams().getOnTouchListener() != null) {
            window.setTouchInterceptor(getParams().getOnTouchListener());
        }

        if(getParams().getEnterTransition() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setEnterTransition(getParams().getEnterTransition());
        }

        if(getParams().getExitTransition() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setExitTransition(getParams().getExitTransition());
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getParams().getElevation() != null) {
            window.setElevation(getParams().getElevation());
        }

        if (getParams().isDismissible()) {
            window.setBackgroundDrawable(null);
            window.setOutsideTouchable(true);
        } else if(getParams().getDrawableRes() != null) {
            window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), getParams().getDrawableRes()));
        }

        return window;
    }

    private void buildLayoutContent(@NonNull View containerView) {
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

    @SuppressWarnings("deprecation")
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
                throw new InvalidParameterException("Did you forget to add the " +
                        "title/text view to your layout?");
            }
        } else if (getController().provideIsDebugState()) {
            throw new InvalidParameterException("Title or text view id is null.");
        }
    }

    private void buildActionButton(final Button button, View view, final boolean forceClose) {
        if(button != null) {
            final View icon = view.findViewById(button.getParams().getViewIds()[0]);
            View click = view.findViewById(button.getParams().getViewIds()[1]);

            if(click == null) {
                click = icon;
            }

            if (icon != null) {
                if (icon instanceof TextView) {
                    ((TextView) icon).setText(HitogoUtils.getHtmlText(button.getParams().getText()));
                }

                icon.setVisibility(View.VISIBLE);
                click.setVisibility(View.VISIBLE);
                click.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        button.getParams().getListener().onClick();

                        if(button.getParams().isClosingAfterClick() || forceClose) {
                            close();
                        }
                    }
                });
            } else if(getController().provideIsDebugState()) {
                throw new InvalidParameterException("Did you forget to add the button to your layout?");
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
