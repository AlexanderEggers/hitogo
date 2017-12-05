package org.hitogo.examples;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import org.hitogo.alert.core.HitogoAlertType;
import org.hitogo.button.core.HitogoButtonListener;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoActivity;
import org.hitogo.core.HitogoController;
import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoVisibilityListener;
import org.hitogo.alert.view.anim.HitogoLeftAnimation;
import org.hitogo.alert.view.anim.HitogoTopAnimation;

public class MainActivity extends HitogoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFirstView();
    }

    private void showFirstView() {
        HitogoButton button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        testOnClick();
                    }
                }, false)
                .forViewAction(R.id.button)
                .setText("Click me!")
                .build();

        HitogoButton closeButton = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        getController().closeAll(true);
                    }
                }, false)
                .forViewAction(R.id.close)
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Title")
                .asLayoutChild(R.id.container_layout)
                .addButton(button)
                .consumeLayoutClick()
                .addVisibilityListener(new HitogoVisibilityListener() {
                    @Override
                    public void onCreate(HitogoAlert object) {
                        Log.i(MainActivity.class.getName(), "Creating Hitogo");
                    }

                    @Override
                    public void onShow(HitogoAlert object) {
                        Log.i(MainActivity.class.getName(), "Showing Hitogo");
                    }

                    @Override
                    public void onClose(HitogoAlert object) {
                        Log.i(MainActivity.class.getName(), "Closing Hitogo");
                    }
                })
                .setState(AlertState.HINT)
                .setTag("TestHint 1")
                .showDelayed(1000);
    }

    private void testOnClick() {
        HitogoButton button = Hitogo.with(this)
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
                .addText("Long message...")
                .addButton(button)
                .addButton("Cancel")
                .asDismissible()
                .setTag("Test Dialog")
                .show();
    }

    private void dialogTest2() {
        HitogoButton button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        showPopup();
                    }
                }, false)
                .forClickOnlyAction()
                .setText("Ok")
                .build();

        Hitogo.with(this)
                .asDialog()
                .setTitle(R.id.title, "Test Dialog")
                .addText(R.id.text, "Long message...")
                .setState(AlertState.DANGER)
                .addButton(button)
                .asDismissible()
                .setTag("Test Dialog 2")
                .show();
    }

    private void showSecondView() {
        HitogoButton button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        showTestView();
                    }
                }, false)
                .forViewAction(R.id.button)
                .setText("Click me!")
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(HitogoLeftAnimation.build(), R.id.content)
                .addText("Test 2")
                .allowOthers()
                .asLayoutChild(R.id.container_layout)
                .addButton(button)
                .setState(AlertState.WARNING)
                .setTag("TestHint 2")
                .show();
    }

    private void showTestView() {
        HitogoButton next = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        getController().showNext(false, HitogoAlertType.VIEW);
                    }
                }, true)
                .forViewAction(R.id.button)
                .setText("Next")
                .build();

        HitogoButton save = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        showThirdView();
                    }
                }, false)
                .forViewAction(R.id.close)
                .setText("Load next internally")
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(HitogoLeftAnimation.build(), R.id.content)
                .addText("Test for 'Show Later'")
                .asLayoutChild(R.id.container_layout)
                .addButton(next)
                .closeOthers()
                .addButton(save)
                .setState(AlertState.HINT)
                .setTag("TestHint 2")
                .show();
    }

    private void showThirdView() {
        HitogoButton button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        dialogTest2();
                    }
                }, false)
                .forViewAction(R.id.button)
                .setText("Click me!")
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(HitogoTopAnimation.build(), R.id.content)
                .addText("Test 3")
                .asLayoutChild(R.id.container_layout)
                .addButton(button)
                .setState(AlertState.WARNING)
                .setTag("TestHint 3")
                .showLater(true);
    }

    private void showPopup() {
        Hitogo.with(this)
                .asPopup()
                .addText("Test Popup >> Nice button here!")
                .setAnchor(R.id.button_test)
                .setState(AlertState.HINT)
                .asDismissible()
                .show();
    }

    @NonNull
    @Override
    public HitogoController initialiseHitogo(@NonNull Lifecycle lifecycle) {
        return new AlertController(lifecycle);
    }
}
