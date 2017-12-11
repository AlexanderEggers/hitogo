package org.hitogo.button.action;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActionButtonParams extends ButtonParams {

    private String text;
    private int[] viewIds;
    private boolean hasButtonView;
    private boolean closeAfterClick;

    @Override
    protected void onCreateParams(ButtonParamsHolder holder, ButtonParams buttonParams) {
        text = holder.getString(ActionButtonParamsKeys.TEXT_KEY);
        viewIds = holder.getIntList(ActionButtonParamsKeys.VIEW_IDS_KEY);
        hasButtonView = holder.getBoolean(ActionButtonParamsKeys.HAS_BUTTON_VIEW_KEY);
        closeAfterClick = holder.getBoolean(ActionButtonParamsKeys.CLOSE_AFTER_CLICK_KEY);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int[] getViewIds() {
        return viewIds;
    }

    @Override
    public boolean hasButtonView() {
        return hasButtonView;
    }

    @Override
    public boolean isClosingAfterClick() {
        return closeAfterClick;
    }
}
