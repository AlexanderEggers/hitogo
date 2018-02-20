package org.hitogo.button.view;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoParamsHolder;

public class ViewButtonParams extends ButtonParams {

    private Integer iconId;
    private Integer clickId;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        iconId = holder.getInteger(ViewButtonParamsKeys.ICON_ID_KEY);
        clickId = holder.getInteger(ViewButtonParamsKeys.CLICK_ID_KEY);
    }

    public int getIconId() {
        return iconId;
    }

    public Integer getClickId() {
        return clickId != null ? clickId : iconId;
    }

    @Override
    public boolean hasButtonView() {
        return iconId != -1;
    }
}
