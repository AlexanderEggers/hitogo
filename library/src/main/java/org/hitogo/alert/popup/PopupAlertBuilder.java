package org.hitogo.alert.popup;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.transition.Transition;
import android.view.View;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.button.core.Button;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.M;

public interface PopupAlertBuilder extends AlertBuilder<PopupAlertBuilder, PopupAlert> {

    @NonNull
    PopupAlertBuilder asDismissible();

    @NonNull
    PopupAlertBuilder asDismissible(boolean isDismissible);

    @NonNull
    PopupAlertBuilder asDismissible(@Nullable Button closeButton);

    @NonNull
    PopupAlertBuilder setAnchor(@IdRes int anchorViewId);

    @NonNull
    PopupAlertBuilder setAnchor(String anchorViewTag);

    @NonNull
    PopupAlertBuilder setOffset(int xoff, int yoff);

    @NonNull
    @RequiresApi(KITKAT)
    PopupAlertBuilder setGravity(int gravity);

    @NonNull
    @RequiresApi(LOLLIPOP)
    PopupAlertBuilder setElevation(float elevation);

    @NonNull
    PopupAlertBuilder setBackgroundDrawable(@DrawableRes Integer drawableRes);

    @NonNull
    PopupAlertBuilder setSize(int width, int height);

    @NonNull
    PopupAlertBuilder setAnimationStyle(@StyleRes int animationStyle);

    @NonNull
    PopupAlertBuilder setTouchListener(@NonNull View.OnTouchListener onTouchListener);

    @NonNull
    @RequiresApi(M)
    PopupAlertBuilder setTransition(@Nullable Transition enterTransition, @Nullable Transition exitTransition);

    @NonNull
    PopupAlertBuilder dismissByLayoutClick(boolean dismissByClick);

    @NonNull
    PopupAlertBuilder asFullscreen(boolean isFullscreen);

    void show(boolean force);

    void showLater(boolean showLater);

    void showDelayed(long millis);

    void showDelayed(long millis, boolean force);
}
