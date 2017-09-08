package org.hitogo.dialog;

import android.content.Context;
import android.os.Bundle;

import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.button.HitogoButton;

import java.lang.ref.WeakReference;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialogParams implements HitogoParams {

    private String title;
    private String text;

    private Integer dialogThemeResId;

    private boolean isDismissible;
    private int hashCode;

    private Bundle bundle;
    private WeakReference<Context> context;
    private HitogoController controller;
    private List<HitogoButton> callToActionButtons;

    HitogoDialogParams(HitogoDialogBuilder builder) {
        title = builder.title;
        text = builder.text;

        dialogThemeResId = builder.dialogThemeResId;

        isDismissible = builder.isDismissible;
        hashCode = builder.hashCode;

        bundle = builder.bundle;
        context = new WeakReference<>(builder.context);
        controller = builder.controller;
        callToActionButtons = builder.callToActionButtons;
    }

    public Context getContext() {
        return context.get();
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

    public Bundle getBundle() {
        return bundle;
    }

    public List<HitogoButton> getCallToActionButtons() {
        return callToActionButtons;
    }


    @Override
    public HitogoController getController() {
        return controller;
    }

    @Override
    public int getHashCode() {
        return hashCode;
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }
}
