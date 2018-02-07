package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

import org.hitogo.button.core.Button;
import org.hitogo.core.Hitogo;
import org.hitogo.alert.core.AlertBuilderImpl;
import org.hitogo.core.HitogoContainer;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.core.HitogoParamsHolder;
import org.hitogo.core.HitogoUtils;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogAlertBuilderImpl extends AlertBuilderImpl<DialogAlertBuilder, DialogAlert> implements DialogAlertBuilder {

    private Integer dialogThemeResId;
    private boolean isDismissible;

    public DialogAlertBuilderImpl(@NonNull Class<? extends AlertImpl> targetClass,
                                  @NonNull Class<? extends AlertParams> paramClass,
                                  @NonNull HitogoParamsHolder holder,
                                  @NonNull HitogoContainer container) {
        super(targetClass, paramClass, holder, container, AlertType.DIALOG);
    }

    @Override
    @NonNull
    public DialogAlertBuilder asDismissible() {
        return asDismissible(true);
    }

    @Override
    @NonNull
    public DialogAlertBuilder asDismissible(boolean isDismissible) {
        this.isDismissible = isDismissible;
        return this;
    }

    @Override
    @NonNull
    public DialogAlertBuilder asDismissible(@Nullable Button closeButton) {
        this.isDismissible = true;

        if (closeButton != null) {
            return super.setCloseButton(closeButton);
        }
        return this;
    }

    @Override
    @NonNull
    public DialogAlertBuilder asSimpleDialog(@NonNull String title, @StringRes int textRes) {
        return asSimpleDialog(title, HitogoUtils.getStringRes(getContainer().getActivity(), textRes));
    }

    @Override
    @NonNull
    public DialogAlertBuilder asSimpleDialog(@NonNull String title, @NonNull String text) {
        super.setTitle(title);
        super.addText(text);

        DialogAlertBuilder customBuilder = getController().provideSimpleDialog(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible(true);
        }
    }

    @Override
    @NonNull
    public DialogAlertBuilder addButton(@NonNull String... buttonContent) {
        for (String buttonText : buttonContent) {
            Button button = Hitogo.with(getContainer())
                    .asActionButton()
                    .forClickOnlyAction()
                    .setText(buttonText)
                    .build();
            super.addButton(button);
        }
        return this;
    }

    @Override
    @NonNull
    public DialogAlertBuilder addButton(@StringRes int... buttonContent) {
        for (int textRes : buttonContent) {
            Button button = Hitogo.with(getContainer())
                    .asActionButton()
                    .forClickOnlyAction()
                    .setText(textRes)
                    .build();
            super.addButton(button);
        }
        return this;
    }

    @Override
    @NonNull
    public DialogAlertBuilder setStyle(@StyleRes int dialogThemeResId) {
        this.dialogThemeResId = dialogThemeResId;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        super.onProvideData(holder);

        holder.provideInteger(DialogAlertParamsKeys.DIALOG_THEME_RES_ID, dialogThemeResId);
        holder.provideBoolean(DialogAlertParamsKeys.IS_DISMISSIBLE_KEY, isDismissible);
    }
}
