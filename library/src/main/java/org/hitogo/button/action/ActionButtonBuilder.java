package org.hitogo.button.action;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.ButtonBuilder;

public interface ActionButtonBuilder extends ButtonBuilder<ActionButtonBuilder, ActionButton> {

    @NonNull
    ActionButtonBuilder forCloseAction();

    @NonNull
    ActionButtonBuilder forCloseAction(@IdRes Integer closeIconId);

    @NonNull
    ActionButtonBuilder forCloseAction(@IdRes Integer closeIconId, @IdRes @Nullable Integer optionalCloseViewId);

    @NonNull
    ActionButtonBuilder forViewAction(@IdRes Integer closeIconId);

    @NonNull
    ActionButtonBuilder forViewAction(@IdRes Integer iconId, @IdRes @Nullable Integer clickId);

    @NonNull
    ActionButtonBuilder forClickOnlyAction();
}
