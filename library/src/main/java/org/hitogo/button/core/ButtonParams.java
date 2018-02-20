package org.hitogo.button.core;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonParams extends HitogoParams<HitogoParamsHolder> {

    private boolean closeAfterClick;
    private ButtonType buttonType;

    private SparseArray<String> textMap;
    private SparseArray<Drawable> drawableMap;

    private ButtonListener listener;
    private Object buttonParameter;

    @Override
    protected void provideData(HitogoParamsHolder holder, HitogoController controller) {
        super.provideData(holder, controller);

        this.closeAfterClick = holder.getBoolean(ButtonParamsKeys.CLOSE_AFTER_CLICK_KEY);
        this.buttonType = holder.getSerializable(ButtonParamsKeys.BUTTON_TYPE_KEY);

        this.textMap = holder.getCustomObject(ButtonParamsKeys.TEXT_KEY);
        this.drawableMap = holder.getCustomObject(ButtonParamsKeys.DRAWABLE_KEY);
        this.listener = holder.getCustomObject(ButtonParamsKeys.BUTTON_LISTENER_KEY);
        this.buttonParameter = holder.getCustomObject(ButtonParamsKeys.BUTTON_PARAMETER_KEY);

        onCreateParams(holder);
    }

    public abstract int getIconId();

    public abstract Integer getClickId();

    public abstract boolean hasButtonView();

    @NonNull
    public SparseArray<String> getTextMap() {
        return textMap != null ? textMap : new SparseArray<String>();
    }

    @NonNull
    public SparseArray<Drawable> getDrawableMap() {
        return drawableMap != null ? drawableMap : new SparseArray<Drawable>();
    }

    public ButtonType getButtonType() {
        return buttonType;
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
