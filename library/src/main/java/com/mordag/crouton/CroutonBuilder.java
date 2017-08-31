package com.mordag.crouton;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

public final class CroutonBuilder {

    private String title;
    private String text;

    private Integer state;
    private Integer containerId;
    private Integer titleViewId;
    private Integer textViewId;

    private boolean showAnimation = true;
    private boolean isDialog;
    private boolean isDismissible;

    private CroutonAnimation croutonAnimation;
    private WeakReference<Context> contextRef;
    private View rootView;
    private CroutonController controller;
    private List<CroutonButton> callToActionButtons;
    private List<CroutonButton> closeButtons;

    CroutonBuilder(Context context, View rootView, CroutonController controller) {
        this.contextRef = new WeakReference<>(context);
        this.rootView = rootView;
        this.controller = controller;
        this.callToActionButtons = new ArrayList<>();
        this.closeButtons = new ArrayList<>();
    }

    private CroutonBuilder() {
        throw new IllegalStateException("Cannot initialise Builder without crouton factory " +
                "(Crouton.class).");
    }

    @NonNull
    public CroutonBuilder setTitle(int viewId, String title) {
        this.titleViewId = viewId;
        this.title = title;
        return this;
    }

    @NonNull
    public CroutonBuilder setText(int viewId, String text) {
        this.textViewId = viewId;
        this.text = text;
        return this;
    }

    @NonNull
    public CroutonBuilder setTitle(String title) {
        this.titleViewId = controller.getDefaultTitleViewId();
        this.title = title;
        return this;
    }

    @NonNull
    public CroutonBuilder setText(String text) {
        this.textViewId = controller.getDefaultTextViewId();
        this.text = text;
        return this;
    }

    @NonNull
    public CroutonBuilder withAnimations() {
        this.showAnimation = true;
        this.croutonAnimation = controller.getDefaultAnimation();
        return this;
    }

    @NonNull
    public CroutonBuilder withAnimations(@NonNull CroutonAnimation animation) {
        this.showAnimation = true;
        this.croutonAnimation = animation;
        return this;
    }

    @NonNull
    public CroutonBuilder withoutAnimations() {
        this.showAnimation = false;
        this.croutonAnimation = null;
        return this;
    }

    @NonNull
    public CroutonBuilder asDismissible(@NonNull CroutonButton closeButton) {
        if(closeButton.isCloseButton) {
            this.isDismissible = true;
            closeButtons.add(closeButton);
        } else {
            Log.d(CroutonBuilder.class.getName(), "Cannot add call to action button as close " +
                    "buttons...");
            Log.d(CroutonBuilder.class.getName(), "Trying to add default dismissible button...");
            asDismissible();
        }
        return this;
    }

    @NonNull
    public CroutonBuilder asDismissible() {
        this.isDismissible = true;

        try {
            CroutonButton button = CroutonButtonBuilder.with(rootView)
                    .asCloseButton(controller.getDefaultCloseIconId(),
                            controller.getDefaultCloseClickId())
                    .build();
            closeButtons.add(button);
        } catch (InvalidParameterException ex) {
            this.isDismissible = false;
            Log.d(CroutonBuilder.class.getName(), "Cannot make this crouton dismissible.");
            Log.d(CroutonBuilder.class.getName(), "Reason: " + ex.getMessage());
        }

        return this;
    }

    @NonNull
    public CroutonBuilder asDialog() {
        this.isDialog = true;
        return this;
    }

    @NonNull
    public CroutonBuilder addButton(@NonNull CroutonButton callToActionButton) {
        if(!callToActionButton.isCloseButton) {
            callToActionButtons.add(callToActionButton);
        } else {
            Log.d(CroutonBuilder.class.getName(), "Cannot add close buttons as call to " +
                    "action buttons...");
        }
        return this;
    }

    /**
     * <b>Only</b> use if this crouton is <b>allowed</b> to be shown above the app layout
     * (including toolbar).
     *
     * @return builder for the crouton
     */
    @NonNull
    public CroutonBuilder asIgnoreLayout() {
        this.isDialog = false;
        this.containerId = null;
        return this;
    }

    @NonNull
    public CroutonBuilder asOverlay() {
        this.isDialog = false;
        this.containerId = controller.getOverlayContainerId();

        if (rootView != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to let the crouton " +
                        "ignore the given layout...");
                return asIgnoreLayout();
            }
        } else {
            Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to let the crouton " +
                    "ignore the given layout...");
            return asIgnoreLayout();
        }

        return this;
    }

    @NonNull
    public CroutonBuilder asSimple(@NonNull String infoText) {
        this.text = infoText;
        return asLayoutChild()
                .asDismissible()
                .withState(controller.getDefaultState());
    }

    @NonNull
    public CroutonBuilder controlledBy(CroutonController controller) {
        this.controller = controller;
        return this;
    }

    @NonNull
    public CroutonBuilder withState(int state) {
        this.state = state;
        return this;
    }

    @NonNull
    public CroutonBuilder asLayoutChild() {
        this.isDialog = false;
        this.containerId = controller.getLayoutContainerId();

        if (rootView != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to display crouton " +
                        "as overlay...");
                return asOverlay();
            }
        } else {
            Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to let the crouton " +
                    "ignore the given layout...");
            return asIgnoreLayout();
        }

        return this;
    }

    @NonNull
    public CroutonBuilder asLayoutChild(int containerId) {
        this.isDialog = false;
        this.containerId = containerId;

        if (rootView != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to display crouton " +
                        "inside the default container layout...");
                return asLayoutChild();
            }
        } else {
            throw new IllegalStateException("Root view cannot be null.");
        }

        return this;
    }

    public void show(Activity activity) {
        showNow(activity);
    }

    public void showNow(Activity activity) {
        build().show(activity);
    }

    public void showPost(final Activity activity, long millis) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!activity.isFinishing()) {
                    build().show(activity);
                }
            }
        }, millis);
    }

    public void show(Fragment fragment) {
        showNow(fragment);
    }

    public void showNow(Fragment fragment) {
        build().show(fragment.getActivity());
    }

    public void showPost(final Fragment fragment, long millis) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(fragment.isAdded()) {
                    build().show(fragment.getActivity());
                }
            }
        }, millis);
    }

    @NonNull
    public CroutonObject build() {
        if (this.text == null) {
            throw new InvalidParameterException("Info text parameter cannot be null.");
        }

        if (this.isDialog && (this.state == null || callToActionButtons.isEmpty())) {
            throw new InvalidParameterException("State and title parameter cannot be " +
                    "null and this crouton needs button to be displayed if crouton is from type " +
                    "dialog.");
        }

        if (controller == null) {
            throw new InvalidParameterException("Controller cannot be null.");
        }

        if (this.isDialog) {
            return createDialog();
        } else {
            return createOverlayOrLayout();
        }
    }

    @NonNull
    private CroutonObject createDialog() {
        return CroutonDialog.create(getContext(), this.title, this.text,
                callToActionButtons, this.text.hashCode(), controller);
    }

    @NonNull
    private CroutonObject createOverlayOrLayout() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        if(view != null) {
            view = buildLayoutOrOverlayContent(view);
            view = buildLayoutOrOverlayButtons(view);
        } else {
            throw new InvalidParameterException("Crouton is null. Is the layout existing for " +
                    "the state: '" + state + "'?");
        }

        return CroutonView.make(view, holder, this.showAnimation, this.text.hashCode(), controller,
                croutonAnimation);
    }

    @NonNull
    private View buildLayoutOrOverlayContent(@NonNull View view) {
        if (titleViewId != null) {
            TextView titleView = view.findViewById(titleViewId);

            if (title != null && StringUtils.isNotEmpty(title)) {
                titleView.setVisibility(View.VISIBLE);
                titleView.setText(title);
            } else {
                titleView.setVisibility(View.GONE);
            }
        }

        if (textViewId != null) {
            TextView textView = view.findViewById(textViewId);

            if (text != null && StringUtils.isNotEmpty(text)) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(text);
            } else {
                textView.setVisibility(View.GONE);
            }
        }

        return view;
    }

    @NonNull
    private View buildLayoutOrOverlayButtons(@NonNull View view) {
        for (final CroutonButton callToActionButton : callToActionButtons) {
            Button button = view.findViewById(callToActionButton.viewIds[0]);

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

        for (final CroutonButton closeButton : closeButtons) {
            final View removeIcon = view.findViewById(closeButton.viewIds[0]);
            final View removeClick = view.findViewById(closeButton.viewIds[1]);

            if (removeIcon != null && removeClick != null) {
                removeIcon.setVisibility(this.isDismissible ? View.VISIBLE : View.GONE);
                removeClick.setVisibility(this.isDismissible ? View.VISIBLE : View.GONE);
                removeClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controller.hide();
                    }
                });
            }
        }

        return view;
    }

    @NonNull
    private Context getContext() {
        return contextRef.get();
    }
}
