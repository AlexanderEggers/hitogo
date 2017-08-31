package com.mordag.crouton;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
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
    private Integer closeIconViewId;
    private Integer closeClickViewId;
    private Integer titleViewId;
    private Integer textViewId;

    private boolean showAnimation = true;
    private boolean isDialog;
    private boolean isDismissible;

    private WeakReference<Context> contextRef;
    private View rootView;
    private CroutonController controller;
    private List<CroutonButton> buttonList;

    CroutonBuilder(Context context, View rootView, CroutonController controller) {
        this.contextRef = new WeakReference<>(context);
        this.rootView = rootView;
        this.controller = controller;
        this.buttonList = new ArrayList<>();
    }

    private CroutonBuilder() {
        throw new IllegalStateException("Cannot initialise Builder without crouton factory (Crouton.class).");
    }

    public CroutonBuilder setTitle(int viewId, String title) {
        this.titleViewId = viewId;
        this.title = title;
        return this;
    }

    public CroutonBuilder setText(int viewId, String text) {
        this.textViewId = viewId;
        this.text = text;
        return this;
    }

    public CroutonBuilder setTitle(String title) {
        this.titleViewId = controller.getDefaultTitleViewId();
        this.title = title;
        return this;
    }

    public CroutonBuilder setText(String text) {
        this.textViewId = controller.getDefaultTextViewId();
        this.text = text;
        return this;
    }

    public CroutonBuilder withoutAnimation() {
        this.showAnimation = false;
        return this;
    }

    public CroutonBuilder asDismissible(int removeViewId, @Nullable Integer closeClickViewId) {
        this.isDismissible = true;
        this.closeIconViewId = removeViewId;
        this.closeClickViewId = closeClickViewId != null ? closeClickViewId : removeViewId;
        return this;
    }

    public CroutonBuilder asDismissible() {
        this.isDismissible = true;
        this.closeIconViewId = controller.getDefaultCloseIconId();
        this.closeClickViewId = controller.getDefaultCloseClickId() != null ?
                controller.getDefaultCloseClickId() : controller.getDefaultCloseIconId();
        return this;
    }

    public CroutonBuilder asDialog() {
        this.isDialog = true;
        return this;
    }

    public CroutonBuilder addButton(@Nullable CroutonButton button) {
        if(button != null) {
            buttonList.add(button);
        }
        return this;
    }

    /**
     * <b>Only</b> use if this crouton is <b>allowed</b> to be shown above the app layout
     * (including toolbar).
     *
     * @return builder for the crouton
     */
    public CroutonBuilder asIgnoreLayout() {
        this.isDialog = false;
        this.containerId = null;
        return this;
    }

    public CroutonBuilder asOverlay() {
        this.isDialog = false;
        this.containerId = controller.getOverlayContainerId();

        if (rootView != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to let the crouton ignore the given layout...");
                return asIgnoreLayout();
            }
        } else {
            Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to let the crouton ignore the given layout...");
            return asIgnoreLayout();
        }

        return this;
    }

    public CroutonBuilder asSimple(String infoText) {
        this.text = infoText;
        return asLayoutChild()
                .asDismissible()
                .withState(controller.getDefaultState());
    }

    public CroutonBuilder controlledBy(CroutonController controller) {
        this.controller = controller;
        return this;
    }

    public CroutonBuilder withState(int state) {
        this.state = state;
        return this;
    }

    public CroutonBuilder asLayoutChild() {
        this.isDialog = false;
        this.containerId = controller.getLayoutContainerId();

        if (rootView != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to display crouton as overlay...");
                return asOverlay();
            }
        } else {
            Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to let the crouton ignore the given layout...");
            return asIgnoreLayout();
        }

        return this;
    }

    public CroutonBuilder asLayoutChild(int containerId) {
        this.isDialog = false;
        this.containerId = containerId;

        if (rootView != null) {
            View container = rootView.findViewById(this.containerId);
            if (container == null) {
                Log.d(CroutonBuilder.class.getName(), "Trying to use fallback to display crouton inside the default container layout...");
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

    public CroutonObject build() {
        if (this.text == null) {
            throw new InvalidParameterException("Info text parameter cannot be null.");
        }

        if (this.isDialog && (this.state == null || buttonList.isEmpty())) {
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

    private CroutonObject createDialog() {
        return CroutonDialog.create(getContext(), this.title, this.text,
                buttonList, this.text.hashCode(), controller);
    }

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

        view = buildLayoutOrOverlayContent(view);
        view = buildLayoutOrOverlayButtons(view);

        return CroutonView.make(view, holder, this.showAnimation,
                this.text.hashCode(), controller);
    }

    private View buildLayoutOrOverlayContent(View view) {
        if(titleViewId != null) {
            TextView titleView = view.findViewById(titleViewId);

            if (title != null && StringUtils.isNotEmpty(title)) {
                titleView.setVisibility(View.VISIBLE);
                titleView.setText(title);
            } else {
                titleView.setVisibility(View.GONE);
            }
        }

        if(textViewId != null) {
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

    private View buildLayoutOrOverlayButtons(View view) {
        for(final CroutonButton croutonButton : buttonList) {
            Button button = view.findViewById(croutonButton.viewId);

            if (button != null && StringUtils.isNotEmpty(text)) {
                button.setVisibility(View.VISIBLE);
                button.setText(croutonButton.text);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        croutonButton.listener.onClick();
                        controller.hide();
                    }
                });
            }
        }

        if(closeIconViewId != null && closeClickViewId != null) {
            final View removeIcon = view.findViewById(closeIconViewId);
            final View removeClick = view.findViewById(closeClickViewId);

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

    private Context getContext() {
        return contextRef.get();
    }
}
