package org.hitogo.dialog;

import android.os.Bundle;

import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoDialogParams extends HitogoParams {

    private String title;
    private String text;
    private Integer dialogThemeResId;
    private boolean isDismissible;
    private Bundle arguments;

    protected HitogoDialogParams(HitogoBuilder builder, Bundle publicBundle, Bundle privateBundle) {
        super(builder, publicBundle, privateBundle);
    }

    @Override
    protected void onCreateParams(Bundle bundle) {
        title = bundle.getString("title");
        text = bundle.getString("text");
        dialogThemeResId = (Integer) bundle.getSerializable("dialogThemeResId");
        isDismissible = bundle.getBoolean("containerId");
        arguments = bundle.getBundle("arguments");
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
