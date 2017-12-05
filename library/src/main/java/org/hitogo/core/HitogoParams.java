package org.hitogo.core;

import android.os.Bundle;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoParams<T extends HitogoParamsHolder, P extends HitogoParams> {

    protected abstract void provideData(T holder, Bundle privateBundle);
    protected abstract void onCreateParams(T holder, P alertParams);
}
