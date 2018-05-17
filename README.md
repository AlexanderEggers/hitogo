Hitogo
=====
[![Download](https://api.bintray.com/packages/mordag/android/hitogo-core/images/download.svg) ](https://bintray.com/mordag/android/hitogo-core/_latestVersion)

Hitogo is a fluent-api for Android which helps you to simplify alerts for your app! This api can be initialised by only a few lines of codes.

Motivation
--------
This api is not only usable to simplify the creation of alerts, but also helps you to keep control of all alerts, their trigger sources and the actual alerts which are displayed to the user. Trigger sources are for example server requests, actions triggered by the user or something similar.

Handling alerts can be especially difficult if the trigger sources are not just one but many different per activity/fragment/... This api helps you to prioritise, sort and filter these sources to show only the most relevant alerts to your user base. 

**Here one example:**

You have 20 different trigger sources in your acitivty. In this example these sources are server requests. Each source can trigger an alert to the user to inform him in the case that the request has failed. Makes sense, right? So, let's say 10 of these requests failed. Normally 10 alerts would be triggered now. That probably would look wired to the user or fill up the whole screen with error messages.

At this point Hitogo can help you to structure your alerts to only show one error message instead of 10 (in the case all 10 alerts have the same text).

**And another example:**

You have again 20 different trigger sources which are based on server requests and one addtional which will trigger when user clicks a certain button. All alerts will be displayed as Snackbars. Because we all know that only one Snackbar can be displayed at the time, it would make sense to focus on the user interaction alert instead of the error messages of these 20 sources, right? So when the user clicks the button, the current Snackbar message should always be dismissed to show the user alert. And it should also surpress attemps of the other trigger sources to dismiss this user alert.

To solve this problem, the library allows you to define a priority for your alert. This priority could be 1 for the user interaction and 2 for the error messages. That means that the user interaction alert will always be displayed.

Download
--------
You can use Gradle to download this libray:

```gradle
repositories {
  jcenter()
}

dependencies {
  implementation 'org.hitogo:hitogo-core:1.0.1'
  testImplementation 'org.hitogo:hitogo-testing:1.0.1' //mocks and testing tools
}
```

How do I use Hitogo? (Step-by-step introduction for 1.0.1)
-------------------

1. Extend the HitogoController

The HitogoController is the base for the alert system. It decides if the new requested alert should be shown and which should be closed. It also holds several provider-methods which are pointing to your default configuration. The methods are useful if your layouts are quite similar to each other, but only differ for example in the usage of color. For example: getDefaultTitleViewId() holds the view id for the default title textview. Because of defining this default title view id, you can use setTitle(String) instead of setTitle(String, Integer) which should simplify your code base.

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
    public Integer provideDefaultAlertTextViewId(AlertType type) {
        return R.id.text;
    }
    
    ...
}
```

2. Extend HitogoActivity/HitogoFragment or implement HitogoContainer

HitogoActivity and HitogoFragment are using the interface HitogoContainer. This interface is used to give you a certain structure in how to connect alerts to your app. It includes some getter-methods which will be used by the alert builder system.

```java
public class MainActivity extends HitogoActivity<AlertController> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ...
    }

    @NonNull
    @Override
    public AlertController initialiseController(@NonNull Lifecycle lifecycle) {
        return new AlertController(lifecycle);
    }
}
```

3. Start using Hitogo!

If you have finished step 1 and 2, you are ready to go! Using Hitogo you can create views, popups, dialogs, toasts and snackbars. Each builder system will be covered in full length inside the wiki (coming very soon!).

```java
// To create a simple alert that displays a short message, you could do that:
    protected void someMethod() {
        Hitogo.with(this)
                .asViewAlert()
                .withAnimations()
                .addText("Test")
                .asLayoutChild()
                .show();
    }

    //Here is a more complex alert, that has one button:
    public void someMethod() {
        ViewButton button = Hitogo.with(this)
                .asViewButton()
                .setButtonListener(new ButtonListener() {
                    @Override
                    public void onClick(Alert alert, Object parameter) {
                        testOnClick();
                    }
                }, false)
                .setView(R.id.button)
                .addText("Click me!")
                .build();

        Hitogo.with(this)
                .asViewAlert()
                .addText("Test")
                .asLayoutChild()
                .addButton(button)
                .show();
    }

    //Here an example to create a dialog:
    public void someMethod() {
        Hitogo.with(this)
                .asDialogAlert()
                .setTitle("Test Dialog")
                .addText("Long message...")
                .addButton("Ok")
                .show();
    }

    //Last example for creating a popup:
    public void someMethod() {
        Hitogo.with(this)
                .asPopupAlert()
                .addText("Test Popup")
                .setAnchor(R.id.button_test)
                .show();
    }
```

Status
------
Version 1.1.0 is currently under development in the master branch. The latest stable version is 1.0.1 which can be found in the 1.0.x branch.

Each new version version will be supported until the next version is released. For example: 1.0.1 is supported until 1.1.0 or 1.0.2 is out. Each minor release won't include any public API changes.

Comments/bugs/questions/pull requests are always welcome!

Compatibility
-------------

 * **Minimum Android SDK**: Hitogo requires a minimum API level of 16.

Author
------
Alexander Eggers - [@mordag][2] on GitHub

License
-------
Apache 2.0. See the [LICENSE][1] file for details.


[1]: https://github.com/Mordag/hitogo/blob/master/LICENSE
[2]: https://github.com/Mordag
