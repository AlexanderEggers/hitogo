package org.hitogo.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import org.hitogo.button.HitogoButtonObject;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;
import org.hitogo.core.HitogoType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialogBuilder extends HitogoBuilder<HitogoDialogBuilder> {

    private static final HitogoType type = HitogoType.DIALOG;

    private String title;
    private String text;

    private Integer titleViewId;
    private Integer textViewId;
    private Integer dialogThemeResId;

    private boolean isDismissible;

    private List<HitogoButtonObject> buttons = new ArrayList<>();

    public HitogoDialogBuilder(@NonNull Class<? extends HitogoObject> targetClass,
                               @NonNull Class<? extends HitogoParams> paramClass,
                               @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public HitogoDialogBuilder setTitle(@NonNull String title) {
        return setTitle(getController().provideDefaultTitleViewId(type), title);
    }

    @NonNull
    public HitogoDialogBuilder setTitle(Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return this;
    }

    @NonNull
    public HitogoDialogBuilder setText(@NonNull String text) {
        return setText(getController().provideDefaultTextViewId(type), text);
    }

    @NonNull
    public HitogoDialogBuilder setText(Integer viewId, @NonNull String text) {
        this.textViewId = viewId;
        this.text = text;
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
    public HitogoDialogBuilder addButton(@NonNull HitogoButtonObject... buttons) {
        Collections.addAll(this.buttons, buttons);
        return this;
    }

    @NonNull
    public HitogoDialogBuilder addButton(@NonNull String... buttonContent) {
        for (String buttonText : buttonContent) {
            HitogoButtonObject button = Hitogo.with(getContainer())
                    .asButton()
                    .forClickOnlyAction()
                    .setText(buttonText)
                    .build();
            buttons.add(button);
        }
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
        holder.provideInteger("titleViewId", titleViewId);
        holder.provideInteger("textViewId", textViewId);
        holder.provideBoolean("isDismissible", isDismissible);
        holder.provideCallToActionButtons(buttons);
    }
}
