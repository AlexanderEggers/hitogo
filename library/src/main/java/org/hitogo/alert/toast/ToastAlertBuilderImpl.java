package org.hitogo.alert.toast;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.hitogo.alert.core.AlertBuilderImpl;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParamsHolder;

public class ToastAlertBuilderImpl extends AlertBuilderImpl<ToastAlertBuilder, ToastAlert> implements ToastAlertBuilder {

    private int duration = Toast.LENGTH_SHORT;

    private Integer gravity;
    private Integer xOffset;
    private Integer yOffset;

    private Float horizontalMargin;
    private Float verticalMargin;

    /**
     * Default constructor for the ToastAlertBuilderImpl.
     *
     * @param targetClass Class object for the requested alert.
     * @param paramClass  Class object for the params object which is used by the alert.
     * @param container   Container which is used as a reference for this alert (context, view,
     *                    controller).
     * @see HitogoContainer
     * @see HitogoController
     * @see AlertType
     * @since 1.0.0
     */
    public ToastAlertBuilderImpl(@NonNull Class<? extends AlertImpl> targetClass,
                                 @NonNull Class<? extends AlertParams> paramClass,
                                 @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, AlertType.OTHER);
    }

    @Override
    @NonNull
    public ToastAlertBuilder asDismissible(@Nullable Button closeButton) {
        if (closeButton != null) {
            return super.setCloseButton(closeButton);
        }
        return this;
    }

    @NonNull
    @Override
    public ToastAlertBuilder setGravity(int gravity, int xOffset, int yOffset) {
        this.gravity = gravity;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        return this;
    }

    @NonNull
    @Override
    public ToastAlertBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    @NonNull
    @Override
    public ToastAlertBuilder setMargins(float horizontalMargin, float verticalMargin) {
        this.horizontalMargin = horizontalMargin;
        this.verticalMargin = verticalMargin;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        super.onProvideData(holder);

        holder.provideInteger(ToastAlertParamsKeys.DURATION_KEY, duration);

        holder.provideInteger(ToastAlertParamsKeys.GRAVITY_KEY, gravity);
        holder.provideInteger(ToastAlertParamsKeys.X_OFFSET_KEY, xOffset);
        holder.provideInteger(ToastAlertParamsKeys.Y_OFFSET_KEY, yOffset);

        holder.provideFloat(ToastAlertParamsKeys.HORIZONTAL_MARGIN_KEY, horizontalMargin);
        holder.provideFloat(ToastAlertParamsKeys.VERTICAL_MARGIN_KEY, verticalMargin);
    }
}
