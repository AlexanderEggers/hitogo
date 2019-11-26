package org.hitogo.button.core;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.SparseArray;

import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

/**
 * This class is implemented to provide values to the different button types. Due to the abstract
 * implementation, each alert type will have it's own ButtonParams sub-class. This class provides
 * general values which all button types are sharing.
 *
 * @since 1.0.0
 */
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

    /**
     * Returns the icon id for the button.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public abstract int getIconId();

    /**
     * Returns the click id for the button.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public abstract Integer getClickId();

    /**
     * Returns if the button has a button view. This method is used internally for all buttons.
     *
     * @return True if the alert has a button view, false otherwise.
     * @since 1.0.0
     */
    public abstract boolean hasButtonView();

    /**
     * Returns the text map for the button.
     *
     * @return a SparseArray
     * @since 1.0.0
     */
    @NonNull
    public SparseArray<String> getTextMap() {
        return textMap != null ? textMap : new SparseArray<String>();
    }

    /**
     * Returns the drawable map for the button.
     *
     * @return a SparseArray
     * @since 1.0.0
     */
    @NonNull
    public SparseArray<Drawable> getDrawableMap() {
        return drawableMap != null ? drawableMap : new SparseArray<Drawable>();
    }

    /**
     * Returns the ButtonType for the button.
     *
     * @return a ButtonType object
     * @see ButtonType
     * @since 1.0.0
     */
    public ButtonType getButtonType() {
        return buttonType;
    }

    /**
     * Returns the button listener for the button.
     *
     * @return a ButtonListener object
     * @see ButtonListener
     * @see DefaultButtonListener
     * @since 1.0.0
     */
    @NonNull
    public ButtonListener getListener() {
        return listener != null ? listener : new DefaultButtonListener();
    }

    /**
     * Returns the button parameter for the button listener.
     *
     * @return an object
     * @see ButtonListener
     * @since 1.0.0
     */
    @Nullable
    public Object getButtonParameter() {
        return buttonParameter;
    }

    /**
     * Returns if the alert should be closed after clicking this button.
     *
     * @return True if the alert should be closed, false otherwise.
     * @since 1.0.0
     */
    public boolean isClosingAfterClick() {
        return closeAfterClick;
    }
}
