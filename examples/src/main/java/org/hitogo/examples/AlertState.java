package org.hitogo.examples;

public enum AlertState {
    HINT, SUCCESS, WARNING, DANGER;

    public static AlertState parse(int state) {
        for (AlertState alertState : AlertState.values()) {
            if (alertState.ordinal() == state) {
                return alertState;
            }
        }
        return HINT;
    }
}
