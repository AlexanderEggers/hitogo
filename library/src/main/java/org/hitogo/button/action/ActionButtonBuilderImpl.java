package org.hitogo.button.action;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.ButtonBuilderImpl;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActionButtonBuilderImpl extends ButtonBuilderImpl<ActionButtonBuilder, ActionButton> implements ActionButtonBuilder {

    private int[] viewIds;
    private boolean hasButtonView;

    public ActionButtonBuilderImpl(@NonNull Class<? extends ButtonImpl> targetClass,
                                   @NonNull Class<? extends ButtonParams> paramClass,
                                   @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container);
    }

    @Override
    @NonNull
    public ActionButtonBuilderImpl forCloseAction() {
        return forCloseAction(getController().provideDefaultCloseIconId(),
                getController().provideDefaultCloseClickId());
    }

    @Override
    @NonNull
    public ActionButtonBuilderImpl forCloseAction(@IdRes Integer closeIconId) {
        return forCloseAction(closeIconId, getController().provideDefaultCloseClickId());
    }

    @Override
    @NonNull
    public ActionButtonBuilderImpl forCloseAction(@IdRes Integer closeIconId, @Nullable Integer optionalCloseViewId) {
        return forViewAction(closeIconId, optionalCloseViewId);
    }

    @Override
    @NonNull
    public ActionButtonBuilderImpl forViewAction(Integer closeIconId) {
        return forViewAction(closeIconId, null);
    }

    @Override
    @NonNull
    public ActionButtonBuilderImpl forViewAction(@IdRes Integer iconId, @IdRes @Nullable Integer clickId) {
        this.hasButtonView = true;
        this.viewIds = new int[2];
        this.viewIds[0] = iconId;
        this.viewIds[1] = clickId != null ? clickId : iconId;
        return this;
    }

    @Override
    @NonNull
    public ActionButtonBuilderImpl forClickOnlyAction() {
        this.hasButtonView = false;
        this.viewIds = new int[0];
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        super.onProvideData(holder);

        holder.provideSerializable(ActionButtonParamsKeys.VIEW_IDS_KEY, viewIds);
        holder.provideBoolean(ActionButtonParamsKeys.HAS_BUTTON_VIEW_KEY, hasButtonView);
    }
}
