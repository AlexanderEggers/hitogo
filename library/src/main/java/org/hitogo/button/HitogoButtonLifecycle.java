package org.hitogo.button;

import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButtonLifecycle<T extends HitogoParams> {

    protected abstract void onCheck(@NonNull T params);

    protected void onCreate(@NonNull T params) {

    }
}
