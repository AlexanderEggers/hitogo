Hitogo
=====

[![Download](https://api.bintray.com/packages/mordag/android/Hitogo/images/download.svg) ](https://bintray.com/mordag/android/Hitogo/_latestVersion)

Hitogo is a fluent-api for Android which helps to simplify alerts for your app! This api can be initialsed by only a few lines of codes. Hitogo objects are using their own lifecycle to simplify the usage.

Download
--------
You can use Gradle to download this libray:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'org.hitogo:Hitogo:1.0.0-beta9.1'
}
```

How do I use Hitogo? (Step-by-step introduction for 1.0.0-beta9.1)
-------------------

1. Extend the HitogoController

The HitogoController is the base for the alert system. It decides if the new requested alert should be shown and which should be closed. It also holds several provider-methods which are pointing to your default configuration. The methods are useful if your layouts are quite similar to each other, but only differ for example in the usage of color. For example: getDefaultTitleViewId() holds the view id for the default title textview.

```java
public class AlertController extends HitogoController {

    public static final int HINT = 0;
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int DANGER = 3;

    public AlertController(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public Integer provideViewLayout(int state) {
        switch (state) {
            case SUCCESS:
                return R.layout.hitogo_success;
            case WARNING:
                return R.layout.hitogo_warning;
            case DANGER:
                return R.layout.hitogo_danger;
            case HINT:
            default:
                return R.layout.hitogo_hint;
        }
    }

    @Nullable
    @Override
    public Integer provideDefaultTextViewId(HitogoAlertType type) {
        return R.id.text;
    }

    @Nullable
    @Override
    public HitogoAnimation provideDefaultAnimation() {
        return TopAnimation.build();
    }
}
```

2. Extend HitogoActivity/HitogoFragment or implement HitogoContainer

HitogoActivity and HitogoFragment are using the interface HitogoContainer. This interface is used to give you a certain structure in how to connect Hitogo to your app. It includes some getter-methods which will be used by the builder-system.

```java
public class MainActivity extends HitogoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ...
    }

    @NonNull
    @Override
    public HitogoController initialiseHitogo(@NonNull Lifecycle lifecycle) {
        return new AlertController(lifecycle);
    }
}
```

3. Start using Hitogo!

If you have finished step 1 and 2, you are ready to go! Using Hitogo you can create views, popups and dialogs. Each builder system will be covered in full length inside the wiki (Coming soon!).

```java
// To create a simple alert that displays a short message, you could do that:
protected void someMethod() {
    ...
    Hitogo.with(this)
                .asViewAlert()
                .withAnimations()
                .addText("Test")
                .asLayoutChild(R.id.container_layout)
                .setState(HitogoDefaultController.HINT)
                .setTag("TestHint")
                .show();
}

//Here is a more complex alert, that has two buttons:
public void someMethod() {
  ...
       ActionButton button = Hitogo.with(this)
                .asActionButton()
                .listenWith(new ButtonListener() {
                    @Override
                    public void onClick() {
                        testOnClick();
                    }
                }, false)
                .forClickToAction(R.id.button)
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
                .asLayoutChild(R.id.container_layout)
                .addActionButton(button)
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
                .setState(HitogoDefaultController.HINT)
                .setTag("TestHint 1")
                .show();
}

//Here an example to create a dialog:
public void someMethod() {
  ...
       ActionButton button = Hitogo.with(this)
                .asActionButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        testClick();
                    }
                })
                .forClickOnlyAction()
                .setText("Ok")
                .build();

        Hitogo.with(this)
                .asDialogAlert()
                .setTitle("Test Dialog")
                .addText("Long message...")
                .setState(HitogoDefaultController.DANGER)
                .addButton(button)
                .addButton("Cancel")
                .asDismissible()
                .setTag("Test Dialog")
                .show();
                
//Last example for creating a popup:
public void someMethod() {
  ...
       Hitogo.with(this)
                .asPopupAlert()
                .addText("Test Popup")
                .setAnchor(R.id.button_test)
                .setState(HitogoDefaultController.HINT)
                .asDismissible()
                .show();
}
```

Status
------
Version 1 is still in development. Updates are currently released at least monthly with new features and bug fixes. **Please keep in mind that the api is not stable yet and might change!**

Comments/bugs/questions/pull requests are always welcome!

Compatibility
-------------

 * **Minimum Android SDK**: Hitogo requires a minimum API level of 14.
 * **Compile Android SDK**: Hitogo requires you to compile against API 27.
 
TODO
-------------
* Unit testing
* Full documentation (source code and wiki)

Author
------
Alexander Eggers - [@mordag][2] on GitHub

License
-------
Apache 2.0. See the [LICENSE][1] file for details.


[1]: https://github.com/Mordag/hitogo/blob/1.0/LICENSE
[2]: https://github.com/Mordag
