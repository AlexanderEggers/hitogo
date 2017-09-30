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
    protected void onCreateParams(HitogoButtonParamsHolder holder) {
        text = holder.getString("text");
        viewIds = holder.getIntList("viewIds");
        hasActionView = holder.getBoolean("hasActionView");
        closeAfterClick = holder.getBoolean("closeAfterClick");
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
