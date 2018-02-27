package org.hitogo.button.view;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.ButtonBuilderImpl;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonType;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Builder which includes all basic method to assign specific values to the text button.
 *
 * @see org.hitogo.button.core.Button
 * @since 1.0.0
 */
public class ViewButtonBuilderImpl extends ButtonBuilderImpl<ViewButtonBuilder, ViewButton> implements ViewButtonBuilder {

    private Integer iconId;
    private Integer clickId;

    /**
     * Default constructor for the ViewButtonBuilderImpl.
     *
     * @param targetClass Class object for the requested button.
     * @param paramClass  Class object for the params object which is used by the button.
     * @param container   Container which is used as a reference for this button (context, view,
     *                    controller).
     * @see HitogoContainer
     * @see HitogoController
     * @see ButtonType
     * @since 1.0.0
     */
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
        this.iconId = iconId;
        this.clickId = clickId != null ? clickId : -1;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        super.onProvideData(holder);
        holder.provideInteger(ViewButtonParamsKeys.ICON_ID_KEY, iconId);
        holder.provideInteger(ViewButtonParamsKeys.CLICK_ID_KEY, clickId);
    }
}
