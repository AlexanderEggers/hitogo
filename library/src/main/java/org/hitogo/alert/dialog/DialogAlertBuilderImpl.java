package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertBuilderImpl;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.button.core.Button;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Builder which includes all basic method to assign specific dialog values to the alert.
 *
 * @see Alert
 * @since 1.0.0
 */
public class DialogAlertBuilderImpl extends AlertBuilderImpl<DialogAlertBuilder, DialogAlert> implements DialogAlertBuilder {

    private Integer dialogThemeResId;
    private boolean isDismissible;

    /**
     * Default constructor for the DialogAlertBuilderImpl.
     *
     * @param targetClass Class object for the requested alert.
     * @param paramClass  Class object for the params object which is used by the alert.
     * @param container   Container which is used as a reference for this alert (context, view,
     *                    controller).
     * @see HitogoContainer
     * @see HitogoController
     * @see AlertType
     * @since 1.0.0
     */
    public DialogAlertBuilderImpl(@NonNull Class<? extends AlertImpl> targetClass,
                                  @NonNull Class<? extends AlertParams> paramClass,
                                  @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, AlertType.DIALOG);
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
    public DialogAlertBuilder addButton(@NonNull String... buttonContent) {
        for (String buttonText : buttonContent) {
            Button button = Hitogo.with(getContainer())
                    .asTextButton()
                    .addText(buttonText)
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
                    .asTextButton()
                    .addText(textRes)
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
