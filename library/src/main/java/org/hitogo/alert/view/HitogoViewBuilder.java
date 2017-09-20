package org.hitogo.alert.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.Hitogo;
import org.hitogo.alert.core.HitogoAlertBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;
import org.hitogo.alert.core.HitogoAlertType;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoViewBuilder extends HitogoAlertBuilder<HitogoViewBuilder> {

    private static final HitogoAlertType type = HitogoAlertType.VIEW;

    private String title;
    private String text;

    private Integer containerId;
    private Integer titleViewId;
    private Integer textViewId;
    private Integer layoutViewId;

    private boolean isDismissible;
    private boolean closeOthers = true;
    private boolean dismissByClick;

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> callToActionButtons = new ArrayList<>();
    private HitogoButton closeButton;

    public HitogoViewBuilder(@NonNull Class<? extends HitogoAlert> targetClass,
                             @NonNull Class<? extends HitogoAlertParams> paramClass,
                             @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public HitogoViewBuilder setTitle(@NonNull String title) {
        return setTitle(getController().provideDefaultTitleViewId(type), title);
    }

    @NonNull
    public HitogoViewBuilder setTitle(Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return this;
    }

    @NonNull
    public HitogoViewBuilder setText(@NonNull String text) {
        return setText(getController().provideDefaultTextViewId(type), text);
    }

    @NonNull
    public HitogoViewBuilder setText(Integer viewId, @NonNull String text) {
        this.textViewId = viewId;
        this.text = text;
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
        this.hitogoAnimation = animation;
        this.layoutViewId = innerLayoutViewId == null ?
                getController().provideDefaultLayoutViewId() : innerLayoutViewId;
        return this;
    }

    @NonNull
    public HitogoViewBuilder withoutAnimations() {
        this.hitogoAnimation = null;
        return this;
    }

    @NonNull
    public HitogoViewBuilder asDismissible(@Nullable HitogoButton closeButton) {
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
                    .forViewAction()
                    .build();
        } catch (InvalidParameterException ex) {
            Log.e(HitogoViewBuilder.class.getName(), "Cannot add default close button.");
            Log.e(HitogoViewBuilder.class.getName(), "Reason: " + ex.getMessage());
        }

        return this;
    }

    @NonNull
    public HitogoViewBuilder addActionButton(@NonNull HitogoButton...buttons) {
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
                    .withState(getController().provideDefaultState(type));
        }
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

    @NonNull
    public HitogoViewBuilder closeOthers() {
        this.closeOthers = true;
        return this;
    }

    @NonNull
    public HitogoViewBuilder allowOthers() {
        this.closeOthers = false;
        return this;
    }

    @NonNull
    public HitogoViewBuilder consumeLayoutClick() {
        this.dismissByClick = true;
        return this;
    }

    @NonNull
    public HitogoViewBuilder ignoreLayoutClick() {
        this.dismissByClick = false;
        return this;
    }

    @Override
    protected void onProvideData(HitogoAlertParamsHolder holder) {
        holder.provideString("title", title);
        holder.provideString("text", text);

        holder.provideInteger("containerId", containerId);
        holder.provideInteger("titleViewId", titleViewId);
        holder.provideInteger("textViewId", textViewId);
        holder.provideInteger("layoutViewId", layoutViewId);

        holder.provideBoolean("isDismissible", isDismissible);
        holder.provideBoolean("closeOthers", closeOthers);
        holder.provideBoolean("dismissByClick", dismissByClick);

        holder.provideAnimation(hitogoAnimation);
        holder.provideCallToActionButtons(callToActionButtons);
        holder.provideCloseButton(closeButton);
    }
}
