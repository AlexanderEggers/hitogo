package org.hitogo.button.view;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.ButtonBuilderImpl;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonType;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewButtonBuilderImpl extends ButtonBuilderImpl<ViewButtonBuilder, ViewButton> implements ViewButtonBuilder {

    private final int[] viewIds = new int[2];

    public ViewButtonBuilderImpl(@NonNull Class<? extends ButtonImpl> targetClass,
                                 @NonNull Class<? extends ButtonParams> paramClass,
                                 @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, ButtonType.VIEW);
    }

    @Override
    @NonNull
    public ViewButtonBuilder setView(@IdRes int iconId) {
        return setView(iconId, null);
    }

    @Override
    @NonNull
    public ViewButtonBuilder setView(@IdRes int iconId, @IdRes @Nullable Integer clickId) {
        this.viewIds[0] = iconId;
        this.viewIds[1] = clickId != null ? clickId : -1;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        super.onProvideData(holder);
        holder.provideSerializable(ViewButtonParamsKeys.VIEW_IDS_KEY, viewIds);
    }
}
