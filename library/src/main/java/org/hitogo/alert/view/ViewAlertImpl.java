package org.hitogo.alert.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.TextView;

import org.hitogo.BuildConfig;
import org.hitogo.core.HitogoAnimation;
import org.hitogo.button.action.ActionButton;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoController;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.core.HitogoUtils;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewAlertImpl extends AlertImpl<ViewAlertParams> implements ViewAlert {

    private ViewGroup viewGroup;
    private HitogoAnimation animation;

    @Override
    protected void onCheck(@NonNull HitogoController controller, @NonNull ViewAlertParams params) {
        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this alert.");
        }

        if (params.getState() == null && params.getLayoutRes() == null) {
            throw new InvalidParameterException("To display non-dialog alerts you need to define " +
                    "a state which will use a specific layout. This layout needs to be defined " +
                    "inside your HitogoController. Optionally you can define a custom layout via " +
                    "setLayout.");
        }
    }

    @Override
    protected void onCreate(@NonNull HitogoController controller, @NonNull ViewAlertParams params) {
        this.animation = params.getAnimation();
        if (animation == null) {
            this.animation = controller.provideDefaultAnimation();
        }

        if (params.getContainerId() != null && getRootView() != null) {
            View containerView = getRootView().findViewById(params.getContainerId());

            Integer layoutContainerId = getController().provideDefaultOverlayContainerId();
            if(containerView == null && layoutContainerId != null) {
                Log.e(ViewAlertBuilder.class.getName(), "Cannot find container view. " +
                        "Using default layout container view as fallback.");
                containerView = getRootView().findViewById(layoutContainerId);
            }

            Integer overlayContainerId = getController().provideDefaultOverlayContainerId();
            if(containerView == null && overlayContainerId != null) {
                Log.e(ViewAlertBuilder.class.getName(), "Cannot find container view. " +
                        "Using default overlay container view as fallback.");
                containerView = getRootView().findViewById(overlayContainerId);
            }

            if(containerView != null && !(containerView instanceof ViewGroup) &&
                    (BuildConfig.DEBUG || getController().shouldOverrideDebugMode())) {
                throw new InvalidParameterException("Your container view needs to be a ViewGroup. " +
                        "Use for example the LinearLayout to solve this issue.");
            }

            if (containerView != null && containerView instanceof ViewGroup) {
                viewGroup = (ViewGroup) containerView;
            } else {
                Log.e(ViewAlertBuilder.class.getName(), "Cannot find overlay container view. " +
                        "Using activity content view (layer) fallback.");
                viewGroup = null;
            }
        }
    }

    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull Context context,
                                             @NonNull ViewAlertParams params) {

        View view = null;
        if(params.getLayoutRes() != null && params.getLayoutRes() != 0) {
            view = inflater.inflate(params.getLayoutRes(), null);
        } else if(params.getState() != null) {
            view = inflater.inflate(getController().provideViewLayout(params.getState()), null);
        }

        if (view != null) {
            buildLayoutInteractions(view);
            buildLayoutContent(view);
            buildLayoutButtons(view);
            buildCloseButtons(view);
            return view;
        } else if(BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
            throw new InvalidParameterException("Hitogo view is null. Is the layout existing for " +
                    "the state: '" + params.getState() + "'?");
        }
        return null;
    }

    private void buildLayoutInteractions(@NonNull View containerView) {
        if(getParams().dismissByClick()) {
            containerView.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    close();
                }
            });
            containerView.setClickable(true);
        }
    }

    private void buildLayoutContent(@NonNull View containerView) {
        if(getParams().getTitleViewId() != null) {
            setViewString(containerView, getParams().getTitleViewId(), getParams().getTitle());
        }

        SparseArray<String> textMap = getParams().getTextMap();
        for(int i = 0; i < textMap.size(); i++) {
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
                    textView.setText(HitogoUtils.getText(chars));
                } else {
                    textView.setVisibility(View.GONE);
                }
            } else if(BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
                throw new InvalidParameterException("Did you forget to add the view to your layout?");
            }
        } else if(BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
            throw new InvalidParameterException("View id is null.");
        }
    }

    private void buildLayoutButtons(@NonNull View containerView) {
        for (Button buttonObject : getParams().getButtons()) {
            final ActionButton callToActionButton = (ActionButton) buttonObject;

            View button = containerView.findViewById(callToActionButton.getParams().getViewIds()[0]);
            if (button != null) {
                if (button instanceof TextView) {
                    ((TextView) button).setText(HitogoUtils.getText(callToActionButton.getParams().getText()));
                }

                button.setVisibility(android.view.View.VISIBLE);
                button.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
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

    private void buildCloseButtons(@NonNull View containerView) {
        final ActionButton closeButton = (ActionButton) getParams().getCloseButton();

        if(closeButton != null) {
            final View removeIcon = containerView.findViewById(closeButton.getParams().getViewIds()[0]);
            final View removeClick = containerView.findViewById(closeButton.getParams().getViewIds()[1]);

            if (removeIcon != null && removeClick != null) {
                if (removeIcon instanceof TextView) {
                    ((TextView) removeIcon).setText(HitogoUtils.getText(closeButton.getParams().getText()));
                }

                removeIcon.setVisibility(View.VISIBLE);
                removeClick.setVisibility(View.VISIBLE);
                removeClick.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        closeButton.getParams().getListener().onClick();
                        close();
                    }
                });
            } else if(BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
                throw new InvalidParameterException("Did you forget to add the close button to " +
                        "your layout?");
            }
        }
    }

    @Override
    protected void onAttach(@NonNull Context context) {
        if (viewGroup != null) {
            viewGroup.addView(getView());
        } else {
            ViewGroup.LayoutParams layoutParams = getView().getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            if (((Activity) context).isFinishing()) {
                return;
            }
            ((Activity) context).addContentView(getView(), layoutParams);
        }
    }

    @Override
    protected void onShowAnimation(@NonNull Context context) {
        if (animation != null) {
            HitogoUtils.measureView((Activity) context, getView(), viewGroup);
            animation.showAnimation(getParams(), getView(), this);
        }
    }

    @Override
    protected void onCloseAnimation(@NonNull Context context) {
        if (animation != null) {
            animation.hideAnimation(getParams(), getView(), this);
        }
    }

    @Override
    protected void onCloseDefault(@NonNull Context context) {
        final ViewManager manager = (ViewManager) getView().getParent();
        manager.removeView(getView());
    }

    @Override
    public long getAnimationDuration() {
        return animation.getAnimationDuration();
    }
}
