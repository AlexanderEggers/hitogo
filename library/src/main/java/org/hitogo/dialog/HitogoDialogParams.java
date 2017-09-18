package org.hitogo.dialog;

import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialogParams extends HitogoParams {

    private String title;
    private String text;

    private Integer titleViewId;
    private Integer textViewId;
    private Integer dialogThemeResId;

    private boolean isDismissible;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        title = holder.getString("title");
        text = holder.getString("text");
        titleViewId = holder.getInteger("titleViewId");
        textViewId = holder.getInteger("textViewId");
        dialogThemeResId = holder.getInteger("dialogThemeResId");
        isDismissible = holder.getBoolean("containerId");
    }

    public String getTitle() {
        return title;
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
