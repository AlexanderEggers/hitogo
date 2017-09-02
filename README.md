Hitogo
=====

[![Download](https://api.bintray.com/packages/mordag/Hitogo/Hitogo/images/download.svg) ](https://bintray.com/mordag/Hitogo/Hitogo/_latestVersion)

Android Library to display hints inside your app. Those can be displayed as an overflow view, a dialog and inside a specific container view.

More coming soon!

Download
--------
You can use Gradle to download this libray:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'org.hitogo:Hitogo:1.0.0-alpha1'
}
```

How do I use Hitogo?
-------------------
Documentation and source code coming soon!

Simple use cases will look something like this:

```java
// To create simple hint that displays a short message, you could do this :
public void showHint() {
  Hitogo.with(this)
        .asSimple("Short message that explains the user something")
        .show(this);
}

//To use this library use need to create your own HitogoController by simple extending this class. You will need to fill the required methods then. Part of that is the declaration of the different layout types or some default view ids.

//I recommend you to use the HitogoActivity or HitogoFragment to simplify your usage of this API. This classes already can do everything that is needed to initialise all things. But you can also implement the HitogoContainer and create this base yourself.

//Here is a more complex hint that has some buttons, a title and a certain state for the layout (and views):
public void showHint() {
  ...
  HitagoButton button = HitagoButtonBuilder.with(this)
                .setName("Button Text")
                .listen(...)
                .asClickToCallButton(R.id.button)
                .build();
  
  Hitogo.with(this)
        .asLayoutChild(R.id.containerId)
        .setTitle("Test Hint")
        .setText("Test Text")
        .asDismissble()
        .withAnimations()
        .addButton(button)
        .show(this);
}

//Keep in mind that if you want to show Hitogo directly at the beginning, you need to delay this show()-call. You can simple use showDelayed(...) for that.
```

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
Apache 2.0. See the [LICENSE][2] file for details.

Disclaimer
---------
This is not an official Google product.

[1]: https://github.com/Mordag/hitogo/blob/master/LICENSE
