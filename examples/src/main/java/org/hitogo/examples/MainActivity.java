package org.hitogo.examples;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.annotation.NonNull;
import android.os.Bundle;

import org.hitogo.HitogoActivity;
import org.hitogo.HitogoController;

public class MainActivity extends HitogoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NonNull
    @Override
    public HitogoController initialiseHitogo(@NonNull LifecycleRegistry lifecycle) {
        return new HitogoDefaultController(lifecycle);
    }
}
