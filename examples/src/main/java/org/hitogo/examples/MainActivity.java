package org.hitogo.examples;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertType;
import org.hitogo.alert.core.VisibilityListener;
import org.hitogo.alert.view.ViewAlert;
import org.hitogo.button.action.ActionButton;
import org.hitogo.button.core.ButtonListener;
import org.hitogo.core.Hitogo;
import org.hitogo.core.HitogoActivity;
import org.hitogo.core.HitogoController;
import org.hitogo.alert.view.anim.LeftAnimation;
import org.hitogo.alert.view.anim.TopAnimation;

public class MainActivity extends HitogoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFirstView();
        //showPrioAlerts();
    }

    private void showPrioAlerts() {
        ActionButton closeButton = Hitogo.with(this)
                .asActionButton()
                .setButtonListener(new ButtonListener<String>() {
                    @Override
                    public void onClick(Alert alert, String parameter) {
                        getController().showNext(alert, false);
                    }
                }, false, "Test parameter")
                .setText(R.string.test_id)
                .forViewAction(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1")
                .asLayoutChild(R.id.container_layout)
                .dismissByLayoutClick()
                .setState(AlertState.HINT)
                .setPriority(1)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 2")
                .asLayoutChild(R.id.container_layout)
                .dismissByLayoutClick()
                .setState(AlertState.HINT)
                .setPriority(2)
                .addVisibilityListener(new VisibilityListener<ViewAlert>() {
                    @Override
                    public void onShow(ViewAlert object) {
                        thirdPrioTest();
                    }
                })
                .show();
    }

    private void thirdPrioTest() {
        ActionButton closeButton = Hitogo.with(this)
                .asActionButton()
                .setButtonListener(new ButtonListener<String>() {
                    @Override
                    public void onClick(Alert alert, String parameter) {
                        getController().showNext(alert, false);
                        System.out.println(parameter);
                    }
                }, false, "Test parameter")
                .setText(R.string.test_id)
                .forViewAction(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 3")
                .asLayoutChild(R.id.container_layout)
                .dismissByLayoutClick()
                .setState(AlertState.HINT)
                .setPriority(1)
                .showLater(true);

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 4")
                .asLayoutChild(R.id.container_layout)
                .dismissByLayoutClick()
                .setState(AlertState.HINT)
                .setPriority(2)
                .showLater(true);

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 5")
                .asLayoutChild(R.id.container_layout)
                .dismissByLayoutClick()
                .setState(AlertState.HINT)
                .setPriority(2)
                .showLater(true);
    }

    private void showFirstView() {
        ActionButton button = Hitogo.with(this)
                .asActionButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
                        testOnClick();
                    }
                }, false)
                .forViewAction(R.id.button)
                .setText("Click me!")
                .build();

        ActionButton closeButton = Hitogo.with(this)
                .asActionButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
                        getController().closeAll(true);
                    }
                }, false)
                .setText(R.string.test_id)
                .forViewAction(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Title")
                .asLayoutChild(R.id.fake_id)
                .addButton(button)
                .dismissByLayoutClick()
                .addVisibilityListener(new VisibilityListener<ViewAlert>() {

                    @Override
                    public void onShow(ViewAlert object) {
                        Log.i(MainActivity.class.getName(), "Showing Alert");
                    }
                })
                .setState(AlertState.HINT)
                .setTag("TestHint 1")
                .show();
    }

    private void testOnClick() {
        ActionButton button = Hitogo.with(this)
                .asActionButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
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
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
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
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
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
                .closeOthers(false)
                .asLayoutChild(R.id.container_layout)
                .addButton(button)
                .setState(AlertState.WARNING)
                .setTag("TestHint 2")
                .show();
    }

    private void showTestView() {
        ActionButton next = Hitogo.with(this)
                .asActionButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
                        getController().showNext(alert, false);
                    }
                }, true)
                .forViewAction(R.id.button)
                .setText("Next")
                .build();

        ActionButton save = Hitogo.with(this)
                .asActionButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
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
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
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
