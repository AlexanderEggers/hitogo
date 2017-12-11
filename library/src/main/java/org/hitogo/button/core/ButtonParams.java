package org.hitogo.button.core;

import android.os.Bundle;

import org.hitogo.button.action.DefaultActionButtonListener;
import org.hitogo.core.HitogoParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonParams extends HitogoParams<ButtonParamsHolder, ButtonParams> {

    private ButtonListener listener;
    private ButtonType type;

    protected void provideData(ButtonParamsHolder holder, Bundle privateBundle) {
        this.type = (ButtonType) privateBundle.getSerializable(ButtonParamsKeys.TYPE_KEY);
        this.listener = holder.getListener();

        if(this.listener == null) {
            this.listener = new DefaultActionButtonListener();
        }

        onCreateParams(holder, this);
    }

    protected abstract void onCreateParams(ButtonParamsHolder holder, ButtonParams buttonParams);

    public final ButtonListener getListener() {
        return listener;
    }

    public final ButtonType getType() {
        return type;
    }

    public abstract String getText();

    public abstract int[] getViewIds();

    public abstract boolean hasButtonView();

    public abstract boolean isClosingAfterClick();
}
