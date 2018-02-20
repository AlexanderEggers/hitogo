package org.hitogo.button.view;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoParamsHolder;

public class ViewButtonParams extends ButtonParams {

    private Integer iconId;
    private Integer clickId;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        Integer holderIconId = holder.getInteger(ViewButtonParamsKeys.ICON_ID_KEY);
        Integer holderClickId = holder.getInteger(ViewButtonParamsKeys.CLICK_ID_KEY);
        initialiseViewIds(holderIconId, holderClickId);
    }

    protected void initialiseViewIds(Integer holderIconId, Integer holderClickId) {
        iconId = holderIconId != null ? holderIconId : -1;
        clickId = holderClickId != null ? holderClickId : iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public Integer getClickId() {
        return clickId;
    }

    @Override
    public boolean hasButtonView() {
        return getIconId() != -1;
    }
}
