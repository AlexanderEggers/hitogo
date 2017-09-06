package org.hitogo.error;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoException extends IllegalStateException {

    public HitogoException(String message) {
        super(message);
    }
}
