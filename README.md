Hitogo
=====

[![Download](https://api.bintray.com/packages/mordag/android/Hitogo/images/download.svg) ](https://bintray.com/mordag/android/Hitogo/_latestVersion)

Hitogo is a fluent-api for Android which helps to simplfy hints, errors and dialogs inside your app! This api can be initialsed by only a few lines of codes. Hitogo objects are using their own lifecycle to display animations or executing certain actions.

Download
--------
You can use Gradle to download this libray:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'org.hitogo:Hitogo:1.0.0-alpha3'
}
```

How do I use Hitogo?
-------------------

1. Extend the HitogoController

The HitogoController is the base for the hint system. It decides if the new request hint should be shown and which hints should be closed. It also holds several getter-methods which points to your default configuration for hint layouts. Of course you need to define those getter-methods by implementing those. Those getter are handy if your hint layouts are quite similar to each other, but only differ for example in the usage of color. For example: getDefaultTitleViewId() holds the view id for the default title textview used in layouts. Each controller needs to implement the getLayout() method. This method defines which layout should be used for the requested hint. The method is using different states which needs to be defined by the user.

```java
public class HitogoExampleController extends HitogoController {

    public static final int HINT = 0;
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int DANGER = 3;

    public HitogoExampleController(LifecycleRegistry lifecycle) {
        super(lifecycle);
    }

    @Override
    public int getLayout(int state) {
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
    public Integer getDefaultTextViewId() {
        return R.id.text;
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
    public HitogoController initialiseHitogo(@NonNull LifecycleRegistry lifecycle) {
        return new HitogoExampleController(lifecycle);
    }
}
```

3. Start using Hitogo!

If you have finished step 1 and 2, you are ready to go! Using Hitogo you can create hint views, dialogs and even errors (for the console, analytics or something else). Each builder system will be covered in full length inside the wiki (Coming soon!).

```java
// To create simple hint that displays a short message, you could do this :
protected void someMethod() {
    ...
    Hitogo.asView(MainActivity.this)
            .setText("Test")
            .asIgnoreLayout()
            .withState(HitogoExampleController.HINT)
            .show(MainActivity.this);
}

//Here is a more complex hint that has some buttons, a title and a message:
public void someMethod() {
  ...
  HitagoButton button1 = HitagoButton.with(MainActivity.this)
                .setName("Button Text")
                .listen(...)
                .asClickToCallButton(R.id.button)
                .build();
                
  HitagoButton button2 = HitagoButton.with(MainActivity.this)
                .setName("Button Text 2")
                .listen(...)
                .asClickToCallButton(R.id.button)
                .build();
  
  Hitogo.asView(MainActivity.this)
        .asLayoutChild(R.id.containerId)
        .setTitle("Test Hint")
        .setText("Test Text")
        .asDismissible()
        .withAnimations()
        .addActionButton(button1, button2)
        .setState(HitogoExampleController.Hint)
        .show(MainActivity.this);
}

//Here an example to create a dialog:
public void someMethod() {
  ...
  HitagoButton dialogButton = HitagoButton.with(MainActivity.this)
                .setName("Button Text")
                .listen(...)
                .asDialogButton()
                .build();
  
  Hitogo.asDialog(MainActivity.this)
        .setTitle("Test Hint")
        .setText("Test Text")
        .asDismissible()
        .addActionButton(dialogButton)
        .show(MainActivity.this);
}
```

But wait, there is much more! Some features are already included but not documented yet:
- Custom hints/dialogs
- Animations
- Usage of error handling (Hitogo.asError(...))
- Dialogs
- Hitogo Lifecycle
- Android Lifecycle
- Bundle usage
- Buttons
- Parameter classes
- and much more!

Status
------
Version 1 is currently in development. Updates are currently released at least monthly with new features and bug fixes.

Comments/bugs/questions/pull requests are always welcome!

Compatibility
-------------

 * **Android SDK**: Hitogo requires a minimum API level of 14.

Author
------
Alexander Eggers - @mordag on GitHub

License
-------
Apache 2.0. See the [LICENSE][1] file for details.


[1]: https://github.com/Mordag/hitogo/blob/master/LICENSE
