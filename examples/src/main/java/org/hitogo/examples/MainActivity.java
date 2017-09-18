package org.hitogo.examples;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import org.hitogo.button.HitogoButtonListener;
import org.hitogo.button.HitogoButtonObject;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoActivity;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoVisibilityListener;
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
                .forViewAction(R.id.close)
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .setText("Test")
                .asLayoutChild(R.id.container_layout)
                .addActionButton(button)
                .consumeLayoutClick()
                .addVisibilityListener(new HitogoVisibilityListener() {
                    @Override
                    public void onCreate(HitogoObject object) {
                        Log.i(MainActivity.class.getName(), "Creating Hitogo");
                    }

                    @Override
                    public void onShow(HitogoObject object) {
                        Log.i(MainActivity.class.getName(), "Showing Hitogo");
                    }

                    @Override
                    public void onClose(HitogoObject object) {
                        Log.i(MainActivity.class.getName(), "Closing Hitogo");
                    }
                })
                .withState(AlertState.HINT)
                .setTag("TestHint 1")
                .showDelayed(1000);
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
                .forClickOnlyAction()
                .setText("Ok")
                .build();

        Hitogo.with(this)
                .asDialog()
                .setTitle("Test Dialog")
                .setText("Long message...")
                .addButton(button)
                .addButton("Cancel")
                .asDismissible()
                .setTag("Test Dialog")
                .show();
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
                .setTag("TestHint 2")
                .show();
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
                .setTag("TestHint 3")
                .show();
    }

    @NonNull
    @Override
    public HitogoController initialiseHitogo(@NonNull Lifecycle lifecycle) {
        return new HitogoDefaultController(lifecycle);
    }
}
