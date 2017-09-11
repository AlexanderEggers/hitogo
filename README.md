Hitogo
=====

[![Download](https://api.bintray.com/packages/mordag/android/Hitogo/images/download.svg) ](https://bintray.com/mordag/android/Hitogo/_latestVersion)

Hitogo is a fluent-api for Android which helps to simplfy alerts for your app! This api can be initialsed by only a few lines of codes. Hitogo objects are using their own lifecycle to simplify the usage.

Download
--------
You can use Gradle to download this libray:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'org.hitogo:Hitogo:1.0.0-alpha4'
}
```

How do I use Hitogo? (Step-by-step introduction for 1.0.0-alpha4)
-------------------

1. Extend the HitogoController

The HitogoController is the base for the alert system. It decides if the new requested HitogoObject should be shown and which should be closed. It also holds several provider-methods which points to your default configuration. The methods are useful if your layouts are quite similar to each other, but only differ for example in the usage of color. For example: getDefaultTitleViewId() holds the view id for the default title textview.

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
    public Integer provideLayout(int state) {
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
    public Integer provideDefaultTextViewId() {
        return R.id.text;
    }

    @Nullable
    @Override
    public HitogoAnimation provideDefaultAnimation() {
        return HitogoTopAnimation.build();
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

If you have finished step 1 and 2, you are ready to go! Using Hitogo you can create hint views and dialogs. Each builder system will be covered in full length inside the wiki (Coming soon!).

```java
// To create simple hint that displays a short message, you could do this :
protected void someMethod() {
    ...
    Hitogo.with(this)
                .asView()
                .withAnimations(R.id.content)
                .setText("Test")
                .asLayoutChild(R.id.container_layout)
                .withState(HitogoDefaultController.HINT)
                .showDelayed("TestHint", 1000);
}

//Here is a more complex hint that has two buttons, a title and a message:
public void someMethod() {
  ...
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

  HitogoButtonObject button2 = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        testOnClick2();
                    }
                }, false)
                .forClickToAction(R.id.button)
                .setText("Click Test")
                .build();

        Hitogo.with(this)
                .asView()
                .withAnimations(R.id.content)
                .setText("That's a test alert.")
                .asLayoutChild(R.id.container_layout)
                .addActionButton(button, button2)
                .withState(HitogoDefaultController.WARNING)
                .showDelayed("TestHint 2", 1000);
}

//Here an example to create a dialog:
public void someMethod() {
  ...
  HitogoButtonObject button = Hitogo.with(this)
                .asButton()
                .listenWith(new HitogoButtonListener() {
                    @Override
                    public void onClick() {
                        testClick();
                    }
                })
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
```

But wait, there is much more! Some features are already included but not documented yet:
- Generics (you can inject your own builder, alters and holder into this library)
- Animations
- Hitogo Lifecycle
- Bundle usage
- Buttons
- Parameter classes
- and much more!

Status
------
Version 1 is in development. Updates are currently released at least weekly with new features and bug fixes. **Please keep in mind that the api is not stable yet and will change!**

Comments/bugs/questions/pull requests are always welcome!

Compatibility
-------------

 * **Android SDK**: Hitogo requires a minimum API level of 14.
 
TODO
-------------
* More animations (Fade, ...)
* Hitogo layouts (for all possible types)
* Unit/Espresso testing
* More examples
* Full documentation

Author
------
Alexander Eggers - @mordag on GitHub

License
-------
Apache 2.0. See the [LICENSE][1] file for details.


[1]: https://github.com/Mordag/hitogo/blob/master/LICENSE
