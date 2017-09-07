package org.hitogo.error;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoErrorBuilder {

    private HitogoError error;

    public HitogoErrorBuilder() {
        this.error = new HitogoError();
    }

    public HitogoErrorBuilder withMessage(@NonNull String message) {
        error.message = message;
        return this;
    }

    public HitogoErrorBuilder asLog(Class sourceClass) {
        return asLog(sourceClass, HitogoError.DEBUG);
    }

    public HitogoErrorBuilder asLog(Class sourceClass, int logState) {
        error.sourceClass = sourceClass;
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

    @NonNull
    public HitogoErrorBuilder setBundle(@NonNull Bundle bundle) {
        error.bundle = bundle;
        return this;
    }

    public HitogoError build() {
        if (error.message == null) {
            throw new IllegalStateException("You need to declare a message for this error.");
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
