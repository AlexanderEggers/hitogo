package org.hitogo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.annotation.XmlRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoBuilder {

    String title;
    String text;

    Integer state;
    Integer containerId;
    Integer titleViewId;
    Integer textViewId;
    Integer dialogThemeResId;

    int hashCode;

    boolean showAnimation = true;
    boolean isDialog;
    boolean isDismissible;

    HitogoAnimation hitogoAnimation;
    Context context;
    View rootView;
    HitogoController controller;
    List<HitogoButton> callToActionButtons;
    List<HitogoButton> closeButtons;

    HitogoBuilder(@NonNull Context context, @Nullable View rootView, @NonNull HitogoController controller) {
        this.context = context;
        this.rootView = rootView;
        this.controller = controller;
        this.callToActionButtons = new ArrayList<>();
        this.closeButtons = new ArrayList<>();
    }

    private HitogoBuilder() {
        throw new IllegalStateException("Cannot initialise builder without hitogo factory " +
                "(Hitogo.class).");
    }

    @NonNull
    public HitogoBuilder setTitle(@XmlRes int viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return this;
    }

    @NonNull
    public HitogoBuilder setText(@XmlRes int viewId, @NonNull String text) {
        this.textViewId = viewId;
        this.text = text;
        return this;
    }

    @NonNull
    public HitogoBuilder setTitle(@NonNull String title) {
        this.titleViewId = controller.getDefaultTitleViewId();
        this.title = title;
        return this;
    }

    @NonNull
    public HitogoBuilder setText(@NonNull String text) {
        this.textViewId = controller.getDefaultTextViewId();
        this.text = text;
        return this;
    }

    @NonNull
    public HitogoBuilder withAnimations() {
        this.showAnimation = true;
        this.hitogoAnimation = controller.getDefaultAnimation();
        return this;
    }

    @NonNull
    public HitogoBuilder withAnimations(@NonNull HitogoAnimation animation) {
        this.showAnimation = true;
        this.hitogoAnimation = animation;
        return this;
    }

    @NonNull
    public HitogoBuilder withoutAnimations() {
        this.showAnimation = false;
        this.hitogoAnimation = null;
        return this;
    }

    @NonNull
    public HitogoBuilder asDismissible(@NonNull HitogoButton closeButton) {
        if (closeButton.isCloseButton) {
            this.isDismissible = true;
            closeButtons.add(closeButton);
        } else {
            Log.e(HitogoBuilder.class.getName(), "Cannot add call to action button as close " +
                    "buttons...");
            Log.e(HitogoBuilder.class.getName(), "Trying to add default dismissible button...");
            asDismissible();
        }
        return this;
    }

    @NonNull
    public HitogoBuilder asDismissible() {
        this.isDismissible = true;

        try {
            HitogoButton button = HitogoButton.with(rootView)
                    .asCloseButton(controller.getDefaultCloseIconId(), controller.getDefaultCloseClickId())
                    .build();
            closeButtons.add(button);
        } catch (InvalidParameterException ex) {
            this.isDismissible = false;
            Log.e(HitogoBuilder.class.getName(), "Cannot make this hitogo dismissible.");
            Log.e(HitogoBuilder.class.getName(), "Reason: " + ex.getMessage());
        }

        return this;
    }

    @NonNull
    public HitogoBuilder asDialog() {
        this.isDialog = true;
        return this;
    }

    @NonNull
    public HitogoBuilder asDialog(@StyleRes int dialogThemeResId) {
        this.isDialog = true;
        this.dialogThemeResId = dialogThemeResId;
        return this;
    }

    @NonNull
    public HitogoBuilder addButton(@NonNull HitogoButton callToActionButton) {
        if (!callToActionButton.isCloseButton) {
            callToActionButtons.add(callToActionButton);
        } else {
            Log.e(HitogoBuilder.class.getName(), "Cannot add close buttons as call to " +
                    "action buttons...");
        }
        return this;
    }

    /**
     * <b>Only</b> use if this hitogo is <b>allowed</b> to be shown above the app layout
     * (including toolbar).
     *
     * @return builder for the hitogo
     */
    @NonNull
    public HitogoBuilder asIgnoreLayout() {
        this.isDialog = false;
        this.containerId = null;
        return this;
    }

    @NonNull
    public HitogoBuilder asOverlay() {
        this.isDialog = false;
        this.containerId = controller.getDefaultOverlayContainerId();

        if (rootView != null && this.containerId != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.e(HitogoBuilder.class.getName(), "Trying to use fallback to let the hitogo " +
                        "ignore the given layout...");
                return asIgnoreLayout();
            }
        } else {
            Log.e(HitogoBuilder.class.getName(), "Trying to use fallback to let the hitogo " +
                    "ignore the given layout...");
            return asIgnoreLayout();
        }

        return this;
    }

    @NonNull
    public HitogoBuilder asOverlay(int overlayId) {
        this.isDialog = false;
        this.containerId = overlayId;

        if (rootView != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.e(HitogoBuilder.class.getName(), "Trying to use fallback to let the hitogo " +
                        "ignore the given layout...");
                return asIgnoreLayout();
            }
        } else {
            Log.e(HitogoBuilder.class.getName(), "Trying to use fallback to let the hitogo " +
                    "ignore the given layout...");
            return asIgnoreLayout();
        }

        return this;
    }

    @NonNull
    public HitogoBuilder asSimple(@NonNull String infoText) {
        this.text = infoText;

        HitogoBuilder customBuilder = controller.getDefaultAsSimpleCall(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asLayoutChild()
                    .asDismissible()
                    .withState(controller.getDefaultState());
        }
    }

    @NonNull
    public HitogoBuilder controlledBy(@NonNull HitogoController controller) {
        this.controller = controller;
        return this;
    }

    @NonNull
    public HitogoBuilder withState(@Nullable Integer state) {
        this.state = state;
        return this;
    }

    @NonNull
    public HitogoBuilder asLayoutChild() {
        this.isDialog = false;
        this.containerId = controller.getDefaultLayoutContainerId();

        if (rootView != null && this.containerId != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.e(HitogoBuilder.class.getName(), "Trying to use fallback to display hitogo " +
                        "as overlay...");
                return asOverlay();
            }
        } else {
            Log.e(HitogoBuilder.class.getName(), "Trying to use fallback to let the hitogo " +
                    "ignore the given layout...");
            return asIgnoreLayout();
        }

        return this;
    }

    @NonNull
    public HitogoBuilder asLayoutChild(@XmlRes int containerId) {
        this.isDialog = false;
        this.containerId = containerId;

        if (rootView != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.e(HitogoBuilder.class.getName(), "Trying to use fallback to display hitogo " +
                        "inside the default container layout...");
                return asLayoutChild();
            }
        } else {
            Log.e(HitogoBuilder.class.getName(), "Trying to use fallback to let the hitogo " +
                    "ignore the given layout...");
            return asIgnoreLayout();
        }

        return this;
    }

    public void show(@NonNull Activity activity) {
        showNow(activity);
    }

    public void showNow(@NonNull Activity activity) {
        build().show(activity);
    }

    public void showDelayed(@NonNull final Activity activity, long millis) {
        if (millis == 0) {
            show(activity);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!activity.isFinishing()) {
                        build().show(activity);
                    }
                }
            }, millis);
        }
    }

    public void show(@NonNull Fragment fragment) {
        showNow(fragment);
    }

    public void showNow(@NonNull Fragment fragment) {
        build().show(fragment.getActivity());
    }

    public void showDelayed(@NonNull final Fragment fragment, long millis) {
        if (millis == 0) {
            show(fragment);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (fragment.isAdded()) {
                        build().show(fragment.getActivity());
                    }
                }
            }, millis);
        }
    }

    @NonNull
    public HitogoObject build() {
        if (this.text == null) {
            throw new InvalidParameterException("Text parameter cannot be null.");
        }

        if (this.isDialog && callToActionButtons.isEmpty()) {
            throw new InvalidParameterException("This hitogo need at least one button to be " +
                    "displayed if it is from type dialog.");
        }

        if (!this.isDialog && state == null) {
            throw new InvalidParameterException("To display non-dialog hitogos you need to define " +
                    "a state which will use a specific layout.");
        }

        if (!this.isDialog && !isDismissible && callToActionButtons.isEmpty()) {
            Log.e(HitogoBuilder.class.getName(), "Are you sure that this hitogo should have no " +
                    "interaction points? If yes, make sure to close this one if it's not " +
                    "needed anymore!");
        }

        if (controller == null) {
            throw new InvalidParameterException("Controller cannot be null.");
        }

        hashCode = this.text.hashCode();

        if (this.isDialog) {
            return createDialog();
        } else {
            return createOverlayOrLayout();
        }
    }

    @NonNull
    private HitogoObject createDialog() {
        return HitogoDialog.create(this);
    }

    @NonNull
    private HitogoObject createOverlayOrLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(controller.getLayout(state), null);

        ViewGroup holder = null;
        if (this.containerId != null && rootView != null) {
            View containerView = rootView.findViewById(this.containerId);
            if (containerView instanceof ViewGroup) {
                holder = (ViewGroup) containerView;
            } else {
                holder = null;
            }
        }

        if (view != null) {
            view = buildLayoutOrOverlayContent(view);
            view = buildLayoutOrOverlayButtons(view);
        } else {
            throw new InvalidParameterException("Hitogo view is null. Is the layout existing for " +
                    "the state: '" + state + "'?");
        }

        return HitogoView.create(this, view, holder);
    }

    @NonNull
    private View buildLayoutOrOverlayContent(@NonNull View containerView) {
        View view = setViewString(containerView, titleViewId, title);
        view = setViewString(view, textViewId, text);
        return view;
    }

    @NonNull
    private View setViewString(@NonNull View containerView, @Nullable Integer viewId,
                               @Nullable String chars) {
        if (viewId != null) {
            TextView textView = containerView.findViewById(viewId);
            if (textView != null) {
                if (chars != null && StringUtils.isNotEmpty(chars)) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(chars);
                } else {
                    textView.setVisibility(View.GONE);
                }
            }
        }
        return containerView;
    }

    @NonNull
    private View buildLayoutOrOverlayButtons(@NonNull View containerView) {
        for (final HitogoButton callToActionButton : callToActionButtons) {
            TextView button = containerView.findViewById(callToActionButton.viewIds[0]);

            if (button != null && StringUtils.isNotEmpty(text)) {
                button.setVisibility(View.VISIBLE);
                button.setText(callToActionButton.text);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callToActionButton.listener.onClick();
                        controller.hide();
                    }
                });
            }
        }

        for (final HitogoButton closeButton : closeButtons) {
            final View removeIcon = containerView.findViewById(closeButton.viewIds[0]);
            final View removeClick = containerView.findViewById(closeButton.viewIds[1]);

            if (removeIcon != null && removeClick != null) {
                removeIcon.setVisibility(this.isDismissible ? View.VISIBLE : View.GONE);
                removeClick.setVisibility(this.isDismissible ? View.VISIBLE : View.GONE);
                removeClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeButton.listener.onClick();
                        controller.hide();
                    }
                });
            }
        }

        return containerView;
    }
}
