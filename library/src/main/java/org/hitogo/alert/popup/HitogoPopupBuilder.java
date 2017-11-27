package org.hitogo.alert.popup;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertBuilder;
import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;
import org.hitogo.alert.core.HitogoAlertType;
import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoContainer;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoPopupBuilder extends HitogoAlertBuilder<HitogoPopupBuilder> {

    private static final HitogoAlertType type = HitogoAlertType.POPUP;

    private Integer containerId;
    private Integer layoutViewId;

    private boolean isDismissible;
    private boolean closeOthers = true;
    private boolean dismissByClick;

    private HitogoAnimation hitogoAnimation;

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
    public HitogoPopupBuilder closeOthers() {
        this.closeOthers = true;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder allowOthers() {
        this.closeOthers = false;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder consumeLayoutClick() {
        this.dismissByClick = true;
        return this;
    }

    @NonNull
    public HitogoPopupBuilder ignoreLayoutClick() {
        this.dismissByClick = false;
        return this;
    }

    @Override
    protected void onProvideData(HitogoAlertParamsHolder holder) {
        holder.provideInteger("containerId", containerId);
        holder.provideInteger("layoutViewId", layoutViewId);
        holder.provideBoolean("isDismissible", isDismissible);
        holder.provideBoolean("closeOthers", closeOthers);
        holder.provideBoolean("dismissByClick", dismissByClick);

        holder.provideAnimation(hitogoAnimation);
    }
}
