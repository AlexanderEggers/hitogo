package org.hitogo.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.List;

import org.hitogo.core.HitogoAnimation;
import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.button.HitogoButton;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoViewBuilder extends HitogoBuilder {

    private String title;
    private String text;
    private Integer state;
    private Integer containerId;
    private Integer titleViewId;
    private Integer textViewId;
    private Integer layoutViewId;
    private boolean showAnimation;
    private boolean isDismissible;
    private Bundle arguments;

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> callToActionButtons;
    private HitogoButton closeButton;

    public HitogoViewBuilder(@NonNull Class<? extends HitogoObject> targetClass,
                             @NonNull Class<? extends HitogoParams> paramClass,
                             @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, HitogoObject.HitogoType.VIEW);
    }

    @NonNull
    public HitogoViewBuilder setTitle(@NonNull String title) {
        return setTitle(getController().getDefaultTitleViewId(), title);
    }

    @NonNull
    public HitogoViewBuilder setTitle(Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return this;
    }

    @NonNull
    public HitogoViewBuilder setText(@NonNull String text) {
        return setText(getController().getDefaultTextViewId(), text);
    }

    @NonNull
    public HitogoViewBuilder setText(Integer viewId, @NonNull String text) {
        this.textViewId = viewId;
        this.text = text;
        return this;
    }

    @NonNull
    public HitogoViewBuilder setBundle(@NonNull Bundle arguments) {
        this.arguments = arguments;
        return this;
    }

    @NonNull
    public HitogoViewBuilder withAnimations() {
        return withAnimations(getController().getDefaultAnimation(), getController().getDefaultLayoutViewId());
    }

    @NonNull
    public HitogoViewBuilder withAnimations(@Nullable HitogoAnimation animation) {
        return withAnimations(animation, getController().getDefaultLayoutViewId());
    }

    @NonNull
    public HitogoViewBuilder withAnimations(@Nullable HitogoAnimation animation,
                                            @Nullable Integer innerLayoutViewId) {
        this.showAnimation = true;
        this.hitogoAnimation = animation;
        this.layoutViewId = innerLayoutViewId == null ? getController().getDefaultLayoutViewId() : innerLayoutViewId;
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
            closeButton = HitogoButton.with(getController())
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
        return asOverlay(getController().getDefaultOverlayContainerId());
    }

    @NonNull
    public HitogoViewBuilder asOverlay(@Nullable Integer overlayId) {
        this.containerId = overlayId;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asSimpleView(@NonNull String text) {

        HitogoViewBuilder customBuilder = getController().getSimpleView(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asLayoutChild()
                    .setText(text)
                    .asDismissible()
                    .withState(getController().getDefaultState());
        }
    }

    @NonNull
    public HitogoViewBuilder withState(Integer state) {
        this.state = state;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asLayoutChild() {
        asLayoutChild(getController().getDefaultLayoutContainerId());
        return this;
    }

    @NonNull
    public HitogoViewBuilder asLayoutChild(Integer containerId) {
        this.containerId = containerId;
        return this;
    }

    @Override
    protected Bundle onCreatePublicBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("text", text);
        bundle.putSerializable("state", state);
        bundle.putSerializable("containerId", containerId);
        bundle.putSerializable("titleViewId", titleViewId);
        bundle.putSerializable("textViewId", textViewId);
        bundle.putSerializable("layoutViewId", layoutViewId);
        bundle.putBoolean("showAnimation", showAnimation);
        bundle.putBoolean("isDismissible", isDismissible);
        bundle.putBundle("arguments", arguments);
        return bundle;
    }

    @Override
    protected HitogoAnimation getHitogoAnimation() {
        return hitogoAnimation;
    }

    @Override
    protected List<HitogoButton> getCallToActionButtons() {
        return callToActionButtons;
    }

    @Override
    protected HitogoButton getCloseButton() {
        return closeButton;
    }
}
