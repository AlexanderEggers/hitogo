package org.hitogo.button;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.core.HitogoContainer;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoButtonBuilder extends HitogoBuilder {

    private String text;
    private int[] viewIds;
    private boolean hasButtonView;
    private boolean closingAfterExecute;

    private HitogoButtonListener listener;

    public HitogoButtonBuilder(@NonNull Class<? extends HitogoButtonObject> targetClass,
                        @NonNull Class<? extends HitogoParams> paramClass,
                        @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container);
    }

    @NonNull
    public HitogoButtonBuilder setText(String text) {
        this.text = text;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder forClickToAction() {
        return forClickToAction(getController().provideDefaultCallToActionId());
    }

    @NonNull
    public HitogoButtonBuilder forClickToAction(Integer viewId) {
        hasButtonView = true;
        viewIds = new int[1];
        viewIds[0] = viewId;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder forViewAction() {
        return forViewAction(getController().provideDefaultCloseIconId(),
                getController().provideDefaultCloseClickId());
    }

    @NonNull
    public HitogoButtonBuilder forViewAction(Integer closeIconId) {
        return forViewAction(closeIconId, getController().provideDefaultCloseClickId());
    }

    @NonNull
    public HitogoButtonBuilder forViewAction(Integer closeIconId, @Nullable Integer optionalCloseViewId) {
        hasButtonView = true;
        viewIds = new int[2];
        viewIds[0] = closeIconId;
        viewIds[1] = optionalCloseViewId != null ? optionalCloseViewId : closeIconId;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder forClickOnlyAction() {
        hasButtonView = false;
        viewIds = new int[0];
        return this;
    }

    @NonNull
    public HitogoButtonBuilder listenWith(@Nullable HitogoButtonListener listener) {
        this.listener = listener;
        this.closingAfterExecute = true;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder listenWith(@Nullable HitogoButtonListener listener, boolean closingAfterExecute) {
        this.listener = listener;
        this.closingAfterExecute = closingAfterExecute;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        holder.provideString("text", text);
        holder.provideIntArray("viewIds", viewIds);
        holder.provideBoolean("hasButtonView", hasButtonView);
        holder.provideBoolean("closingAfterExecute", closingAfterExecute);
        holder.provideButtonListener(listener);
    }
}
