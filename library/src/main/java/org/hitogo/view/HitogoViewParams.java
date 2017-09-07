package org.hitogo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.button.HitogoButton;

import java.lang.ref.WeakReference;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoViewParams implements HitogoParams {

    private String title;
    private String text;

    private Integer state;
    private Integer containerId;
    private Integer titleViewId;
    private Integer textViewId;
    private Integer layoutViewId;

    private int hashCode;

    private boolean showAnimation;
    private boolean isDismissible;

    private WeakReference<View> hitogoView;
    private WeakReference<ViewGroup> hitogoContainer;

    private List<HitogoButton> callToActionButtons;
    private HitogoButton closeButton;

    private Bundle bundle;
    private WeakReference<Context> context;
    private WeakReference<View> rootView;
    private HitogoController controller;
    private HitogoAnimation hitogoAnimation;

    HitogoViewParams(@NonNull HitogoViewBuilder builder) {
        title = builder.title;
        text = builder.text;

        state = builder.state;
        containerId = builder.containerId;
        titleViewId = builder.titleViewId;
        textViewId = builder.textViewId;
        layoutViewId = builder.layoutViewId;

        hashCode = builder.hashCode;
        showAnimation = builder.showAnimation;
        isDismissible = builder.isDismissible;

        hitogoView = new WeakReference<>(builder.hitogoView);
        hitogoContainer = new WeakReference<>(builder.hitogoContainer);

        callToActionButtons = builder.callToActionButtons;
        closeButton = builder.closeButton;

        bundle = builder.bundle;
        context = new WeakReference<>(builder.context);
        rootView = new WeakReference<>(builder.rootView);
        controller = builder.controller;
        hitogoAnimation = builder.hitogoAnimation;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Integer getState() {
        return state;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public Integer getTitleViewId() {
        return titleViewId;
    }

    public Integer getTextViewId() {
        return textViewId;
    }

    public Integer getLayoutViewId() {
        return layoutViewId;
    }

    @Override
    public int getHashCode() {
        return hashCode;
    }

    public boolean shouldShowAnimation() {
        return showAnimation;
    }

    public boolean isDismissible() {
        return isDismissible;
    }

    public View getHitogoView() {
        return hitogoView.get();
    }

    public ViewGroup getHitogoContainer() {
        return hitogoContainer.get();
    }

    public List<HitogoButton> getCallToActionButtons() {
        return callToActionButtons;
    }

    public HitogoButton getCloseButton() {
        return closeButton;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public Context getContext() {
        return context.get();
    }

    public View getRootView() {
        return rootView.get();
    }

    @Override
    public HitogoController getController() {
        return controller;
    }

    public HitogoAnimation getHitogoAnimation() {
        return hitogoAnimation;
    }
}
