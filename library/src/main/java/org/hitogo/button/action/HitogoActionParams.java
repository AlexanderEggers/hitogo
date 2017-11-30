package org.hitogo.button.action;

import org.hitogo.button.core.HitogoButtonParams;
import org.hitogo.button.core.HitogoButtonParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoActionParams extends HitogoButtonParams {

    private String text;
    private int[] viewIds;
    private boolean hasActionView;
    private boolean closeAfterClick;

    @Override
    protected void onCreateParams(HitogoButtonParamsHolder holder, HitogoButtonParams buttonParams) {
        text = holder.getString(HitogoActionParamsKeys.TEXT_KEY);
        viewIds = holder.getIntList(HitogoActionParamsKeys.VIEW_IDS_KEY);
        hasActionView = holder.getBoolean(HitogoActionParamsKeys.HAS_ACTION_VIEW_KEY);
        closeAfterClick = holder.getBoolean(HitogoActionParamsKeys.CLOSE_AFTER_CLICK_KEY);
    }

    public String getText() {
        return text;
    }

    public int[] getViewIds() {
        return viewIds;
    }

    public boolean hasActionView() {
        return hasActionView;
    }

    public boolean isClosingAfterClick() {
        return closeAfterClick;
    }
}
