package org.hitogo.button.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonParams extends HitogoParams<HitogoParamsHolder, ButtonParams> {

    private String text;
    private boolean closeAfterClick;

    private ButtonListener listener;
    private Object buttonParameter;

    @Override
    protected void provideData(HitogoParamsHolder holder) {
        this.text = holder.getString(ButtonParamsKeys.TEXT_KEY);
        this.closeAfterClick = holder.getBoolean(ButtonParamsKeys.CLOSE_AFTER_CLICK_KEY);

        this.listener = holder.getCustomObject(ButtonParamsKeys.BUTTON_LISTENER_KEY);
        this.buttonParameter = holder.getCustomObject(ButtonParamsKeys.BUTTON_PARAMETER_KEY);

        onCreateParams(holder, this);
    }

    public abstract int[] getViewIds();

    public abstract boolean hasButtonView();

    public String getText() {
        return text;
    }

    @NonNull
    public ButtonListener getListener() {
        return listener != null ? listener : new DefaultButtonListener();
    }

    @Nullable
    public Object getButtonParameter() {
        return buttonParameter;
    }

    public boolean isClosingAfterClick() {
        return closeAfterClick;
    }
}
