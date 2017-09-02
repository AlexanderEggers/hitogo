package org.hitogo.examples;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.hitogo.Hitogo;
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
    public HitogoController initialiseHitogo() {
        return new HitogoDefaultController(getLifecycle());
    }
}
