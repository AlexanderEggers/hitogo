package org.hitogo.alert.view;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.TextView;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.HitogoController;
import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.core.HitogoUtils;
import org.hitogo.button.action.HitogoAction;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoView extends HitogoAlert<HitogoViewParams> {

    private ViewGroup viewGroup;
    private HitogoAnimation animation;
    private HitogoViewParams params;

    @Override
    protected void onCheck(@NonNull HitogoController controller, @NonNull HitogoViewParams params) {
        if (params.getText() == null) {
            throw new InvalidParameterException("Text parameter cannot be null.");
        }

        if (params.getState() == null) {
            throw new InvalidParameterException("To display non-dialog hitogos you need to define " +
                    "a state which will use a specific layout. This layout needs to be defined " +
                    "inside your HitogoController.");
        }

        if (!params.isDismissible() && params.getCallToActionButtons().isEmpty()) {
            Log.w(HitogoViewBuilder.class.getName(), "Are you sure that this hitogo should have no " +
                    "interaction points? If yes, make sure to close this one if it's not " +
                    "needed anymore!");
        }
    }

    @Override
    protected void onCreate(@NonNull HitogoController controller, @NonNull HitogoViewParams params) {
        this.params = params;
        this.animation = params.getAnimation();
        if (animation == null) {
            this.animation = controller.provideDefaultAnimation();
        }

        if (params.getContainerId() != null && getRootView() != null) {
            View containerView = getRootView().findViewById(params.getContainerId());
            if (containerView instanceof ViewGroup) {
                viewGroup = (ViewGroup) containerView;
            } else {
                viewGroup = null;
            }
        }
    }

    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull Activity activity,
                                @NonNull HitogoViewParams params) {
        View view = inflater.inflate(getController().provideViewLayout(params.getState()), null);

        if (view != null) {
            buildLayoutInteractions(view);
            buildLayoutContent(view);
            buildCallToActionButtons(view);
            buildCloseButtons(view);
            return view;
        } else {
            throw new InvalidParameterException("Hitogo view is null. Is the layout existing for " +
                    "the state: '" + params.getState() + "'?");
        }
    }

    private void buildLayoutInteractions(@NonNull View containerView) {
        if(params.consumeLayoutClick()) {
            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    close();
                }
            });
            containerView.setClickable(true);
        }
    }

    private void buildLayoutContent(@NonNull View containerView) {
        if(params.getTitle() != null) {
            setViewString(containerView, params.getTitleViewId(), params.getTitle());
            setViewString(containerView, params.getTextViewId(), params.getText());
        } else {
            setViewString(containerView, params.getTextViewId(), params.getText());
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
            } else {
                throw new InvalidParameterException("Did you forget to add the " +
                        "title/text view to your layout?");
            }
        } else {
            throw new InvalidParameterException("Title or text view id is null.");
        }
    }

    private void buildCallToActionButtons(@NonNull View containerView) {
        for (HitogoButton buttonObject : params.getCallToActionButtons()) {
            final HitogoAction callToActionButton = (HitogoAction) buttonObject;

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
                        if(callToActionButton.getParams().isClosingAfterExecute()) {
                            close();
                        }
                    }
                });
            } else {
                throw new InvalidParameterException("Did you forget to add the " +
                        "call-to-action button to your layout?");
            }
        }
    }

    private void buildCloseButtons(@NonNull View containerView) {
        final HitogoAction closeButton = (HitogoAction) params.getCloseButton();
        boolean isDismissible = params.isDismissible();

        if(closeButton != null) {
            final View removeIcon = containerView.findViewById(closeButton.getParams().getViewIds()[0]);
            final View removeClick = containerView.findViewById(closeButton.getParams().getViewIds()[1]);

            if (removeIcon != null && removeClick != null) {
                removeIcon.setVisibility(isDismissible ? View.VISIBLE : View.GONE);
                removeClick.setVisibility(isDismissible ? View.VISIBLE : View.GONE);
                removeClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeButton.getParams().getListener().onClick();
                        close();
                    }
                });
            } else {
                throw new InvalidParameterException("Did you forget to add the close button to " +
                        "your layout?");
            }
        }
    }

    @Override
    protected void onAttach(@NonNull Activity activity) {
        if (viewGroup != null) {
            viewGroup.addView(getView());
        } else {
            ViewGroup.LayoutParams layoutParams = getView().getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            if (activity.isFinishing()) {
                return;
            }
            activity.addContentView(getView(), layoutParams);
        }
    }

    @Override
    protected void onShowAnimation(@NonNull Activity activity) {
        if (animation != null) {
            HitogoUtils.measureView(activity, getView(), viewGroup);
            animation.showAnimation(params, getView(), this);
        }
    }

    @Override
    protected void onCloseAnimation(@NonNull Activity activity) {
        if (animation != null) {
            animation.hideAnimation(params, getView(), this);
        }
    }

    @Override
    protected void onCloseDefault(@NonNull Activity activity) {
        final ViewManager manager = (ViewManager) getView().getParent();
        manager.removeView(getView());
    }

    @Override
    public long getAnimationDuration() {
        return animation.getAnimationDuration();
    }
}
