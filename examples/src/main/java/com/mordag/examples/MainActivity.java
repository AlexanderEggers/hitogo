package com.mordag.examples;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mordag.hitogo.Hitogo;
import com.mordag.hitogo.HitogoActivity;
import com.mordag.hitogo.HitogoController;

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
