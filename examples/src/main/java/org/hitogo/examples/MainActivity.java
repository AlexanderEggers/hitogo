package org.hitogo.examples;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.annotation.NonNull;
import android.os.Bundle;

import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoActivity;
import org.hitogo.core.HitogoController;

public class MainActivity extends HitogoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hitogo.asView(TestView.class, this)
                .setText("Test")
                .asIgnoreLayout()
                .withState(HitogoDefaultController.HINT)
                .show(this);
    }

    @NonNull
    @Override
    public HitogoController initialiseHitogo(@NonNull LifecycleRegistry lifecycle) {
        return new HitogoDefaultController(lifecycle);
    }
}
