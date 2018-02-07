package org.hitogo.alert.dialog;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogAlertParams extends AlertParams {

    private Integer dialogThemeResId;
    private boolean isDismissible;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder, AlertParams alertParams) {
        dialogThemeResId = holder.getInteger("dialogThemeResId");
        isDismissible = holder.getBoolean("containerId");
    }

    public Integer getDialogThemeResId() {
        return dialogThemeResId;
    }

    public boolean isDismissible() {
        return isDismissible;
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }

    @Override
    public boolean isClosingOthers() {
        return false;
    }
}
