package org.hitogo.alert.dialog;

import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoDialogParams extends HitogoAlertParams {

    private Integer dialogThemeResId;
    private boolean isDismissible;

    @Override
    protected void onCreateParams(HitogoAlertParamsHolder holder, HitogoAlertParams alertParams) {
        dialogThemeResId = holder.getInteger("dialogThemeResId");
        isDismissible = holder.getBoolean("containerId");
    }

    public Integer getDialogThemeResId() {
        return dialogThemeResId;
    }

    public boolean isDismissible() {
        return isDismissible;
    }
}
