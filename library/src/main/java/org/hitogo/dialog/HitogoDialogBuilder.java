package org.hitogo.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import org.hitogo.button.HitogoButton;
import org.hitogo.button.HitogoButtonObject;
import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialogBuilder extends HitogoBuilder {

    private String title;
    private String text;
    private Integer dialogThemeResId;
    private boolean isDismissible;
    private Bundle arguments;

    protected List<HitogoButtonObject> buttons;

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

        HitogoDialogBuilder customBuilder = getController().provideSimpleDialog(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible();
        }
    }

    @NonNull
    public HitogoDialogBuilder addButton(@NonNull HitogoButton...buttons) {
        Collections.addAll(this.buttons, buttons);
        return this;
    }

    @NonNull
    public HitogoDialogBuilder useStyle(@Nullable @StyleRes Integer dialogThemeResId) {
        this.dialogThemeResId = dialogThemeResId;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        holder.provideString("title", title);
        holder.provideString("text", text);
        holder.provideInteger("dialogThemeResId", dialogThemeResId);
        holder.provideBoolean("isDismissible", isDismissible);
        holder.provideExtras("arguments", arguments);

        holder.provideCallToActionButtons(buttons);
    }
}
