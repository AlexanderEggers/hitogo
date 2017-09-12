package org.hitogo.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hitogo.button.HitogoButtonObject;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoAnimation;
import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoViewBuilder extends HitogoBuilder {

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
    private List<HitogoButtonObject> callToActionButtons;
    private HitogoButtonObject closeButton;

    public HitogoViewBuilder(@NonNull Class<? extends HitogoObject> targetClass,
                             @NonNull Class<? extends HitogoParams> paramClass,
                             @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, HitogoObject.HitogoType.VIEW);
        callToActionButtons = new ArrayList<>();
    }

    @NonNull
    public HitogoViewBuilder setTitle(@NonNull String title) {
        return setTitle(getController().provideDefaultTitleViewId(), title);
    }

    @NonNull
    public HitogoViewBuilder setTitle(Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return this;
    }

    @NonNull
    public HitogoViewBuilder setText(@NonNull String text) {
        return setText(getController().provideDefaultTextViewId(), text);
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
        return withAnimations(getController().provideDefaultAnimation(),
                getController().provideDefaultLayoutViewId());
    }

    @NonNull
    public HitogoViewBuilder withAnimations(@Nullable Integer innerLayoutViewId) {
        return withAnimations(getController().provideDefaultAnimation(), innerLayoutViewId);
    }

    @NonNull
    public HitogoViewBuilder withAnimations(@Nullable HitogoAnimation animation) {
        return withAnimations(animation, getController().provideDefaultLayoutViewId());
    }

    @NonNull
    public HitogoViewBuilder withAnimations(@Nullable HitogoAnimation animation,
                                            @Nullable Integer innerLayoutViewId) {
        this.showAnimation = true;
        this.hitogoAnimation = animation;
        this.layoutViewId = innerLayoutViewId == null ?
                getController().provideDefaultLayoutViewId() : innerLayoutViewId;
        return this;
    }

    @NonNull
    public HitogoViewBuilder withoutAnimations() {
        this.showAnimation = false;
        this.hitogoAnimation = null;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asDismissible(@Nullable HitogoButtonObject closeButton) {
        this.isDismissible = true;

        if (closeButton != null) {
            this.closeButton = closeButton;
        }
        return this;
    }

    @NonNull
    public HitogoViewBuilder asDismissible() {
        this.isDismissible = true;

        try {
            closeButton = Hitogo.with(getContainer())
                    .asButton()
                    .forClose()
                    .build();
        } catch (InvalidParameterException ex) {
            Log.e(HitogoViewBuilder.class.getName(), "Cannot add default close button.");
            Log.e(HitogoViewBuilder.class.getName(), "Reason: " + ex.getMessage());
        }

        return this;
    }

    @NonNull
    public HitogoViewBuilder addActionButton(@NonNull HitogoButtonObject...buttons) {
        Collections.addAll(callToActionButtons, buttons);
        return this;
    }

    @NonNull
    public HitogoViewBuilder asIgnoreLayout() {
        this.containerId = null;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asOverlay() {
        return asOverlay(getController().provideDefaultOverlayContainerId());
    }

    @NonNull
    public HitogoViewBuilder asOverlay(@Nullable Integer overlayId) {
        this.containerId = overlayId;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asSimpleView(@NonNull String text) {

        HitogoViewBuilder customBuilder = getController().provideSimpleView(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asLayoutChild()
                    .setText(text)
                    .asDismissible()
                    .withState(getController().provideDefaultState());
        }
    }

    @NonNull
    public HitogoViewBuilder withState(Integer state) {
        this.state = state;
        return this;
    }

    @NonNull
    public HitogoViewBuilder withState(Enum state) {
        this.state = state != null ? state.ordinal() : null;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asLayoutChild() {
        asLayoutChild(getController().provideDefaultLayoutContainerId());
        return this;
    }

    @NonNull
    public HitogoViewBuilder asLayoutChild(Integer containerId) {
        this.containerId = containerId;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        holder.provideString("title", title);
        holder.provideString("text", text);
        holder.provideInteger("state", state);
        holder.provideInteger("containerId", containerId);
        holder.provideInteger("titleViewId", titleViewId);
        holder.provideInteger("textViewId", textViewId);
        holder.provideInteger("layoutViewId", layoutViewId);
        holder.provideBoolean("showAnimation", showAnimation);
        holder.provideBoolean("isDismissible", isDismissible);
        holder.provideExtras("arguments", arguments);

        holder.provideAnimation(hitogoAnimation);
        holder.provideCallToActionButtons(callToActionButtons);
        holder.provideCloseButton(closeButton);
    }
}
