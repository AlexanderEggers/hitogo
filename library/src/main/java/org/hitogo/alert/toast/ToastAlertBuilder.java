package org.hitogo.alert.toast;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.button.core.Button;

public interface ToastAlertBuilder extends AlertBuilder<ToastAlertBuilder, ToastAlert> {

    @NonNull
    ToastAlertBuilder asDismissible(@Nullable Button closeButton);

    @NonNull
    ToastAlertBuilder setGravity(@IntRange(from = 0) int gravity, int xOffset, int yOffset);

    @NonNull
    ToastAlertBuilder setDuration(@IntRange(from = 0, to = 1) int duration);

    @NonNull
    ToastAlertBuilder setMargins(float horizontalMargin, float verticalMargin);
}
