package com.mordag.crouton;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.InvalidParameterException;

public final class CroutonButtonBuilder {

    private CroutonButton button;
    private View containerView;

    private CroutonButtonBuilder(CroutonContainer container) {
        button = new CroutonButton();
        this.containerView = container.getView();
    }

    private CroutonButtonBuilder(View containerView) {
        button = new CroutonButton();
        this.containerView = containerView;
    }

    @NonNull
    public static CroutonButtonBuilder with(@NonNull CroutonContainer container) {
        return new CroutonButtonBuilder(container);
    }

    @NonNull
    public static CroutonButtonBuilder with(@NonNull View containerView) {
        return new CroutonButtonBuilder(containerView);
    }

    @NonNull
    public CroutonButtonBuilder setName(@Nullable String name) {
        button.text = name;
        return this;
    }

    @NonNull
    public CroutonButtonBuilder asClickToCallButton(int viewId) {
        button.isCloseButton = false;
        button.viewIds = new Integer[1];
        button.viewIds[0] = viewId;
        return this;
    }

    @NonNull
    public CroutonButtonBuilder asCloseButton(int closeIconId) {
        button.isCloseButton = true;
        button.viewIds = new Integer[2];
        button.viewIds[0] = closeIconId;
        button.viewIds[1] = closeIconId;
        return this;
    }

    @NonNull
    public CroutonButtonBuilder asCloseButton(int closeIconId, @Nullable Integer optionalCloseViewId) {
        button.viewIds = new Integer[2];
        button.viewIds[0] = closeIconId;
        button.viewIds[1] = optionalCloseViewId != null ? optionalCloseViewId : closeIconId;
        return this;
    }

    @NonNull
    public CroutonButtonBuilder listen(@Nullable CroutonButtonListener listener) {
        button.listener = listener;
        return this;
    }

    @NonNull
    public CroutonButton build() {
        if (button.text == null || StringUtils.isEmpty(button.text)) {
            Log.w(CroutonButtonBuilder.class.getName(), "Button has no text. If you want to" +
                    "display a simple close button, you can ignore this warning.");
        }

        if (button.viewIds == null || button.viewIds.length == 0) {
            throw new InvalidParameterException("Have you forgot to add the view id for this button?");
        } else {
            for (int id : button.viewIds) {
                View v = checkButtonView(id);

                if (button.viewIds.length == 1 && v instanceof Button) {
                    throw new InvalidParameterException("The view of your button needs to " +
                            "extend the Button class...");
                }
            }
        }

        if (button.isCloseButton && button.viewIds.length < 2) {
            throw new InvalidParameterException("To display a close button, you should use the " +
                    "asCloseButton method.");
        }

        if (button.listener == null) {
            button.listener = new DefaultCroutonButtonListener();
            Log.d(CroutonButtonBuilder.class.getName(), "Using default button listener...");
        }

        return button;
    }

    @NonNull
    private View checkButtonView(int viewId) {
        if (containerView != null) {
            View view = containerView.findViewById(viewId);
            if (view == null) {
                throw new InvalidParameterException("Have you forgot to add the button to your layout?");
            }
            return view;
        } else {
            throw new InvalidParameterException("Root view must not be null...");
        }
    }
}
