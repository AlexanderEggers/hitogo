package org.hitogo.dialog;

import android.os.Bundle;

import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoDialogParams extends HitogoParams {

    private String title;
    private String text;
    private Integer dialogThemeResId;
    private boolean isDismissible;
    private Bundle arguments;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        title = holder.getString("title");
        text = holder.getString("text");
        dialogThemeResId = holder.getInteger("dialogThemeResId");
        isDismissible = holder.getBoolean("containerId");
        arguments = holder.getExtras("arguments");
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Integer getDialogThemeResId() {
        return dialogThemeResId;
    }

    public boolean isDismissible() {
        return isDismissible;
    }

    public Bundle getArguments() {
        return arguments;
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }
}
