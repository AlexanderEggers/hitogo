package org.hitogo.examples;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.Nullable;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.core.HitogoController;
import org.hitogo.alert.core.HitogoAlertType;
import org.hitogo.alert.view.anim.HitogoTopAnimation;

public class HitogoDefaultController extends HitogoController {

    public HitogoDefaultController(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public Integer provideViewLayout(int state) {
        AlertState alertState = AlertState.parse(state);

        switch (alertState) {
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

    @Nullable
    @Override
    public Integer provideDialogLayout(int state) {
        AlertState alertState = AlertState.parse(state);

        switch (alertState) {
            default:
                return R.layout.hitogo_dialog_danger;
        }
    }

    @Nullable
    @Override
    public Integer provideDefaultTextViewId(HitogoAlertType type) {
        return R.id.text;
    }

    @Nullable
    @Override
    public HitogoAnimation provideDefaultAnimation() {
        return HitogoTopAnimation.build();
    }
}
