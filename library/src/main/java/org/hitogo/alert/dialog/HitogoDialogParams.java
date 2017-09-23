package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialogParams extends HitogoAlertParams {

    private String title;
    private String text;

    private Integer titleViewId;
    private Integer textViewId;
    private Integer dialogThemeResId;

    private boolean isDismissible;

    @Override
    protected void onCreateParams(HitogoAlertParamsHolder holder) {
        title = holder.getString("title");
        text = holder.getString("text");
        titleViewId = holder.getInteger("titleViewId");
        textViewId = holder.getInteger("textViewId");
        dialogThemeResId = holder.getInteger("dialogThemeResId");
        isDismissible = holder.getBoolean("containerId");
    }

    @NonNull
    public String getTitle() {
        return title != null ? title : "";
    }

    public String getText() {
        return text;
    }

    public Integer getTextViewId() {
        return textViewId;
    }

    public Integer getTitleViewId() {
        return titleViewId;
    }

    public Integer getDialogThemeResId() {
        return dialogThemeResId;
    }

    public boolean isDismissible() {
        return isDismissible;
    }
}
