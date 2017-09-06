package org.hitogo.error;

import android.support.annotation.NonNull;
import android.util.Log;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoError {

    public static final int WARNING = 0;
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int VERBOSE = 3;
    public static final int WTF = 4;

    Class sourceClass;
    String message;
    boolean isException;
    int logState = DEBUG;
    HitogoErrorListener listener;

    HitogoError() {
        //is doing nothing here - just hiding the constructor for other packages
    }

    public void show() {
        listener.onError();

        if (isException) {
            throw new HitogoException(message);
        } else {
            displayLogMessage();
        }
    }

    private void displayLogMessage() {
        switch (logState) {
            case WARNING:
                Log.w(sourceClass.getName(), message);
                break;
            case INFO:
                Log.i(sourceClass.getName(), message);
                break;
            case VERBOSE:
                Log.v(sourceClass.getName(), message);
                break;
            case WTF:
                Log.wtf(sourceClass.getName(), message);
                break;
            case DEBUG:
            default:
                Log.d(sourceClass.getName(), message);
                break;
        }
    }

    @NonNull
    public static HitogoErrorBuilder with() {
        return new HitogoErrorBuilder();
    }

    @NonNull
    public static HitogoErrorBuilder with(@NonNull Class sourceClass) {
        return new HitogoErrorBuilder(sourceClass);
    }
}
