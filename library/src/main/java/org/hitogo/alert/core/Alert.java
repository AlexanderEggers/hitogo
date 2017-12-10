package org.hitogo.alert.core;

import android.content.Context;

@SuppressWarnings("unused")
public interface Alert<T extends AlertParams> {

    void show();

    void close();

    void close(final boolean force);

    boolean isDetached();

    boolean isAttached();

    boolean isClosing();

    int hashCode();

    int getState();

    boolean equals(final Object obj);

    AlertType getType();

    T getParams();

    String getTag();

    Context getContext();
}
