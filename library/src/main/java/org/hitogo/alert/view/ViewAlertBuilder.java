package org.hitogo.alert.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.core.HitogoAnimation;
import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertParamsHolder;
import org.hitogo.alert.core.AlertType;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewAlertBuilder extends AlertBuilder<ViewAlertBuilder, ViewAlert> {

    private static final AlertType type = AlertType.VIEW;

    private Integer containerId;
    private Integer innerLayoutViewId;

    private boolean closeOthers;
    private boolean dismissByClick;

    private HitogoAnimation animation;

    public ViewAlertBuilder(@NonNull Class<? extends AlertImpl> targetClass,
                            @NonNull Class<? extends AlertParams> paramClass,
                            @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public ViewAlertBuilder withAnimations() {
        return withAnimations(getController().provideDefaultAnimation(),
                getController().provideDefaultLayoutViewId());
    }

    @NonNull
    public ViewAlertBuilder withAnimations(@Nullable Integer innerLayoutViewId) {
        return withAnimations(getController().provideDefaultAnimation(), innerLayoutViewId);
    }

    @NonNull
    public ViewAlertBuilder withAnimations(@Nullable HitogoAnimation animation) {
        return withAnimations(animation, getController().provideDefaultLayoutViewId());
    }

    @NonNull
    public ViewAlertBuilder withAnimations(@Nullable HitogoAnimation animation,
                                           @Nullable Integer innerLayoutViewId) {
        this.animation = animation;
        this.innerLayoutViewId = innerLayoutViewId == null ?
                getController().provideDefaultLayoutViewId() : innerLayoutViewId;
        return this;
    }

    @NonNull
    public ViewAlertBuilder withoutAnimations() {
        this.animation = null;
        return this;
    }

    @NonNull
    public ViewAlertBuilder asIgnoreLayout() {
        this.containerId = null;
        return this;
    }

    @NonNull
    public ViewAlertBuilder asOverlay() {
        return asOverlay(getController().provideDefaultOverlayContainerId());
    }

    @NonNull
    public ViewAlertBuilder asOverlay(@Nullable Integer overlayId) {
        this.containerId = overlayId == null ?
                getController().provideDefaultOverlayContainerId() : overlayId;
        return this;
    }

    @NonNull
    public ViewAlertBuilder asSimpleView(@NonNull String text) {
        ViewAlertBuilder customBuilder = getController().provideSimpleView(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asLayoutChild()
                    .addText(text)
                    .asDismissible()
                    .setState(getController().provideDefaultState(type));
        }
    }

    @NonNull
    public ViewAlertBuilder asLayoutChild() {
        return asLayoutChild(getController().provideDefaultLayoutContainerId());
    }

    @NonNull
    public ViewAlertBuilder asLayoutChild(Integer containerId) {
        this.containerId = containerId;
        return this;
    }

    @NonNull
    public ViewAlertBuilder closeOthers() {
        this.closeOthers = true;
        return this;
    }

    @NonNull
    public ViewAlertBuilder allowOthers() {
        this.closeOthers = false;
        return this;
    }

    @NonNull
    public ViewAlertBuilder consumeLayoutClick() {
        this.dismissByClick = true;
        return this;
    }

    @NonNull
    public ViewAlertBuilder ignoreLayoutClick() {
        this.dismissByClick = false;
        return this;
    }

    @Override
    public final void show() {
        show(false);
    }

    public final void show(boolean force) {
        build().show(force);
    }

    public final void showLater(boolean showLater) {
        if (showLater) {
            build().showLater(true);
        } else {
            show(false);
        }
    }

    public final void showDelayed(long millis) {
        showDelayed(millis, false);
    }

    public final void showDelayed(long millis, boolean force) {
        build().showDelayed(millis, force);
    }

    @Override
    protected void onProvideData(AlertParamsHolder holder) {
        holder.provideInteger(ViewAlertParamsKeys.CONTAINER_ID_KEY, containerId);
        holder.provideInteger(ViewAlertParamsKeys.INNER_LAYOUT_VIEW_ID_KEY, innerLayoutViewId);
        holder.provideBoolean(ViewAlertParamsKeys.CLOSE_OTHERS_KEY, closeOthers);
        holder.provideBoolean(ViewAlertParamsKeys.DISMISS_BY_CLICK_KEY, dismissByClick);

        holder.provideAnimation(animation);
    }
}
