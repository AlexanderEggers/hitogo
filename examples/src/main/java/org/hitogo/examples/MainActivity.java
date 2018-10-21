package org.hitogo.examples;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.VisibilityListener;
import org.hitogo.alert.view.ViewAlert;
import org.hitogo.button.core.ButtonListener;
import org.hitogo.button.text.TextButton;
import org.hitogo.button.view.ViewButton;
import org.hitogo.core.Hitogo;
import org.hitogo.support.HitogoActivity;
import org.hitogo.core.HitogoController;

public class MainActivity extends HitogoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sourceTest();
        //showFirstView();
        //showPrioAlerts();
        //showPrioAlerts2();
        //testImage();
        //testButtonImage();
        dialogTestThreeButtons(); // DialogAlerts support up to three buttons

        //testToast();
        testSnackbar();
    }

    public void sourceTest() {
        ViewButton closeButton = Hitogo.with(this)
                .asViewButton()
                .addText(R.string.test_id)
                .setView(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(1)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(1)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(1)
                .show();
    }

    private void showFirstView() {
        ViewButton button = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        testOnClick();
                    }
                })
                .setView(R.id.button)
                .addText("Click me!")
                .build();

        ViewButton closeButton = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        getController().closeAll();
                    }
                })
                .addText(R.string.test_id)
                .setView(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Title")
                .asLayoutChild(-1)
                .addButton(button)
                .dismissByLayoutClick(true)
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

    private void showPrioAlerts() {
        ViewButton closeButton = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener<String>() {
                    @Override
                    public void onClick(@NonNull Alert alert, String parameter) {
                        getController().showNext(alert);
                    }
                }, false, "Test parameter")
                .addText(R.string.test_id)
                .setView(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 4")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(4)
                .addVisibilityListener(new VisibilityListener<ViewAlert>() {
                    @Override
                    public void onShow(ViewAlert object) {
                        thirdPrioTest();
                    }
                })
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 2")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(2)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 3")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(3)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(1)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1 copy")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(1)
                .show();
    }

    private void showPrioAlerts2() {
        ViewButton closeButton = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener<String>() {
                    @Override
                    public void onClick(@NonNull Alert alert, String parameter) {
                        getController().showNext(alert);
                    }
                }, "Test parameter")
                .addText(R.string.test_id)
                .setView(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(1)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(2)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1 copy")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(2)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1 copy")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(2)
                .show();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 3")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(3)
                .show();
    }

    public void testImage() {
        ViewButton viewButton = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener<String>() {
                    @Override
                    public void onClick(@NonNull Alert alert, String parameter) {
                        testImageDialog();
                    }
                }, false)
                .addText("Dialog")
                .setView(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .addText("Test Image Alert")
                .asLayoutChild()
                .addButton(viewButton)
                .setState(AlertState.HINT)
                .addDrawable(R.id.imageTest, android.R.drawable.star_on)
                .show();
    }

    private void testButtonImage() {
        ViewButton viewButton = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener<String>() {
                    @Override
                    public void onClick(@NonNull Alert alert, String parameter) {
                        testImageDialog();
                    }
                }, false)
                .addText(R.id.button_text, "Dialog")
                .setView(R.id.button_test_image)
                .addDrawable(R.id.button_image, android.R.drawable.star_on)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .addText("Test Image Alert")
                .asLayoutChild()
                .addButton(viewButton)
                .setState(AlertState.SUCCESS)
                .show();
    }

    public void testImageDialog() {
        Hitogo.with(this)
                .asDialogAlert()
                .setTitle("Test")
                .addText("Test Image Alert")
                .addButton("Ok")
                .addDrawable(android.R.drawable.star_on)
                .show();
    }

    private void thirdPrioTest() {
        ViewButton closeButton = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener<String>() {
                    @Override
                    public void onClick(@NonNull Alert alert, String parameter) {
                        getController().showNext(alert);
                        System.out.println(parameter);
                    }
                }, false, "Test parameter")
                .addText(R.string.test_id)
                .setView(R.id.close)
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 4a")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(4)
                .showLater(true);

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 3a")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(3)
                .showLater(true);

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .asDismissible(closeButton)
                .addText("Test")
                .setTitle("Test Prio 1a")
                .asLayoutChild(R.id.container_layout)
                .setState(AlertState.HINT)
                .setPriority(1)
                .showLater(true);
    }

    private void testOnClick() {
        TextButton button = Hitogo.with(this)
                .asTextButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        showSecondView();
                    }
                })
                .addText("Ok")
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
        TextButton button = Hitogo.with(this)
                .asTextButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        showPopup();
                    }
                })
                .addText("Ok")
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

    private void dialogTestThreeButtons() {
        TextButton button1 = Hitogo.with(this)
                .asTextButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        showPopup();
                    }
                })
                .addText("Button 1")
                .build();

        TextButton button2 = Hitogo.with(this)
                .asTextButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        showPopup();
                    }
                })
                .addText("Button 2")
                .build();

        TextButton button3 = Hitogo.with(this)
                .asTextButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        showPopup();
                    }
                })
                .addText("Button 3")
                .build();

        Hitogo.with(this)
                .asDialogAlert()
                .setTitle(R.id.title, "Test Dialog")
                .addText(R.id.text, "Long message...")
                .setState(AlertState.DANGER)
                .addButton(button1)
                .addButton(button2)
                .addButton(button3)
                .asDismissible()
                .setTag("Test Dialog 2 Buttons")
                .show();
    }

    private void showSecondView() {
        ViewButton button = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        showTestView();
                    }
                })
                .setView(R.id.button)
                .addText("Click me!")
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .addText("Test 2")
                .asLayoutChild(R.id.container_layout)
                .addButton(button)
                .setState(AlertState.WARNING)
                .setTag("TestHint 2")
                .show();
    }

    private void showTestView() {
        ViewButton next = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        getController().showNext(alert);
                    }
                })
                .setView(R.id.button)
                .addText("Next")
                .build();

        ViewButton save = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        showThirdView();
                    }
                }, false)
                .setView(R.id.close)
                .addText("Load next internally")
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
                .addText("Test for 'Show Later'")
                .asLayoutChild(R.id.container_layout)
                .addButton(next)
                .addButton(save)
                .setState(AlertState.HINT)
                .setTag("TestHint 2")
                .show();
    }

    private void showThirdView() {
        ViewButton button = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(@NonNull Alert alert, Object parameter) {
                        dialogTest2();
                    }
                })
                .setView(R.id.button)
                .addText("Click me!")
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .withAnimations(R.id.content)
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

    public void testToast() {
        findViewById(R.id.button_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hitogo.with(MainActivity.this)
                        .asToastAlert()
                        .addText("Test Toast")
                        .setDuration(Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void testSnackbar() {
        findViewById(R.id.button_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hitogo.with(MainActivity.this)
                        .asSnackbarAlert()
                        .addText("Test Snackbar")
                        .setDuration(Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @NonNull
    @Override
    public HitogoController initialiseController(@NonNull Lifecycle lifecycle) {
        return new AlertController(lifecycle);
    }
}
