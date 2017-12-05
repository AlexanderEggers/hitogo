package org.hitogo.alert.dialog;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogAlertParams extends AlertParams {

    private Integer dialogThemeResId;
    private boolean isDismissible;

    @Override
    protected void onCreateParams(AlertParamsHolder holder, AlertParams alertParams) {
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
