package org.hitogo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoErrorBuilder {

    private HitogoError error;

    HitogoErrorBuilder() {
        this.error = new HitogoError();
    }

    HitogoErrorBuilder(Class sourceClass) {
        this();
        this.error.sourceClass = sourceClass;
    }

    public HitogoErrorBuilder withMessage(@NonNull String message) {
        error.message = message;
        return this;
    }

    public HitogoErrorBuilder asLog() {
        return asLog(HitogoError.DEBUG);
    }

    public HitogoErrorBuilder asLog(int logState) {
        error.logState = logState;
        error.isException = false;
        return this;
    }

    public HitogoErrorBuilder asException() {
        error.isException = true;
        return this;
    }

    public HitogoErrorBuilder listen(@Nullable HitogoErrorListener listener) {
        error.listener = listener;
        return this;
    }

    public HitogoError build() {
        if (error.message == null) {
            throw new IllegalStateException("You need to declare a message for that error.");
        }

        if (error.listener == null) {
            error.listener = new HitogoDefaultErrorListener();
            Log.d(HitogoErrorBuilder.class.getName(), "Using default error listener.");
        }

        return error;
    }

    public void show() {
        build().show();
    }
}
