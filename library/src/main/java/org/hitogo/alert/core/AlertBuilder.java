package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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
     * Creates the requested alert using the provided values. This method is using java reflection
     * to determine the class for the alert and it's alert params object.
     *
     * @return Requested alert object.
     * @see Alert
     * @see AlertParams
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

    private void onProvidePrivateData(AlertParamsHolder holder) {
        privateBundle.putString(AlertParamsKeys.TITLE_KEY, title);
        privateBundle.putSerializable(AlertParamsKeys.TITLE_VIEW_ID_KEY, titleViewId);
        privateBundle.putString(AlertParamsKeys.TAG_KEY, tag);
        privateBundle.putBundle(AlertParamsKeys.ARGUMENTS_KEY, arguments);
        privateBundle.putSerializable(AlertParamsKeys.TYPE_KEY, builderType);
        privateBundle.putSerializable(AlertParamsKeys.STATE_KEY, state);
        privateBundle.putSerializable(AlertParamsKeys.LAYOUT_RES_KEY, layoutRes);

        holder.provideVisibilityListener(visibilityListener);
        holder.provideTextMap(textMap);
        holder.provideButtons(buttons);
        holder.provideCloseButton(closeButton);
    }

    protected abstract void onProvideData(AlertParamsHolder holder);

    @NonNull
    public B setController(HitogoController controller) {
        this.controller = controller;
        return (B) this;
    }

    @NonNull
    public B setBundle(@NonNull Bundle arguments) {
        this.arguments = arguments;
        return (B) this;
    }

    @NonNull
    public B setTitle(@NonNull String title) {
        return setTitle(getController().provideDefaultTitleViewId(builderType), title);
    }

    @NonNull
    public B setTitle(@StringRes int titleRes) {
        return setTitle(getController().provideDefaultTitleViewId(builderType),
                HitogoUtils.getStringRes(getContainer().getActivity(), titleRes));
    }

    @NonNull
    public B setTitle(Integer viewId, @StringRes int titleRes) {
        return setTitle(viewId, HitogoUtils.getStringRes(getContainer().getActivity(), titleRes));
    }

    @NonNull
    public B setTitle(Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return (B) this;
    }

    @NonNull
    public B addText(@NonNull String text) {
        return addText(getController().provideDefaultTextViewId(builderType), text);
    }

    @NonNull
    public B addText(@StringRes int textRes) {
        return addText(getController().provideDefaultTextViewId(builderType),
                HitogoUtils.getStringRes(getContainer().getActivity(), textRes));
    }

    @NonNull
    public B addText(Integer viewId, @StringRes int textRes) {
        return addText(viewId, HitogoUtils.getStringRes(getContainer().getActivity(), textRes));
    }

    @NonNull
    public B addText(Integer viewId, @NonNull String text) {
        textMap.put(viewId, text);
        return (B) this;
    }

    @NonNull
    public B setTag(@NonNull String tag) {
        this.tag = tag;
        return (B) this;
    }

    @NonNull
    public B setState(Integer state) {
        this.state = state;
        return (B) this;
    }

    @NonNull
    public B setState(Enum state) {
        this.state = state != null ? state.ordinal() : null;
        return (B) this;
    }

    @NonNull
    public B addVisibilityListener(@NonNull VisibilityListener<A> listener) {
        this.visibilityListener.add(listener);
        return (B) this;
    }

    @NonNull
    public B addButton(@NonNull Button... buttons) {
        Collections.addAll(this.buttons, buttons);
        return (B) this;
    }

    @NonNull
    protected B setCloseButton(@NonNull Button closeButton) {
        this.closeButton = closeButton;
        return (B) this;
    }

    @NonNull
    public B setLayout(@LayoutRes Integer layoutRes) {
        this.layoutRes = layoutRes;
        return (B) this;
    }

    public void show() {
        build().show();
    }

    protected HitogoContainer getContainer() {
        return containerRef.get();
    }

    protected HitogoController getController() {
        return controller;
    }
}
