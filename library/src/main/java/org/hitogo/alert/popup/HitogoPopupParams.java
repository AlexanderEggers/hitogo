package org.hitogo.alert.popup;

import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoPopupParams extends HitogoAlertParams {

    private Integer drawableRes;
    private Float elevation;
    private String anchorViewTag;

    private int anchorViewId;
    private int xoff;
    private int yoff;
    private int width;
    private int height;

    private boolean isDismissible;

    @Override
    protected void onCreateParams(HitogoAlertParamsHolder holder) {
        drawableRes = holder.getInteger("drawableRes");
        elevation = holder.getFloat("elevation");
        anchorViewTag = holder.getString("anchorViewTag");

        anchorViewId = holder.getInteger("anchorViewId");
        xoff = holder.getInteger("xoff");
        yoff = holder.getInteger("yoff");
        width = holder.getInteger("width");
        height = holder.getInteger("height");

        isDismissible = holder.getBoolean("isDismissible");
    }

    public int getAnchorViewId() {
        return anchorViewId;
    }

    public int getXoff() {
        return xoff;
    }

    public int getYoff() {
        return yoff;
    }

    public Integer getDrawableRes() {
        return drawableRes;
    }

    public Float getElevation() {
        return elevation;
    }

    public String getAnchorViewTag() {
        return anchorViewTag;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected boolean isDismissible() {
        return isDismissible;
    }
}
