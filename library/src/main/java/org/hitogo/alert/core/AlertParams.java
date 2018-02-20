package org.hitogo.alert.core;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

import java.util.Collections;
import java.util.List;

/**
 * This class is implemented to provide values to the different alert types. Due to the abstract
 * implementation, each alert type will have it's own AlertParams sub-class. This class provides
 * general values which all alert types are sharing.
 *
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AlertParams extends HitogoParams<HitogoParamsHolder> {

    private String title;
    private String tag;
    private SparseArray<String> textMap;
    private SparseArray<Drawable> drawableMap;

    private Integer layoutRes;
    private Integer titleViewId;
    private Integer state;
    private Integer priority;

    private List<Button> buttons;
    private Button closeButton;

    private AlertType type;
    private Bundle arguments;
    private List<VisibilityListener> visibilityListener;

    @Override
    protected void provideData(HitogoParamsHolder holder, HitogoController controller) {
        super.provideData(holder, controller);

        title = holder.getString(AlertParamsKeys.TITLE_KEY);
        tag = holder.getString(AlertParamsKeys.TAG_KEY);
        textMap = holder.getCustomObject(AlertParamsKeys.TEXT_KEY);
        drawableMap = holder.getCustomObject(AlertParamsKeys.DRAWABLE_KEY);

        layoutRes = holder.getSerializable(AlertParamsKeys.LAYOUT_RES_KEY);
        titleViewId = holder.getSerializable(AlertParamsKeys.TITLE_VIEW_ID_KEY);
        state = holder.getSerializable(AlertParamsKeys.STATE_KEY);
        priority = holder.getSerializable(AlertParamsKeys.PRIORITY_KEY);

        buttons = holder.getCustomObject(AlertParamsKeys.BUTTONS_KEY);
        closeButton = holder.getCustomObject(AlertParamsKeys.CLOSE_BUTTON_KEY);

        type = holder.getSerializable(AlertParamsKeys.TYPE_KEY);
        arguments = holder.getBundle(AlertParamsKeys.ARGUMENTS_KEY);

        visibilityListener = holder.getCustomObject(AlertParamsKeys.VISIBILITY_LISTENER_KEY);

        onCreateParams(holder);
    }

    /**
     * Returns if the alert has an animation. This method is used internally for all alerts. Due to
     * forcing overriding the correct method, this method is required to implement.
     *
     * @return True if the alert has animations, false otherwise.
     * @since 1.0.0
     */
    public abstract boolean hasAnimation();

    /**
     * Returns if the alert is closing alerts when it is in process to be displayed. This method is
     * used internally for all alerts. Due to forcing overriding the correct method, this method is
     * required to implement.
     *
     * @return True if the alert is closing other alerts when displayed, false otherwise.
     * @since 1.0.0
     */
    public abstract boolean isClosingOthers();

    /**
     * Returns the title for the alert.
     *
     * @return a String
     * @since 1.0.0
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the title view id for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getTitleViewId() {
        return titleViewId;
    }

    /**
     * Returns the AlertType for the alert.
     *
     * @return an AlertType object
     * @see AlertType
     * @since 1.0.0
     */
    @NonNull
    public AlertType getType() {
        return type;
    }

    /**
     * Returns the tag for the alert.
     *
     * @return a String
     * @since 1.0.0
     */
    @NonNull
    public String getTag() {
        return tag;
    }

    /**
     * Returns the state for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getState() {
        return state;
    }

    /**
     * Returns the layout resource for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getLayoutRes() {
        return layoutRes;
    }

    /**
     * Returns the buttons that are attached to this alert.
     *
     * @return List of buttons
     * @since 1.0.0
     */
    @NonNull
    public List<Button> getButtons() {
        return buttons != null ? buttons : Collections.<Button>emptyList();
    }

    /**
     * Returns the text elements that are attached to this alert. The key is the view id, value is
     * the text element. If the alert is not using any views for the text, the key is -1.
     *
     * @return SparseArray of String
     * @since 1.0.0
     */
    @NonNull
    public SparseArray<String> getTextMap() {
        return textMap != null ? textMap : new SparseArray<String>();
    }

    /**
     * Returns the drawables that are attached to this alert. The key is the view id, value is the
     * drawable. If the alert is not using any views for the drawable, the key is -1.
     *
     * @return SparseArray of Drawable
     * @since 1.0.0
     */
    @NonNull
    public SparseArray<Drawable> getDrawableMap() {
        return drawableMap != null ? drawableMap : new SparseArray<Drawable>();
    }

    /**
     * Returns the optional bundle object for the alert.
     *
     * @return a Bundle object or null
     * @since 1.0.0
     */
    public Bundle getArguments() {
        return arguments;
    }

    /**
     * Returns the close button for the alert.
     *
     * @return a Button object or null
     * @since 1.0.0
     */
    public Button getCloseButton() {
        return closeButton;
    }

    /**
     * Returns the VisibilityListener that are attached to this alert.
     *
     * @return List of VisibilityListener
     * @see VisibilityListener
     * @since 1.0.0
     */
    @NonNull
    public List<VisibilityListener> getVisibilityListener() {
        return visibilityListener != null ? visibilityListener : Collections.<VisibilityListener>emptyList();
    }

    /**
     * Returns the priority for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getPriority() {
        return priority;
    }
}
