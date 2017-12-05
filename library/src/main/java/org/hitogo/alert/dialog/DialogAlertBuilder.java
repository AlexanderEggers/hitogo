package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import org.hitogo.button.core.Button;
import org.hitogo.core.Hitogo;
import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertParamsHolder;
import org.hitogo.alert.core.AlertType;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogAlertBuilder extends AlertBuilder<DialogAlertBuilder, DialogAlert> {

    private static final AlertType type = AlertType.DIALOG;

    private Integer dialogThemeResId;
    private boolean isDismissible;

    public DialogAlertBuilder(@NonNull Class<? extends AlertImpl> targetClass,
                              @NonNull Class<? extends AlertParams> paramClass,
                              @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public DialogAlertBuilder asDismissible() {
        this.isDismissible = true;
        return this;
    }

    @NonNull
    public DialogAlertBuilder asSimpleDialog(@NonNull String title, @NonNull String text) {
        super.setTitle(title);
        super.addText(text);

        DialogAlertBuilder customBuilder = getController().provideSimpleDialog(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible();
        }
    }

    @NonNull
    public DialogAlertBuilder addButton(@NonNull String... buttonContent) {
        for (String buttonText : buttonContent) {
            Button button = Hitogo.with(getContainer())
                    .asButton()
                    .forClickOnlyAction()
                    .setText(buttonText)
                    .build();
            super.addButton(button);
        }
        return this;
    }

    @NonNull
    public DialogAlertBuilder setStyle(@Nullable @StyleRes Integer dialogThemeResId) {
        this.dialogThemeResId = dialogThemeResId;
        return this;
    }

    @Override
    protected void onProvideData(AlertParamsHolder holder) {
        holder.provideInteger(DialogAlertParamsKeys.DIALOG_THEME_RES_ID, dialogThemeResId);
        holder.provideBoolean(DialogAlertParamsKeys.IS_DISMISSIBLE_KEY, isDismissible);
    }
}
