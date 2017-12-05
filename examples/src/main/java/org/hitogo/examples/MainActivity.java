package org.hitogo.examples;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertType;
import org.hitogo.button.action.ActionButton;
import org.hitogo.button.core.ButtonListener;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoActivity;
import org.hitogo.core.HitogoController;
import org.hitogo.alert.core.VisibilityListener;
import org.hitogo.alert.view.anim.LeftAnimation;
import org.hitogo.alert.view.anim.TopAnimation;

public class MainActivity extends HitogoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFirstView();
    }

    private void showFirstView() {
        ActionButton button = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        testOnClick();
                    }
                }, false)
                .forViewAction(R.id.button)
                .setText("Click me!")
                .build();

        ActionButton closeButton = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        getController().closeAll(true);
                    }
                }, false)
                .forViewAction(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Title")
                .asLayoutChild(R.id.container_layout)
                .addButton(button)
                .consumeLayoutClick()
                .addVisibilityListener(new VisibilityListener() {
                    @Override
                    public void onCreate(Alert object) {
                        Log.i(MainActivity.class.getName(), "Creating Hitogo");
                    }

                    @Override
                    public void onShow(Alert object) {
                        Log.i(MainActivity.class.getName(), "Showing Hitogo");
                    }

                    @Override
                    public void onClose(Alert object) {
                        Log.i(MainActivity.class.getName(), "Closing Hitogo");
                    }
                })
                .setState(AlertState.HINT)
                .setTag("TestHint 1")
                .show();
    }

    private void testOnClick() {
        ActionButton button = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        showSecondView();
                    }
                }, false)
                .forClickOnlyAction()
                .setText("Ok")
                .build();

        Hitogo.with(this)
                .asDialogAlert()
                .setTitle("Test Dialog")
                .addText("Long message...")
                .addButton(button)
                .addButton("Cancel")
                .asDismissible()
                .setTag("Test Dialog")
                .show();
    }

    private void dialogTest2() {
        ActionButton button = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        showPopup();
                    }
                }, false)
                .forClickOnlyAction()
                .setText("Ok")
                .build();

        Hitogo.with(this)
                .asDialogAlert()
                .setTitle(R.id.title, "Test Dialog")
                .addText(R.id.text, "Long message...")
                .setState(AlertState.DANGER)
                .addButton(button)
                .asDismissible()
                .setTag("Test Dialog 2")
                .show();
    }

    private void showSecondView() {
        ActionButton button = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        showTestView();
                    }
                }, false)
                .forViewAction(R.id.button)
                .setText("Click me!")
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(LeftAnimation.build(), R.id.content)
                .addText("Test 2")
                .allowOthers()
                .asLayoutChild(R.id.container_layout)
                .addButton(button)
                .setState(AlertState.WARNING)
                .setTag("TestHint 2")
                .show();
    }

    private void showTestView() {
        ActionButton next = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        getController().showNext(false, AlertType.VIEW);
                    }
                }, true)
                .forViewAction(R.id.button)
                .setText("Next")
                .build();

        ActionButton save = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        showThirdView();
                    }
                }, false)
                .forViewAction(R.id.close)
                .setText("Load next internally")
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(LeftAnimation.build(), R.id.content)
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
        ActionButton button = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        dialogTest2();
                    }
                }, false)
                .forViewAction(R.id.button)
                .setText("Click me!")
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(TopAnimation.build(), R.id.content)
                .addText("Test 3")
                .asLayoutChild(R.id.container_layout)
                .addButton(button)
                .setState(AlertState.WARNING)
                .setTag("TestHint 3")
                .showLater(true);
    }

    private void showPopup() {
        Hitogo.with(this)
                .asPopupAlert()
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
