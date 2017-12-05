package org.hitogo.alert.core;

import android.content.Context;
import android.support.annotation.NonNull;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

@SuppressWarnings("unused")
public interface Alert<T extends AlertParams> {

    void show();

    void close();

    void close(final boolean force);

    boolean isDetached();

    boolean isAttached();

    boolean isClosing();

    int hashCode();

    boolean equals(final Object obj);

    @NonNull
    AlertType getType();

    @NonNull
    T getParams();

    @NonNull
    String getTag();

    @NonNull
    Context getContext();
}
