package org.hitogo.button.core;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.hitogo.button.action.DefaultActionButtonListener;
import org.hitogo.core.HitogoParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonParams extends HitogoParams<ButtonParamsHolder, ButtonParams> {

    private String text;
    private boolean closeAfterClick;
    private ButtonType type;

    private ButtonListener listener;

    protected void provideData(ButtonParamsHolder holder, Bundle privateBundle) {
        this.text = privateBundle.getString(ButtonParamsKeys.TEXT_KEY);
        this.closeAfterClick = privateBundle.getBoolean(ButtonParamsKeys.CLOSE_AFTER_CLICK_KEY);
        this.type = (ButtonType) privateBundle.getSerializable(ButtonParamsKeys.TYPE_KEY);

        this.listener = holder.getListener();

        onCreateParams(holder, this);
    }

    protected abstract void onCreateParams(ButtonParamsHolder holder, ButtonParams buttonParams);

    public abstract int[] getViewIds();

    public abstract boolean hasButtonView();

    public final ButtonType getType() {
        return type;
    }

    public final String getText() {
        return text;
    }

    @NonNull
    public final ButtonListener getListener() {
        return listener != null ? listener : new DefaultActionButtonListener();
    }

    public final boolean isClosingAfterClick() {
        return closeAfterClick;
    }
}
