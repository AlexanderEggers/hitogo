package org.hitogo.examples;

import android.app.Activity;
import android.support.annotation.NonNull;

import org.hitogo.core.HitogoObject;
import org.hitogo.view.HitogoViewParams;

public class TestView extends HitogoObject {

    public TestView(@NonNull HitogoViewParams params) {
        super(params);
        System.out.println(params.getText());
    }

    @Override
    protected void makeVisible(@NonNull Activity activity) {

    }

    @Override
    protected void makeInvisible() {

    }

    @Override
    protected boolean isGone() {
        return false;
    }

    @Override
    protected boolean isVisible() {
        return false;
    }
}
