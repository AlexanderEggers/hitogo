package org.hitogo.button.view;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewButtonParams extends ButtonParams {

    private int[] viewIds;
    private boolean hasButtonView;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        viewIds = holder.getSerializable(ViewButtonParamsKeys.VIEW_IDS_KEY);
    }

    @Override
    public int[] getViewIds() {
        return viewIds;
    }

    @Override
    public boolean hasButtonView() {
        return viewIds.length != 0;
    }
}
