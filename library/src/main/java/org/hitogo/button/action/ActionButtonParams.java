package org.hitogo.button.action;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActionButtonParams extends ButtonParams {

    private String text;
    private int[] viewIds;
    private boolean hasActionView;
    private boolean closeAfterClick;

    @Override
    protected void onCreateParams(ButtonParamsHolder holder, ButtonParams buttonParams) {
        text = holder.getString(ActionButtonParamsKeys.TEXT_KEY);
        viewIds = holder.getIntList(ActionButtonParamsKeys.VIEW_IDS_KEY);
        hasActionView = holder.getBoolean(ActionButtonParamsKeys.HAS_ACTION_VIEW_KEY);
        closeAfterClick = holder.getBoolean(ActionButtonParamsKeys.CLOSE_AFTER_CLICK_KEY);
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
