package org.hitogo.button.close;

import org.hitogo.button.view.ViewButtonParams;
import org.hitogo.button.view.ViewButtonParamsKeys;
import org.hitogo.core.HitogoParamsHolder;

public class CloseButtonParams extends ViewButtonParams {

    private Integer iconId;
    private Integer clickId;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        iconId = holder.getInteger(ViewButtonParamsKeys.ICON_ID_KEY);
        Integer defaultIconId = getController().provideDefaultButtonCloseIconId(getButtonType());
        defaultIconId = defaultIconId != null ? defaultIconId : -1;
        iconId = iconId != null ? iconId : defaultIconId;

        clickId = holder.getInteger(ViewButtonParamsKeys.CLICK_ID_KEY);
        clickId = clickId != null ? clickId : getController().provideDefaultButtonCloseClickId(getButtonType());
    }

    @Override
    public int getIconId() {
        return iconId;
    }

    @Override
    public Integer getClickId() {
        return clickId;
    }
}
