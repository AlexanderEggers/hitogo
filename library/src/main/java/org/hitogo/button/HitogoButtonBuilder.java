package org.hitogo.button;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.core.HitogoContainer;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoButtonBuilder extends HitogoBuilder {

    private String text;
    private int[] viewIds;
    private boolean hasButtonView;

    private HitogoButtonListener listener;

    public HitogoButtonBuilder(@NonNull Class<? extends HitogoButtonObject> targetClass,
                        @NonNull Class<? extends HitogoParams> paramClass,
                        @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container);
    }

    @NonNull
    public HitogoButtonBuilder setName(String name) {
        text = name;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asClickToCallButton() {
        return asClickToCallButton(getController().provideDefaultCallToActionId());
    }

    @NonNull
    public HitogoButtonBuilder asClickToCallButton(Integer viewId) {
        hasButtonView = true;
        viewIds = new int[1];
        viewIds[0] = viewId;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asCloseButton() {
        return asCloseButton(getController().provideDefaultCloseIconId(),
                getController().provideDefaultCloseClickId());
    }

    @NonNull
    public HitogoButtonBuilder asCloseButton(Integer closeIconId) {
        return asCloseButton(closeIconId, getController().provideDefaultCloseClickId());
    }

    @NonNull
    public HitogoButtonBuilder asCloseButton(Integer closeIconId, @Nullable Integer optionalCloseViewId) {
        hasButtonView = true;
        viewIds = new int[2];
        viewIds[0] = closeIconId;
        viewIds[1] = optionalCloseViewId != null ? optionalCloseViewId : closeIconId;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asDialogButton() {
        hasButtonView = false;
        viewIds = new int[0];
        return this;
    }

    @NonNull
    public HitogoButtonBuilder listen(@Nullable HitogoButtonListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        holder.provideString("text", text);
        holder.provideIntArray("viewIds", viewIds);
        holder.provideBoolean("hasButtonView", hasButtonView);
        holder.provideButtonListener(listener);
    }
}
