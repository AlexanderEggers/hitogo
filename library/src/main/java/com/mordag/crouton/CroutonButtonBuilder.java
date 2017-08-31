package com.mordag.crouton;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.InvalidParameterException;

public final class CroutonButtonBuilder {

    private CroutonButton button;
    private CroutonContainer container;

    private CroutonButtonBuilder(CroutonContainer container) {
        button = new CroutonButton();
        this.container = container;
    }

    public static CroutonButtonBuilder with(CroutonContainer container) {
        return new CroutonButtonBuilder(container);
    }

    public CroutonButtonBuilder setName(String name) {
        button.text = name;
        return this;
    }

    public CroutonButtonBuilder viewId(int viewId) {
        button.viewId = viewId;
        return this;
    }

    public CroutonButtonBuilder listen(CroutonButtonListener listener) {
        button.listener = listener;
        return this;
    }

    public CroutonButton build() {
        if(button.text == null) {
            throw new InvalidParameterException("Name parameter cannot be null.");
        }

        if(button.viewId == 0) {
            throw new InvalidParameterException("Did you forgot to set the view id of your button?");
        } else if(container.getRootView() != null) {
            View view = container.getRootView().findViewById(button.viewId);

            if(view == null) {
                throw new InvalidParameterException("Have you forgot to add the button to your layout?");
            } else if(view instanceof Button) {
                throw new InvalidParameterException("The view of your button needs to extend the Button class...");
            }
        }

        if(button.listener == null) {
            button.listener = button.defaultListener;
            Log.d(CroutonButtonBuilder.class.getName(), "Using default button listener...");
        }

        return button;
    }
}
