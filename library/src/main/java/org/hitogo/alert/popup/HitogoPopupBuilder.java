package org.hitogo.alert.popup;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.widget.LinearLayout;

import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertBuilder;
import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;
import org.hitogo.alert.core.HitogoAlertType;
import org.hitogo.core.HitogoContainer;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoPopupBuilder extends HitogoAlertBuilder<HitogoPopupBuilder> {

    private static final HitogoAlertType type = HitogoAlertType.POPUP;

    private Integer drawableRes;
    private Float elevation;
    private String anchorViewTag;

    private int anchorViewId;
    private int xoff;
    private int yoff;

    private int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    private int height = LinearLayout.LayoutParams.WRAP_CONTENT;

    private boolean isDismissible;

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
    public HitogoPopupBuilder setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    protected void onProvideData(HitogoAlertParamsHolder holder) {
        holder.provideInteger("drawableRes", drawableRes);
        holder.provideFloat("elevation", elevation);
        holder.provideString("anchorViewTag", anchorViewTag);

        holder.provideInteger("anchorViewId", anchorViewId);
        holder.provideInteger("xoff", xoff);
        holder.provideInteger("yoff", yoff);
        holder.provideInteger("width", width);
        holder.provideInteger("height", height);

        holder.provideBoolean("isDismissible", isDismissible);
    }
}
