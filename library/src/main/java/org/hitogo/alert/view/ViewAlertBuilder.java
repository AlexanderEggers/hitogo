package org.hitogo.alert.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.InvalidParameterException;

import org.hitogo.core.HitogoAnimation;
import org.hitogo.button.core.Button;
import org.hitogo.core.Hitogo;
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
        return withAnimations(true);
    }

    @NonNull
    public ViewAlertBuilder withAnimations(boolean withAnimation) {
        if(withAnimation) {
            return withAnimations(getController().provideDefaultAnimation(),
                    getController().provideDefaultLayoutViewId());
        }
        this.animation = null;
        return this;
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
    public ViewAlertBuilder asDismissible() {
        return asDismissible(true);
    }

    @NonNull
    public ViewAlertBuilder asDismissible(boolean isDismissible) {
        if(isDismissible) {
            try {
                return asDismissible(Hitogo.with(getContainer())
                        .asActionButton()
                        .forCloseAction()
                        .build());
            } catch (InvalidParameterException ex) {
                Log.e(ViewAlertBuilder.class.getName(), "Cannot add default close button.");
                Log.e(ViewAlertBuilder.class.getName(), "Reason: " + ex.getMessage());
            }
        }

        return this;
    }

    @NonNull
    public ViewAlertBuilder asDismissible(@Nullable Button closeButton) {
        if (closeButton != null) {
            return super.setCloseButton(closeButton);
        }
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
                    .asDismissible(true)
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
        return closeOthers(true);
    }

    @NonNull
    public ViewAlertBuilder closeOthers(boolean closeOthers) {
        this.closeOthers = closeOthers;
        return this;
    }

    @NonNull
    public ViewAlertBuilder dismissByLayoutClick() {
        return dismissByLayoutClick(true);
    }

    @NonNull
    public ViewAlertBuilder dismissByLayoutClick(boolean dismissByClick) {
        this.dismissByClick = dismissByClick;
        return this;
    }

    @Override
    public void show() {
        show(false);
    }

    public void show(boolean force) {
        build().show(force);
    }

    public void showLater(boolean showLater) {
        if (showLater) {
            build().showLater(true);
        } else {
            show(false);
        }
    }

    public void showDelayed(long millis) {
        showDelayed(millis, false);
    }

    public void showDelayed(long millis, boolean force) {
        build().showDelayed(millis, force);
    }

    @Override
    protected void onProvideData(AlertParamsHolder holder) {
        holder.provideInteger(ViewAlertParamsKeys.CONTAINER_ID_KEY, containerId);
        holder.provideInteger(ViewAlertParamsKeys.INNER_LAYOUT_VIEW_ID_KEY, innerLayoutViewId);
        holder.provideBoolean(ViewAlertParamsKeys.CLOSE_OTHERS_KEY, closeOthers);
        holder.provideBoolean(ViewAlertParamsKeys.DISMISS_BY_LAYOUT_CLICK_KEY, dismissByClick);

        holder.provideAnimation(animation);
    }
}
