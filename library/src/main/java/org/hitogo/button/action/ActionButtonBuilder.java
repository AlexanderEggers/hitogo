package org.hitogo.button.action;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonListener;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonParamsHolder;
import org.hitogo.button.core.ButtonType;
import org.hitogo.core.HitogoContainer;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActionButtonBuilder extends ButtonBuilder<ActionButton> {

    private static final ButtonType type = ButtonType.ACTION;

    private String text;

    private int[] viewIds;

    private boolean hasActionView;
    private boolean closeAfterClick;

    private ButtonListener listener;

    public ActionButtonBuilder(@NonNull Class<? extends ButtonImpl> targetClass,
                               @NonNull Class<? extends ButtonParams> paramClass,
                               @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, type);
    }

    @NonNull
    public ActionButtonBuilder setText(String text) {
        this.text = text;
        return this;
    }

    @NonNull
    public ActionButtonBuilder forViewAction() {
        return forViewAction(getController().provideDefaultCloseIconId(),
                getController().provideDefaultCloseClickId());
    }

    @NonNull
    public ActionButtonBuilder forViewAction(Integer closeIconId) {
        return forViewAction(closeIconId, getController().provideDefaultCloseClickId());
    }

    @NonNull
    public ActionButtonBuilder forViewAction(Integer closeIconId, @Nullable Integer optionalCloseViewId) {
        this.hasActionView = true;
        this.viewIds = new int[2];
        this.viewIds[0] = closeIconId;
        this.viewIds[1] = optionalCloseViewId != null ? optionalCloseViewId : closeIconId;
        return this;
    }

    @NonNull
    public ActionButtonBuilder forClickOnlyAction() {
        this.hasActionView = false;
        this.viewIds = new int[0];
        return this;
    }

    @NonNull
    public ActionButtonBuilder listenWith(@Nullable ButtonListener listener) {
        this.listener = listener;
        this.closeAfterClick = true;
        return this;
    }

    @NonNull
    public ActionButtonBuilder listenWith(@Nullable ButtonListener listener, boolean closeAfterClick) {
        this.listener = listener;
        this.closeAfterClick = closeAfterClick;
        return this;
    }

    @Override
    protected void onProvideData(ButtonParamsHolder holder) {
        holder.provideString(ActionButtonParamsKeys.TEXT_KEY, text);
        holder.provideIntArray(ActionButtonParamsKeys.VIEW_IDS_KEY, viewIds);
        holder.provideBoolean(ActionButtonParamsKeys.HAS_ACTION_VIEW_KEY, hasActionView);
        holder.provideBoolean(ActionButtonParamsKeys.CLOSE_AFTER_CLICK_KEY, closeAfterClick);
        holder.provideButtonListener(listener);
    }
}
