package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.Hitogo;
import org.hitogo.alert.core.HitogoAlertBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;
import org.hitogo.alert.core.HitogoAlertType;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoDialogBuilder extends HitogoAlertBuilder<HitogoDialogBuilder> {

    private static final HitogoAlertType type = HitogoAlertType.DIALOG;

    private Integer dialogThemeResId;
    private boolean isDismissible;

    public HitogoDialogBuilder(@NonNull Class<? extends HitogoAlert> targetClass,
                               @NonNull Class<? extends HitogoAlertParams> paramClass,
                               @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public HitogoDialogBuilder asDismissible() {
        this.isDismissible = true;
        return this;
    }

    @NonNull
    public HitogoDialogBuilder asSimple(@NonNull String title, @NonNull String text) {
        super.setTitle(title);
        super.addText(text);

        HitogoDialogBuilder customBuilder = getController().provideSimpleDialog(this);
        if (customBuilder != null) {
            return customBuilder;
        } else {
            return asDismissible();
        }
    }

    @NonNull
    public HitogoDialogBuilder addButton(@NonNull String... buttonContent) {
        for (String buttonText : buttonContent) {
            HitogoButton button = Hitogo.with(getContainer())
                    .asButton()
                    .forClickOnlyAction()
                    .setText(buttonText)
                    .build();
            super.addButton(button);
        }
        return this;
    }

    @NonNull
    public HitogoDialogBuilder useStyle(@Nullable @StyleRes Integer dialogThemeResId) {
        this.dialogThemeResId = dialogThemeResId;
        return this;
    }

    @Override
    protected void onProvideData(HitogoAlertParamsHolder holder) {
        holder.provideInteger("dialogThemeResId", dialogThemeResId);
        holder.provideBoolean("isDismissible", isDismissible);
    }
}
