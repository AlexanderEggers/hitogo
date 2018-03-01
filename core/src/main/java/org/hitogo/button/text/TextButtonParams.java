package org.hitogo.button.text;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Params object for the TextButton.
 *
 * @see ButtonParams
 * @since 1.0.0
 */
public class TextButtonParams extends ButtonParams {

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        //do nothing
    }

    @Override
    public int getIconId() {
        return -1;
    }

    @Override
    public Integer getClickId() {
        return null;
    }

    @Override
    public boolean hasButtonView() {
        return false;
    }
}
