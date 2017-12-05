package org.hitogo.alert.popup;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.PopupWindow;

import org.hitogo.alert.core.Alert;

public interface PopupAlert extends Alert<PopupAlertParams> {

    void show(final boolean force);
    void showLater(final boolean showLater);
    void showDelayed(final long millis);
    void showDelayed(final long millis, final boolean force);

    void close(final boolean force);

    @Nullable
    View getRootView();

    @Nullable
    PopupWindow getPopup();
}
