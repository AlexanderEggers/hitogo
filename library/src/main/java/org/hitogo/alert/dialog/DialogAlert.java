package org.hitogo.alert.dialog;

import android.app.Dialog;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.Alert;

@SuppressWarnings("unused")
public interface DialogAlert extends Alert<DialogAlertParams> {

    @Nullable
    Dialog getDialog();
}
