package org.hitogo.alert.view;

import android.support.annotation.Nullable;
import android.view.View;

import org.hitogo.alert.core.Alert;

@SuppressWarnings("unused")
public interface ViewAlert extends Alert<ViewAlertParams> {

    void show(final boolean force);
    void showLater(final boolean showLater);
    void showDelayed(final long millis);
    void showDelayed(final long millis, final boolean force);

    void close(final boolean force);

    @Nullable
    View getRootView();

    @Nullable
    View getView();

    boolean hasAnimation();
    boolean isClosingOthers();
}
