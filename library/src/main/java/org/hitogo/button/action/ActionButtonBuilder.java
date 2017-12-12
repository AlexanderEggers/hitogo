package org.hitogo.button.action;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonParamsHolder;
import org.hitogo.button.core.ButtonType;
import org.hitogo.core.HitogoContainer;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActionButtonBuilder extends ButtonBuilder<ActionButtonBuilder, ActionButton> {

    private static final ButtonType type = ButtonType.ACTION;

    private int[] viewIds;
    private boolean hasButtonView;

    public ActionButtonBuilder(@NonNull Class<? extends ButtonImpl> targetClass,
                               @NonNull Class<? extends ButtonParams> paramClass,
                               @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public ActionButtonBuilder forViewAction() {
        return forViewAction(getController().provideDefaultCloseIconId(),
                getController().provideDefaultCloseClickId());
    }

    @NonNull
    public ActionButtonBuilder forViewAction(Integer closeIconId) {
        return forViewAction(closeIconId, getController().provideDefaultCloseClickId());
    }

    @NonNull
    public ActionButtonBuilder forViewAction(Integer closeIconId, @Nullable Integer optionalCloseViewId) {
        this.hasButtonView = true;
        this.viewIds = new int[2];
        this.viewIds[0] = closeIconId;
        this.viewIds[1] = optionalCloseViewId != null ? optionalCloseViewId : closeIconId;
        return this;
    }

    @NonNull
    public ActionButtonBuilder forClickOnlyAction() {
        this.hasButtonView = false;
        this.viewIds = new int[0];
        return this;
    }

    @Override
    protected void onProvideData(ButtonParamsHolder holder) {
        holder.provideIntArray(ActionButtonParamsKeys.VIEW_IDS_KEY, viewIds);
        holder.provideBoolean(ActionButtonParamsKeys.HAS_BUTTON_VIEW_KEY, hasButtonView);
    }
}
