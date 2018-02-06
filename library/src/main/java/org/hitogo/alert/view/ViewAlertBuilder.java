package org.hitogo.alert.view;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoAnimation;

public interface ViewAlertBuilder extends AlertBuilder<ViewAlertBuilder, ViewAlert> {

    @NonNull
    ViewAlertBuilder withAnimations();

    @NonNull
    ViewAlertBuilder withAnimations(boolean withAnimation);

    @NonNull
    ViewAlertBuilder withAnimations(@IdRes @Nullable Integer innerLayoutViewId);

    @NonNull
    ViewAlertBuilder withAnimations(@Nullable HitogoAnimation animation);

    @NonNull
    ViewAlertBuilder withAnimations(@Nullable HitogoAnimation animation,
                                    @IdRes @Nullable Integer innerLayoutViewId);

    @NonNull
    ViewAlertBuilder asDismissible();

    @NonNull
    ViewAlertBuilder asDismissible(boolean isDismissible);

    @NonNull
    ViewAlertBuilder asDismissible(@Nullable Button closeButton);

    @NonNull
    ViewAlertBuilder asIgnoreLayout();

    @NonNull
    ViewAlertBuilder asOverlay();

    @NonNull
    ViewAlertBuilder asOverlay(@IdRes @Nullable Integer overlayId);

    @NonNull
    ViewAlertBuilder asSimpleView(@NonNull String text);

    @NonNull
    ViewAlertBuilder asLayoutChild();

    @NonNull
    ViewAlertBuilder asLayoutChild(@IdRes @Nullable Integer containerId);

    @NonNull
    ViewAlertBuilder closeOthers(boolean closeOthers);

    @NonNull
    ViewAlertBuilder dismissByLayoutClick(boolean dismissByClick);

    void show(boolean force);

    void showLater(boolean showLater);

    void showDelayed(long millis);

    void showDelayed(long millis, boolean force);
}
