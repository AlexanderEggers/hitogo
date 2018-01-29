package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.util.SparseArray;

import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base alert builder which includes all basic method to assign the most important/common values to
 * the alert. The builder includes also some alert visibility method to prevent breaking the fluent
 * api design.
 *
 * @param <B> Type for the build class which is using this class implementation.
 * @param <A> Type for the result alert which is usable to execute certain methods at the end.
 * @see Alert
 * @since 1.0
 */
@SuppressWarnings({"unchecked"})
public abstract class AlertBuilder<B extends AlertBuilder, A extends Alert> {

    private Class<? extends AlertImpl> targetClass;
    private Class<? extends AlertParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;
    private HitogoController controller;
    private final List<VisibilityListener> visibilityListener = new ArrayList<>();

    private AlertParamsHolder holder = new AlertParamsHolder();

    private SparseArray<String> textMap = new SparseArray<>();
    private List<Button> buttons = new ArrayList<>();
    private Button closeButton;
    private String title;
    private Integer titleViewId;

    private Bundle privateBundle = new Bundle();
    private Bundle arguments;
    private Integer state;
    private String tag;
    private AlertType builderType;
    private Integer layoutRes;
    private Integer priority;

    /**
     * Default constructor for the AlertBuilder.
     *
     * @param targetClass Class object for the requested alert.
     * @param paramClass  Class object for the params object which is used by the alert.
     * @param container   Container which is used as a reference for this alert (context, view,
     *                    controller).
     * @param builderType AlertType which is needed for the build and visibility process of this
     *                    alert.
     * @see HitogoContainer
     * @see HitogoController
     * @see AlertType
     * @since 1.0
     */
    public AlertBuilder(@NonNull Class<? extends AlertImpl> targetClass,
                        @NonNull Class<? extends AlertParams> paramClass,
                        @NonNull HitogoContainer container, @NonNull AlertType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.builderType = builderType;
    }

    /**
     * Creates the requested alert using the provided builder values. This method is using java
     * reflection to determine the class for the alert and it's alert params object.
     *
     * @return Requested alert object.
     * @see Alert
     * @see AlertParams
     * @since 1.0
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public A build() {
        onProvideData(holder);
        onProvidePrivateData(holder);

        try {
            AlertImpl object = targetClass.getConstructor().newInstance();
            AlertParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, privateBundle);
            return (A) object.create(getContainer(), params);
        } catch (Exception e) {
            Log.wtf(ViewAlertBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    /**
     * Provides certain values of this builder to a private bundle object and the
     * AlertParamsHolder. The private bundle and the AlertParamsHolder is used to initialised the
     * AlertParams object, which is the data foundation for the alert object itself.
     *
     * @param holder Temporary object holder for certain alert objects, like the VisibilityListener.
     * @see AlertParamsHolder
     * @see AlertParams
     * @see Bundle
     * @since 1.0
     */
    private void onProvidePrivateData(AlertParamsHolder holder) {
        privateBundle.putString(AlertParamsKeys.TITLE_KEY, title);
        privateBundle.putSerializable(AlertParamsKeys.TITLE_VIEW_ID_KEY, titleViewId);
        privateBundle.putString(AlertParamsKeys.TAG_KEY, tag);
        privateBundle.putBundle(AlertParamsKeys.ARGUMENTS_KEY, arguments);
        privateBundle.putSerializable(AlertParamsKeys.TYPE_KEY, builderType);
        privateBundle.putSerializable(AlertParamsKeys.STATE_KEY, state);
        privateBundle.putSerializable(AlertParamsKeys.LAYOUT_RES_KEY, layoutRes);
        privateBundle.putSerializable(AlertParamsKeys.PRIORITY_KEY, priority);

        holder.provideVisibilityListener(visibilityListener);
        holder.provideTextMap(textMap);
        holder.provideButtons(buttons);
        holder.provideCloseButton(closeButton);
    }

    /**
     * Provides builder values which are used by the implemented builder class. The given
     * AlertParamsHolder is used to initialised the data foundation for the alert object.
     *
     * @param holder Temporary object holder for certain alert values, like the title string.
     * @see AlertParamsHolder
     * @see AlertParams
     * @since 1.0
     */
    protected abstract void onProvideData(AlertParamsHolder holder);

    /**
     * Binds a different HitogoController object to this alert. This will result in using a
     * different root-view.
     *
     * @param controller New HitogoController object which should replace the current.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0
     */
    @NonNull
    public B setController(HitogoController controller) {
        this.controller = controller;
        return (B) this;
    }

    /**
     * Adds a bundle object to this alert which can be used inside the alert implementation. This
     * method makes only sense for custom alert implementation which are certain bundle objects.
     *
     * @param arguments Bundle object for the alert
     * @return Builder object which has called this method.
     * @see Bundle
     * @since 1.0
     */
    @NonNull
    public B setBundle(@NonNull Bundle arguments) {
        this.arguments = arguments;
        return (B) this;
    }

    /**
     * Adds a title to this alert which can be used inside the alert implementation. This method
     * implementation will use the provideDefaultTitleViewId(Integer) method offered by the
     * HitogoController. This method will define the default view id for this title.
     *
     * @param title Title for the alert.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0
     */
    @NonNull
    public B setTitle(@NonNull String title) {
        return setTitle(getController().provideDefaultTitleViewId(builderType), title);
    }

    /**
     * Adds a title resource to this alert which can be used inside the alert implementation. This
     * method implementation will use the provideDefaultTitleViewId(Integer) method offered by the
     * HitogoController. This method will define the default view id for this title. The string
     * resource will be translated by the builder using the HitogoUtils.getStringRes(int).
     *
     * @param titleRes String resource which is used for the title.
     * @return Builder object which has called this method.
     * @see HitogoUtils
     * @see HitogoController
     * @since 1.0
     */
    @NonNull
    public B setTitle(@StringRes int titleRes) {
        return setTitle(getController().provideDefaultTitleViewId(builderType),
                HitogoUtils.getStringRes(getContainer().getActivity(), titleRes));
    }

    /**
     * Adds a title resource and it's related view id to this alert which can be used inside the
     * alert implementation. The string resource will be translated by the builder using the
     * HitogoUtils.getStringRes(int).
     *
     * @param viewId   View id which is going to use the title (optional).
     * @param titleRes String resource which is used for the title.
     * @return Builder object which has called this method.
     * @see HitogoUtils
     * @since 1.0
     */
    @NonNull
    public B setTitle(@Nullable Integer viewId, @StringRes int titleRes) {
        return setTitle(viewId, HitogoUtils.getStringRes(getContainer().getActivity(), titleRes));
    }

    /**
     * Adds a title to this alert which can be used inside the alert implementation.
     *
     * @param viewId View id which is going to use the title (optional).
     * @param title  Title for the alert.
     * @return Builder object which has called this method.
     * @since 1.0
     */
    @NonNull
    public B setTitle(@Nullable Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return (B) this;
    }

    /**
     * Adds a text element to this alert which can be used inside the alert implementation. This
     * method implementation will use the provideDefaultTextViewId(Integer) method offered by the
     * HitogoController. This method will define the default view id for this text element. Alerts
     * can have more than one text element. If more than one text element is defined, the method
     * addText(Integer, String) should rather be used to include a view id that can differ between
     * the text elements.
     *
     * @param text Text element for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0
     */
    @NonNull
    public B addText(@NonNull String text) {
        return addText(getController().provideDefaultTextViewId(builderType), text);
    }

    /**
     * Adds a text string resource to this alert which can be used inside the alert implementation.
     * This method implementation will use the provideDefaultTextViewId(Integer) method offered by
     * the HitogoController. This method will define the default view id for this text element.
     * Alerts can have more than one text element. If more than one text element is defined, the
     * method addText(Integer, int) should rather be used to include a view id that can differ
     * between the text elements. The string resource will be translated by the builder using the
     * HitogoUtils.getStringRes(int).
     *
     * @param textRes Text element for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @see HitogoUtils
     * @since 1.0
     */
    @NonNull
    public B addText(@StringRes int textRes) {
        return addText(getController().provideDefaultTextViewId(builderType),
                HitogoUtils.getStringRes(getContainer().getActivity(), textRes));
    }

    /**
     * Adds a text string resource to this alert which can be used inside the alert implementation.
     * Alerts can have more than one text element. If more than one text element is defined, each
     * text element will need it's own view (id). The string resource will be translated by the
     * builder using the HitogoUtils.getStringRes(int).
     *
     * @param viewId  View id which is going to use the text element (optional).
     * @param textRes Text element for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoUtils
     * @since 1.0
     */
    @NonNull
    public B addText(Integer viewId, @StringRes int textRes) {
        return addText(viewId, HitogoUtils.getStringRes(getContainer().getActivity(), textRes));
    }

    /**
     * Adds a text element to this alert which can be used inside the alert implementation. Alerts
     * can have more than one text element. If more than one text element is defined, each text
     * element will need it's own view (id).
     *
     * @param viewId View id which is going to use the text element (optional).
     * @param text   Text element for the alert object.
     * @return Builder object which has called this method.
     * @since 1.0
     */
    @NonNull
    public B addText(Integer viewId, @NonNull String text) {
        textMap.put(viewId, text);
        return (B) this;
    }

    /**
     * Adds a tag to this alert, which makes it closable by using the closeByTag from the
     * HitogoController. This tag is one way to make the alert unique.
     *
     * @param tag Tag for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0
     */
    @NonNull
    public B setTag(@NonNull String tag) {
        this.tag = tag;
        return (B) this;
    }

    /**
     * Sets the state for this alert. The state can define different areas of the alert, like
     * closeByState or provide(...)Layout using the HitogoController. Usually this method should be
     * called if the alert can use more than one layout or visual state.
     *
     * @param state State for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0
     */
    @NonNull
    public B setState(Integer state) {
        this.state = state;
        return (B) this;
    }

    /**
     * Sets the state for this alert. The state can define different areas of the alert, like
     * closeByState or provide(...)Layout using the HitogoController. Usually this method should be
     * called if the alert can use more than one layout or visual state.
     *
     * @param state State for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0
     */
    @NonNull
    public B setState(Enum state) {
        this.state = state != null ? state.ordinal() : null;
        return (B) this;
    }

    /**
     * Adds a VisibilityListener to the alert. The VisibilityListener can be used to keep track
     * of the different alert states (onCreate, onShow, onClose). Each alerts has the ability to
     * accept more than one VisibilityListener.
     *
     * @param listener VisibilityListener for the alert.
     * @return Builder object which has called this method.
     * @see VisibilityListener
     * @since 1.0
     */
    @NonNull
    public B addVisibilityListener(@NonNull VisibilityListener<A> listener) {
        this.visibilityListener.add(listener);
        return (B) this;
    }

    /**
     * Adds a button to the alert. Buttons are a abstract container for all needed information
     * that one button could have (title, listener, view id, ...).
     *
     * @param buttons One or more button/s for the alert.
     * @return Builder object which has called this method.
     * @see Button
     * @since 1.0
     */
    @NonNull
    public B addButton(@NonNull Button... buttons) {
        Collections.addAll(this.buttons, buttons);
        return (B) this;
    }

    /**
     * Sets the close button for the alert. Buttons are a abstract container for all needed
     * information that one button could have (title, listener, view id, ...). The close button is
     * a special button which is used to define the visual closing area/icon for the alert.
     *
     * @param closeButton The close button for the alert.
     * @return Builder object which has called this method.
     * @see Button
     * @since 1.0
     */
    @NonNull
    protected B setCloseButton(@NonNull Button closeButton) {
        this.closeButton = closeButton;
        return (B) this;
    }

    /**
     * Sets a custom layout resource id for the alert. Usually this method should only be when
     * using an unique layout for this certain alert. Otherwise the more general method
     * provide(...)Layout offered by the HitogoController should be used to define the common cases
     * using alert states.
     *
     * @param layoutRes Layout res id for the alert.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0
     */
    @NonNull
    public B setLayout(@LayoutRes Integer layoutRes) {
        this.layoutRes = layoutRes;
        return (B) this;
    }

    /**
     * Sets the priority for the alert. The lower the value, the higher the priority. This value is
     * used to determine the "importance" of a certain alert. Alerts with a lower priority will
     * always be closed if a new higher priority is about to get visible.<br>
     * <br>
     * <b>IMPORTANT: If no priority is set, the non-priority alert will <u>always</u> be shown even
     * if the current alert has one (unless it's suppressed in another way, like same same
     * content).</b>
     *
     * @param priority Priority for the alert.
     * @return Builder object which has called this method.
     * * @since 1.0
     */
    public B setPriority(int priority) {
        this.priority = priority;
        return (B) this;
    }

    /**
     * Builds and displays the alert object.
     */
    public void show() {
        build().show();
    }

    /**
     * Returns the used HitogoContainer object for the alert.
     *
     * @return HitogoContainer of the alert.
     * @see HitogoContainer
     * @since 1.0
     */
    protected HitogoContainer getContainer() {
        return containerRef.get();
    }

    /**
     * Returns the used HitogoController object for the alert.
     *
     * @return HitogoController of the alert.
     * @see HitogoController
     * @since 1.0
     */
    protected HitogoController getController() {
        return controller;
    }
}
