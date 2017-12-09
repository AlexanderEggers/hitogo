package org.hitogo.alert.popup;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.view.View;
import android.widget.LinearLayout;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertParamsHolder;
import org.hitogo.alert.core.AlertType;
import org.hitogo.core.HitogoContainer;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.M;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PopupAlertBuilder extends AlertBuilder<PopupAlertBuilder, PopupAlert> {

    private static final AlertType type = AlertType.POPUP;

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

    private Transition enterTransition;
    private Transition exitTransition;
    private View.OnTouchListener onTouchListener;

    public PopupAlertBuilder(@NonNull Class<? extends AlertImpl> targetClass,
                             @NonNull Class<? extends AlertParams> paramClass,
                             @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public PopupAlertBuilder setAnchor(int anchorViewId) {
        this.anchorViewId = anchorViewId;
        return this;
    }

    @NonNull
    public PopupAlertBuilder setAnchor(String anchorViewTag) {
        this.anchorViewTag = anchorViewTag;
        return this;
    }

    @NonNull
    public PopupAlertBuilder asSimplePopup(int anchorViewId, String text) {
        setAnchor(anchorViewId);
        addText(text);

        PopupAlertBuilder customBuilder = getController().provideSimplePopup(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible();
        }
    }

    @NonNull
    public PopupAlertBuilder asSimplePopup(String anchorViewTag, String text) {
        setAnchor(anchorViewTag);
        addText(text);

        PopupAlertBuilder customBuilder = getController().provideSimplePopup(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible();
        }
    }

    @NonNull
    public PopupAlertBuilder setOffset(int xoff, int yoff) {
        this.xoff = xoff;
        this.yoff = yoff;
        return this;
    }

    @NonNull
    @RequiresApi(KITKAT)
    public PopupAlertBuilder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    @NonNull
    @RequiresApi(LOLLIPOP)
    public PopupAlertBuilder setElevation(float elevation) {
        this.elevation = elevation;
        return this;
    }

    @NonNull
    public PopupAlertBuilder setBackgroundDrawable(@DrawableRes Integer drawableRes) {
        this.drawableRes = drawableRes;
        return this;
    }

    @NonNull
    public PopupAlertBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @NonNull
    public PopupAlertBuilder setAnimationStyle(int animationStyle) {
        this.animationStyle = animationStyle;
        return this;
    }

    public PopupAlertBuilder setTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
        return this;
    }

    @NonNull
    @RequiresApi(M)
    public PopupAlertBuilder setTransition(Transition enterTransition, Transition exitTransition) {
        this.enterTransition = enterTransition;
        this.exitTransition = exitTransition;
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
        if (!showLater) {
            show(false);
        } else {
            build().showLater(true);
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

        holder.provideTransition(enterTransition);
        holder.provideTransition(exitTransition);
        holder.provideOnTouchListener(onTouchListener);
    }
}
