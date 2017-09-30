package org.hitogo.button.core;

import android.os.Bundle;

import org.hitogo.button.action.HitogoDefaultActionListener;
import org.hitogo.core.HitogoParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButtonParams extends HitogoParams<HitogoButtonParamsHolder> {

    private HitogoButtonListener listener;
    private HitogoButtonType type;

    protected void provideData(HitogoButtonParamsHolder holder, Bundle privateBundle) {
        this.type = (HitogoButtonType) privateBundle.getSerializable("type");
        this.listener = holder.getListener();

        if(this.listener == null) {
            this.listener = new HitogoDefaultActionListener();
        }

        onCreateParams(holder);
    }

    protected abstract void onCreateParams(HitogoButtonParamsHolder holder);

    public final HitogoButtonListener getListener() {
        return listener;
    }

    public final HitogoButtonType getType() {
        return type;
    }
}
