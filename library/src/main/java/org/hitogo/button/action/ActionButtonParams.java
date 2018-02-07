package org.hitogo.button.action;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActionButtonParams extends ButtonParams {

    private int[] viewIds;
    private boolean hasButtonView;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder, ButtonParams buttonParams) {
        viewIds = holder.getSerializable(ActionButtonParamsKeys.VIEW_IDS_KEY);
        hasButtonView = holder.getBoolean(ActionButtonParamsKeys.HAS_BUTTON_VIEW_KEY);
    }

    @Override
    public int[] getViewIds() {
        return viewIds;
    }

    @Override
    public boolean hasButtonView() {
        return hasButtonView;
    }
}
