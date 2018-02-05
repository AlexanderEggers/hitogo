package org.hitogo.button.action;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.ButtonBuilder;

public interface ActionButtonBuilder extends ButtonBuilder<ActionButtonBuilder, ActionButton> {

    @NonNull
    ActionButtonBuilder forCloseAction();

    @NonNull
    ActionButtonBuilder forCloseAction(Integer closeIconId);

    @NonNull
    ActionButtonBuilder forCloseAction(Integer closeIconId, @Nullable Integer optionalCloseViewId);

    @NonNull
    ActionButtonBuilder forViewAction(Integer closeIconId);

    @NonNull
    ActionButtonBuilder forViewAction(Integer iconId, @Nullable Integer clickId);

    @NonNull
    ActionButtonBuilder forClickOnlyAction();
}
