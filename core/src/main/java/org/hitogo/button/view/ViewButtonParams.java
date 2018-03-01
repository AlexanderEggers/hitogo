package org.hitogo.button.view;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Params object for the ViewButton.
 *
 * @see ButtonParams
 * @since 1.0.0
 */
public class ViewButtonParams extends ButtonParams {

    private Integer iconId;
    private Integer clickId;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        Integer holderIconId = holder.getInteger(ViewButtonParamsKeys.ICON_ID_KEY);
        Integer holderClickId = holder.getInteger(ViewButtonParamsKeys.CLICK_ID_KEY);
        initialiseViewIds(holderIconId, holderClickId);
    }

    /**
     * Initialising the values for the icon and click view ids. Using this method, those values can
     * have different default values depending on the implementation. For example the close button
     * is using a different default value for the click id if this value is null. This value will be
     * provided by the HitogoController instead (provideDefaultButtonCloseClickId(Integer)). The
     * view button (this implementation) uses the icon view id as a fallback if the click view id
     * is null.
     *
     * @param holderIconId  potential icon view id for this button object
     * @param holderClickId potential click view id for this button object
     * @see org.hitogo.button.close.CloseButtonParams
     * @since 1.0.0
     */
    protected void initialiseViewIds(Integer holderIconId, Integer holderClickId) {
        iconId = holderIconId != null ? holderIconId : -1;
        clickId = holderClickId != null ? holderClickId : iconId;
    }

    @Override
    public int getIconId() {
        return iconId;
    }

    @Override
    public Integer getClickId() {
        return clickId;
    }

    @Override
    public boolean hasButtonView() {
        return getIconId() != -1;
    }
}
