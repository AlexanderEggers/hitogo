package org.hitogo.alert.popup;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.widget.LinearLayout;

import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertBuilder;
import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;
import org.hitogo.alert.core.HitogoAlertType;
import org.hitogo.core.HitogoContainer;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.M;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoPopupBuilder extends HitogoAlertBuilder<HitogoPopupBuilder> {

    private static final HitogoAlertType type = HitogoAlertType.POPUP;

    private Integer drawableRes;
    private Integer anchorViewId;
    private Integer animationStyle;

    private int xoff;
    private int yoff;
    private int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    private int height = LinearLayout.LayoutParams.WRAP_CONTENT;

    private Float elevation;
    private String anchorViewTag;
    private boolean isDismissible;

    private Transition enterTransition;
    private Transition exitTransition;

    public HitogoPopupBuilder(@NonNull Class<? extends HitogoAlert> targetClass,
                              @NonNull Class<? extends HitogoAlertParams> paramClass,
                              @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public HitogoPopupBuilder asDismissible() {
        this.isDismissible = true;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder setAnchor(int anchorViewId) {
        this.anchorViewId = anchorViewId;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder setAnchor(String anchorViewTag) {
        this.anchorViewTag = anchorViewTag;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder asSimplePopup(int anchorViewId, String text) {
        setAnchor(anchorViewId);
        addText(text);

        HitogoPopupBuilder customBuilder = getController().provideSimplePopup(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible();
        }
    }

    @NonNull
    public HitogoPopupBuilder asSimplePopup(String anchorViewTag, String text) {
        setAnchor(anchorViewTag);
        addText(text);

        HitogoPopupBuilder customBuilder = getController().provideSimplePopup(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible();
        }
    }

    @NonNull
    public HitogoPopupBuilder setOffset(int xoff, int yoff) {
        this.xoff = xoff;
        this.yoff = yoff;
        return this;
    }

    @NonNull
    @RequiresApi(LOLLIPOP)
    public HitogoPopupBuilder setElevation(float elevation) {
        this.elevation = elevation;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder setBackgroundDrawable(@DrawableRes Integer drawableRes) {
        this.drawableRes = drawableRes;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder setAnimationStyle(int animationStyle) {
        this.animationStyle = animationStyle;
        return this;
    }

    @NonNull
    @RequiresApi(M)
    public HitogoPopupBuilder setTransition(Transition enterTransition, Transition exitTransition) {
        this.enterTransition = enterTransition;
        this.exitTransition = exitTransition;
        return this;
    }

    @Override
    protected void onProvideData(HitogoAlertParamsHolder holder) {
        holder.provideInteger(HitogoPopupParamsKeys.DRAWABLE_RES_KEY, drawableRes);
        holder.provideInteger(HitogoPopupParamsKeys.ANIMATION_STYLE_KEY, animationStyle);
        holder.provideInteger(HitogoPopupParamsKeys.ANCHOR_VIEW_ID_KEY, anchorViewId);

        holder.provideInteger(HitogoPopupParamsKeys.X_OFF_KEY, xoff);
        holder.provideInteger(HitogoPopupParamsKeys.Y_OFF_KEY, yoff);
        holder.provideInteger(HitogoPopupParamsKeys.WIDTH_KEY, width);
        holder.provideInteger(HitogoPopupParamsKeys.HEIGHT_KEY, height);

        holder.provideFloat(HitogoPopupParamsKeys.ELEVATION_KEY, elevation);
        holder.provideString(HitogoPopupParamsKeys.ANCHOR_VIEW_TAG_KEY, anchorViewTag);
        holder.provideBoolean(HitogoPopupParamsKeys.IS_DISMISSIBLE_KEY, isDismissible);

        holder.provideTransition(enterTransition);
        holder.provideTransition(exitTransition);
    }
}
