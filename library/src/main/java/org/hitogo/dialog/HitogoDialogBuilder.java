package org.hitogo.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;

import org.hitogo.button.HitogoButton;
import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoParams;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoDialogBuilder extends HitogoBuilder {

    private String title;
    private String text;
    private Integer dialogThemeResId;
    private boolean isDismissible;
    private Bundle arguments;

    protected List<HitogoButton> callToActionButtons;

    public HitogoDialogBuilder(@NonNull Class<? extends HitogoObject> targetClass,
                               @NonNull Class<? extends HitogoParams> paramClass,
                               @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, HitogoObject.HitogoType.DIALOG);
    }

    @NonNull
    public HitogoDialogBuilder setTitle(@NonNull String title) {
        this.title = title;
        return this;
    }

    @NonNull
    public HitogoDialogBuilder setText(@NonNull String text) {
        this.text = text;
        return this;
    }

    @NonNull
    public HitogoDialogBuilder setBundle(@NonNull Bundle arguments) {
        this.arguments = arguments;
        return this;
    }

    @NonNull
    public HitogoDialogBuilder asDismissible() {
        this.isDismissible = true;
        return this;
    }

    @NonNull
    public HitogoDialogBuilder asSimple(@NonNull String title, @NonNull String text) {
        this.title = title;
        this.text = text;

        HitogoDialogBuilder customBuilder = getController().getSimpleDialog(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible();
        }
    }

    @NonNull
    public HitogoDialogBuilder addActionButton(@NonNull HitogoButton...buttons) {
        for(HitogoButton button : buttons) {
            if (!button.isCloseButton()) {
                callToActionButtons.add(button);
            } else {
                Log.e(HitogoDialogBuilder.class.getName(), "Cannot add close buttons as call to action buttons.");
            }
        }
        return this;
    }

    @NonNull
    public HitogoDialogBuilder useStyle(@Nullable @StyleRes Integer dialogThemeResId) {
        this.dialogThemeResId = dialogThemeResId;
        return this;
    }

    @Override
    protected Bundle onCreatePublicBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("text", text);
        bundle.putSerializable("dialogThemeResId", dialogThemeResId);
        bundle.putBoolean("isDismissible", isDismissible);
        bundle.putBundle("arguments", arguments);
        return bundle;
    }

    @Override
    public List<HitogoButton> getCallToActionButtons() {
        return callToActionButtons;
    }
}
