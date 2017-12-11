package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class AlertBuilder<B extends AlertBuilder, A extends Alert> {

    private Class<? extends AlertImpl> targetClass;
    private Class<? extends AlertParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;
    private HitogoController controller;
    private VisibilityListener visibilityListener;

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

    public AlertBuilder(@NonNull Class<? extends AlertImpl> targetClass,
                        @NonNull Class<? extends AlertParams> paramClass,
                        @NonNull HitogoContainer container, @NonNull AlertType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.builderType = builderType;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public final A build() {
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
    public final B setController(HitogoController controller) {
        this.controller = controller;
        return (B) this;
    }

    @NonNull
    public final B setBundle(@NonNull Bundle arguments) {
        this.arguments = arguments;
        return (B) this;
    }

    @NonNull
    public final B setTitle(@NonNull String title) {
        return setTitle(getController().provideDefaultTitleViewId(builderType), title);
    }

    @NonNull
    public final B setTitle(Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return (B) this;
    }

    @NonNull
    public final B addText(@NonNull String text) {
        return addText(getController().provideDefaultTextViewId(builderType), text);
    }

    @NonNull
    public final B addText(Integer viewId, @NonNull String text) {
        textMap.put(viewId, text);
        return (B) this;
    }

    @NonNull
    public final B setTag(@NonNull String tag) {
        this.tag = tag;
        return (B) this;
    }

    @NonNull
    public final B setState(Integer state) {
        this.state = state;
        return (B) this;
    }

    @NonNull
    public final B setState(Enum state) {
        this.state = state != null ? state.ordinal() : null;
        return (B) this;
    }

    @NonNull
    public final B setVisibilityListener(@NonNull VisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        return (B) this;
    }

    @NonNull
    public final B addButton(@NonNull Button... buttons) {
        Collections.addAll(this.buttons, buttons);
        return (B) this;
    }

    @NonNull
    protected final B setCloseButton(@NonNull Button closeButton) {
        this.closeButton = closeButton;
        return (B) this;
    }

    @NonNull
    public final B setLayout(@LayoutRes Integer layoutRes) {
        this.layoutRes = layoutRes;
        return (B) this;
    }

    public void show() {
        build().show();
    }

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    protected final HitogoController getController() {
        return controller;
    }
}
