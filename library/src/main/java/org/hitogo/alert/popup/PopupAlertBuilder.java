package org.hitogo.alert.popup;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
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
    public PopupAlertBuilder asDismissible();

    @NonNull
    public PopupAlertBuilder asDismissible(boolean isDismissible);

    @NonNull
    public PopupAlertBuilder asDismissible(@Nullable Button closeButton);

    @NonNull
    public PopupAlertBuilder setAnchor(@IdRes int anchorViewId);

    @NonNull
    public PopupAlertBuilder setAnchor(String anchorViewTag);

    @NonNull
    public PopupAlertBuilder setOffset(int xoff, int yoff);

    @NonNull
    @RequiresApi(KITKAT)
    public PopupAlertBuilder setGravity(int gravity);

    @NonNull
    @RequiresApi(LOLLIPOP)
    public PopupAlertBuilder setElevation(float elevation);

    @NonNull
    public PopupAlertBuilder setBackgroundDrawable(@DrawableRes Integer drawableRes);

    @NonNull
    public PopupAlertBuilder setSize(int width, int height);

    @NonNull
    public PopupAlertBuilder setAnimationStyle(@StyleRes int animationStyle);

    public PopupAlertBuilder setTouchListener(@NonNull View.OnTouchListener onTouchListener);

    @NonNull
    @RequiresApi(M)
    public PopupAlertBuilder setTransition(@Nullable Transition enterTransition, @Nullable Transition exitTransition);

    void show(boolean force);

    void showLater(boolean showLater);

    void showDelayed(long millis);

    void showDelayed(long millis, boolean force);
}
