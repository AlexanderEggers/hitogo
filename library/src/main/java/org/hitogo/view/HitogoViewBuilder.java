package org.hitogo.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import org.hitogo.core.HitogoAnimation;
import org.hitogo.core.button.HitogoButton;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoUtils;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoViewBuilder {

    Class<? extends HitogoObject> targetClass;

    String title;
    String text;

    Integer state;
    Integer containerId;
    Integer titleViewId;
    Integer textViewId;
    Integer layoutViewId;

    int hashCode;
    boolean showAnimation;
    boolean isDismissible;

    View hitogoView;
    ViewGroup hitogoContainer;

    List<HitogoButton> callToActionButtons;
    HitogoButton closeButton;

    Bundle bundle;
    Activity activity;
    View rootView;
    HitogoController controller;
    HitogoAnimation hitogoAnimation;

    public HitogoViewBuilder(@NonNull Class<? extends HitogoObject> targetClass,
                             @NonNull Activity activity, @Nullable View rootView,
                             @NonNull HitogoController controller) {
        this.targetClass = targetClass;
        this.activity = activity;
        this.rootView = rootView;
        this.controller = controller;
        this.callToActionButtons = new ArrayList<>();
    }

    @NonNull
    public HitogoViewBuilder setTitle(Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return this;
    }

    @NonNull
    public HitogoViewBuilder setTitle(@NonNull String title) {
        return setTitle(controller.getDefaultTitleViewId(), title);
    }

    @NonNull
    public HitogoViewBuilder setText(Integer viewId, @NonNull String text) {
        this.textViewId = viewId;
        this.text = text;
        return this;
    }

    @NonNull
    public HitogoViewBuilder setBundle(@NonNull Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    @NonNull
    public HitogoViewBuilder setText(@NonNull String text) {
        return setText(controller.getDefaultTextViewId(), text);
    }

    @NonNull
    public HitogoViewBuilder withAnimations() {
        return withAnimations(controller.getDefaultAnimation(), controller.getDefaultLayoutViewId());
    }

    @NonNull
    public HitogoViewBuilder withAnimations(@Nullable HitogoAnimation animation) {
        return withAnimations(animation, controller.getDefaultLayoutViewId());
    }

    @NonNull
    public HitogoViewBuilder withAnimations(@Nullable HitogoAnimation animation,
                                            @Nullable Integer innerLayoutViewId) {
        this.showAnimation = true;
        this.hitogoAnimation = animation;
        this.layoutViewId = innerLayoutViewId == null ? controller.getDefaultLayoutViewId() : innerLayoutViewId;
        return this;
    }

    @NonNull
    public HitogoViewBuilder withoutAnimations() {
        this.showAnimation = false;
        this.hitogoAnimation = null;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asDismissible(@Nullable HitogoButton closeButton) {
        this.isDismissible = true;

        if (closeButton != null && closeButton.isCloseButton()) {
            this.closeButton = closeButton;
        }
        return this;
    }

    @NonNull
    public HitogoViewBuilder asDismissible() {
        this.isDismissible = true;

        try {
            closeButton = HitogoButton.with(controller)
                    .asCloseButton()
                    .build();
        } catch (InvalidParameterException ex) {
            Log.e(HitogoViewBuilder.class.getName(), "Cannot add default close button.");
            Log.e(HitogoViewBuilder.class.getName(), "Reason: " + ex.getMessage());
        }

        return this;
    }

    @NonNull
    public HitogoViewBuilder addActionButton(@NonNull HitogoButton...buttons) {
        for(HitogoButton button : buttons) {
            if (!button.isCloseButton()) {
                callToActionButtons.add(button);
            } else {
                Log.e(HitogoViewBuilder.class.getName(), "Cannot add close buttons as call to action buttons.");
            }
        }
        return this;
    }

    @NonNull
    public HitogoViewBuilder asIgnoreLayout() {
        this.containerId = null;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asOverlay() {
        return asOverlay(controller.getDefaultOverlayContainerId());
    }

    @NonNull
    public HitogoViewBuilder asOverlay(@Nullable Integer overlayId) {
        this.containerId = overlayId;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asSimple(@NonNull String infoText) {
        this.text = infoText;

        HitogoViewBuilder customBuilder = controller.getSimpleView(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asLayoutChild()
                    .asDismissible()
                    .withState(controller.getDefaultState());
        }
    }

    @NonNull
    public HitogoViewBuilder controlledBy(@NonNull HitogoController controller) {
        this.controller = controller;
        return this;
    }

    @NonNull
    public HitogoViewBuilder withState(Integer state) {
        this.state = state;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asLayoutChild() {
        asLayoutChild(controller.getDefaultLayoutContainerId());
        return this;
    }

    @NonNull
    public HitogoViewBuilder asLayoutChild(Integer containerId) {
        this.containerId = containerId;
        return this;
    }

    @NonNull
    public HitogoObject build() {
        hashCode = this.text.hashCode();

        try {
            HitogoObject object = targetClass.getConstructor().newInstance();
            object.startHitogo(new HitogoViewParams(this));
            return object;
        } catch (Exception e) {
            Log.wtf(HitogoViewBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    public void show(@NonNull Activity activity) {
        build().show(activity);
    }

    public void showDelayed(@NonNull final Activity activity, long millis) {
        if (millis == 0) {
            build().show(activity);
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
        build().show(fragment);
    }

    public void showDelayed(@NonNull final Fragment fragment, long millis) {
        if (millis == 0) {
            build().show(fragment);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (fragment.isAdded()) {
                        build().show(fragment);
                    }
                }
            }, millis);
        }
    }
}
