package org.hitogo.examples;

import android.arch.lifecycle.LifecycleRegistry;

import org.hitogo.HitogoController;

public class HitogoDefaultController extends HitogoController {

    public static final int HINT = 0;
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int DANGER = 3;

    public HitogoDefaultController(LifecycleRegistry lifecycle) {
        super(lifecycle);
    }

    @Override
    public int getLayout(int state) {
        switch (state) {
            case SUCCESS:
                return R.layout.hitogo_success;
            case WARNING:
                return R.layout.hitogo_warning;
            case DANGER:
                return R.layout.hitogo_danger;
            case HINT:
            default:
                return R.layout.hitogo_hint;
        }
    }
}