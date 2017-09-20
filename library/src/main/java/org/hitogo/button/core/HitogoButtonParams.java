package org.hitogo.button.core;

import android.os.Bundle;

import org.hitogo.button.action.HitogoDefaultActionListener;
import org.hitogo.core.HitogoParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButtonParams extends HitogoParams<HitogoButtonParamsHolder> {

    private HitogoButtonListener listener;

    protected void provideData(HitogoButtonParamsHolder holder, Bundle privateBundle) {
        listener = holder.getListener();

        if(listener == null) {
            listener = new HitogoDefaultActionListener();
        }

        onCreateParams(holder);
    }

    protected abstract void onCreateParams(HitogoButtonParamsHolder holder);

    public HitogoButtonListener getListener() {
        return listener;
    }
}
