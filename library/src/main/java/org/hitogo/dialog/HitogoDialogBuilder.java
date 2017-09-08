package org.hitogo.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;

import org.hitogo.core.button.HitogoButton;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoObject;
import org.hitogo.view.HitogoViewBuilder;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoDialogBuilder {

    String title;
    String text;

    Integer dialogThemeResId;

    boolean isDismissible;
    int hashCode;

    Bundle bundle;
    Context context;
    HitogoController controller;
    List<HitogoButton> callToActionButtons;

    public HitogoDialogBuilder(@NonNull Context context, @NonNull HitogoController controller) {
        this.context = context;
        this.controller = controller;
        this.callToActionButtons = new ArrayList<>();
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
    public HitogoDialogBuilder setBundle(@NonNull Bundle bundle) {
        this.bundle = bundle;
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

        HitogoDialogBuilder customBuilder = controller.getSimpleDialog(this);
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

    @NonNull
    public HitogoDialogBuilder controlledBy(@NonNull HitogoController controller) {
        this.controller = controller;
        return this;
    }

    public HitogoObject build() {
        if (this.text == null) {
            throw new InvalidParameterException("Text parameter cannot be null.");
        }

        if (this.title == null) {
            throw new InvalidParameterException("Title parameter cannot be null.");
        }

        if (callToActionButtons.isEmpty()) {
            throw new InvalidParameterException("This hitogo need at least one button.");
        }

        if (callToActionButtons.size() > 3) {
            Log.d(HitogoViewBuilder.class.getName(), "The dialog can handle only up to 3 different buttons.");
        }

        hashCode = this.text.hashCode();
        try {
            return new HitogoDialog().startHitogo(new HitogoDialogParams(this));
        } catch (IllegalAccessException e) {
            Log.wtf(HitogoViewBuilder.class.getName(), "Build process failed.");
            return null;
        }
    }

    public void show(@NonNull Activity activity) {
        build().show(activity);
    }

    public void showDelayed(@NonNull final Activity activity, long millis) {
        if (millis == 0) {
            show(activity);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!activity.isFinishing()) {
                        build().show(activity);
                    }
                }
            }, millis);
        }
    }
}
