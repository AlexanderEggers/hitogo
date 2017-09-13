package org.hitogo.examples;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.annotation.NonNull;
import android.os.Bundle;

import org.hitogo.button.HitogoButtonListener;
import org.hitogo.button.HitogoButtonObject;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoActivity;
import org.hitogo.core.HitogoController;
import org.hitogo.view.HitogoLeftAnimation;
import org.hitogo.view.HitogoTopAnimation;

public class MainActivity extends HitogoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFirstView();
    }

    private void showFirstView() {
        HitogoButtonObject button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        testOnClick();
                    }
                }, false)
                .forClickToAction(R.id.button)
                .setText("Click me!")
                .build();

        HitogoButtonObject closeButton = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        getController().forceCloseAll();
                    }
                }, false)
                .forClose(R.id.close)
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .setText("Test")
                .asLayoutChild(R.id.container_layout)
                .addActionButton(button)
                .withState(AlertState.HINT)
                .showDelayed("TestHint 1", 1000);
    }

    private void testOnClick() {
        HitogoButtonObject button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        showSecondView();
                    }
                }, false)
                .forDialog()
                .setText("Ok")
                .build();

        Hitogo.with(this)
                .asDialog()
                .setTitle("Test Dialog")
                .setText("Long message...")
                .addButton(button)
                .addButton("Cancel")
                .asDismissible()
                .show("Test Dialog");
    }

    private void showSecondView() {
        HitogoButtonObject button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        showThirdView();
                    }
                }, false)
                .forClickToAction(R.id.button)
                .setText("Click me!")
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(HitogoLeftAnimation.build(), R.id.content)
                .setText("Test 2")
                .allowOthers()
                .asLayoutChild(R.id.container_layout)
                .addActionButton(button)
                .withState(AlertState.WARNING)
                .show("TestHint 2");
    }

    private void showThirdView() {
        HitogoButtonObject button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        testOnClick();
                    }
                }, false)
                .forClickToAction(R.id.button)
                .setText("Click me!")
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(HitogoTopAnimation.build(), R.id.content)
                .setText("Test 3")
                .asLayoutChild(R.id.container_layout)
                .addActionButton(button)
                .withState(AlertState.WARNING)
                .show("TestHint 3");
    }

    @NonNull
    @Override
    public HitogoController initialiseHitogo(@NonNull LifecycleRegistry lifecycle) {
        return new HitogoDefaultController(lifecycle);
    }
}
