package org.hitogo.button.close;

import org.hitogo.button.view.ViewButtonParams;
import org.hitogo.button.view.ViewButtonParamsKeys;
import org.hitogo.core.HitogoParamsHolder;

public class CloseButtonParams extends ViewButtonParams {

    private Integer clickId;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        clickId = holder.getInteger(ViewButtonParamsKeys.CLICK_ID_KEY);
    }

    @Override
    public Integer getClickId() {
        return clickId != null ? clickId : getController().provideDefaultButtonCloseClickId(getButtonType());
    }
}
