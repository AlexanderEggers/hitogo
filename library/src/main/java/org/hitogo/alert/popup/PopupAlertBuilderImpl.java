package org.hitogo.alert.popup;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.transition.Transition;
import android.view.View;
import android.widget.LinearLayout;

import org.hitogo.alert.core.AlertBuilderImpl;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoParamsHolder;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.M;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PopupAlertBuilderImpl extends AlertBuilderImpl<PopupAlertBuilder, PopupAlert>
        implements PopupAlertBuilder {

    private Integer drawableRes;
    private Integer anchorViewId;
    private Integer animationStyle;
    private Integer gravity;

    private int xoff;
    private int yoff;
    private int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    private int height = LinearLayout.LayoutParams.WRAP_CONTENT;

    private Float elevation;
    private String anchorViewTag;
    private boolean isDismissible;
    private boolean dismissByClick;

    private Transition enterTransition;
    private Transition exitTransition;
    private View.OnTouchListener onTouchListener;

    public PopupAlertBuilderImpl(@NonNull Class<? extends AlertImpl> targetClass,
                                 @NonNull Class<? extends AlertParams> paramClass,
                                 @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, AlertType.POPUP);
    }

    @Override
    @NonNull
    public PopupAlertBuilder asDismissible() {
        return asDismissible(true);
    }

    @Override
    @NonNull
    public PopupAlertBuilder asDismissible(boolean isDismissible) {
        this.isDismissible = isDismissible;
        return this;
    }

    @Override
    @NonNull
    public PopupAlertBuilder asDismissible(@Nullable Button closeButton) {
        this.isDismissible = true;

        if (closeButton != null) {
            return super.setCloseButton(closeButton);
        }
        return this;
    }

    @Override
    @NonNull
    public PopupAlertBuilder setAnchor(@IdRes int anchorViewId) {
        this.anchorViewId = anchorViewId;
        return this;
    }

    @Override
    @NonNull
    public PopupAlertBuilder setAnchor(String anchorViewTag) {
        this.anchorViewTag = anchorViewTag;
        return this;
    }

    @Override
    @NonNull
    public PopupAlertBuilder setOffset(int xoff, int yoff) {
        this.xoff = xoff;
        this.yoff = yoff;
        return this;
    }

    @Override
    @NonNull
    @RequiresApi(KITKAT)
    public PopupAlertBuilder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    @Override
    @NonNull
    @RequiresApi(LOLLIPOP)
    public PopupAlertBuilder setElevation(float elevation) {
        this.elevation = elevation;
        return this;
    }

    @Override
    @NonNull
    public PopupAlertBuilder setBackgroundDrawable(@DrawableRes Integer drawableRes) {
        this.drawableRes = drawableRes;
        return this;
    }

    @Override
    @NonNull
    public PopupAlertBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    @NonNull
    public PopupAlertBuilder setAnimationStyle(@StyleRes int animationStyle) {
        this.animationStyle = animationStyle;
        return this;
    }

    @NonNull
    @Override
    public PopupAlertBuilder setTouchListener(@NonNull View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
        return this;
    }

    @Override
    @NonNull
    @RequiresApi(M)
    public PopupAlertBuilder setTransition(@Nullable Transition enterTransition, @Nullable Transition exitTransition) {
        this.enterTransition = enterTransition;
        this.exitTransition = exitTransition;
        return this;
    }

    @NonNull
    @Override
    public PopupAlertBuilder dismissByLayoutClick(boolean dismissByClick) {
        this.dismissByClick = dismissByClick;
        return this;
    }

    @Override
    public void show() {
        show(false);
    }

    @Override
    public void show(boolean force) {
        build().show(force);
    }

    @Override
    public void showLater(boolean showLater) {
        if (!showLater) {
            show(false);
        } else {
            build().showLater(true);
        }
    }

    @Override
    public void showDelayed(long millis) {
        showDelayed(millis, false);
    }

    @Override
    public void showDelayed(long millis, boolean force) {
        build().showDelayed(millis, force);
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        super.onProvideData(holder);

        holder.provideInteger(PopupAlertParamsKeys.DRAWABLE_RES_KEY, drawableRes);
        holder.provideInteger(PopupAlertParamsKeys.ANIMATION_STYLE_KEY, animationStyle);
        holder.provideInteger(PopupAlertParamsKeys.ANCHOR_VIEW_ID_KEY, anchorViewId);
        holder.provideInteger(PopupAlertParamsKeys.GRAVITY_KEY, gravity);

        holder.provideInteger(PopupAlertParamsKeys.X_OFF_KEY, xoff);
        holder.provideInteger(PopupAlertParamsKeys.Y_OFF_KEY, yoff);
        holder.provideInteger(PopupAlertParamsKeys.WIDTH_KEY, width);
        holder.provideInteger(PopupAlertParamsKeys.HEIGHT_KEY, height);

        holder.provideFloat(PopupAlertParamsKeys.ELEVATION_KEY, elevation);
        holder.provideString(PopupAlertParamsKeys.ANCHOR_VIEW_TAG_KEY, anchorViewTag);
        holder.provideBoolean(PopupAlertParamsKeys.IS_DISMISSIBLE_KEY, isDismissible);
        holder.provideBoolean(PopupAlertParamsKeys.DISMISS_BY_LAYOUT_CLICK_KEY, dismissByClick);

        holder.provideCustomObject(PopupAlertParamsKeys.ENTER_TRANSITION_KEY, enterTransition);
        holder.provideCustomObject(PopupAlertParamsKeys.EXIT_TRANSITION_KEY, exitTransition);
        holder.provideCustomObject(PopupAlertParamsKeys.TOUCH_LISTENER_KEY, onTouchListener);
    }
}
