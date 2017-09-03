package org.hitogo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoButtonBuilder {

    private HitogoButton button;
    private View containerView;

    HitogoButtonBuilder(@NonNull HitogoContainer container) {
        button = new HitogoButton();
        this.containerView = container.getView();
    }

    HitogoButtonBuilder(@NonNull View containerView) {
        button = new HitogoButton();
        this.containerView = containerView;
    }

    @NonNull
    public HitogoButtonBuilder setName(@Nullable String name) {
        button.text = name;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asClickToCallButton(@XmlRes int viewId) {
        button.isCloseButton = false;
        button.viewIds = new Integer[1];
        button.viewIds[0] = viewId;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asCloseButton(@XmlRes int closeIconId) {
        button.isCloseButton = true;
        button.viewIds = new Integer[2];
        button.viewIds[0] = closeIconId;
        button.viewIds[1] = closeIconId;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asCloseButton(@Nullable @XmlRes Integer closeIconId,
                                             @Nullable @XmlRes Integer optionalCloseViewId) {
        if (closeIconId == null) {
            throw new IllegalStateException("Close icon view id must not be null");
        } else {
            button.viewIds = new Integer[2];
            button.viewIds[0] = closeIconId;
            button.viewIds[1] = optionalCloseViewId != null ? optionalCloseViewId : closeIconId;
        }
        return this;
    }

    @NonNull
    public HitogoButtonBuilder listen(@Nullable HitogoButtonListener listener) {
        button.listener = listener;
        return this;
    }

    @NonNull
    public HitogoButton build() {
        if (button.text == null || StringUtils.isEmpty(button.text)) {
            Log.w(HitogoButtonBuilder.class.getName(), "Button has no text. If you want to " +
                    "display a close button with only one icon, you can ignore this warning.");
        }

        if (button.viewIds == null || button.viewIds.length == 0) {
            throw new InvalidParameterException("Have you forgot to add the view id for this button?");
        } else {
            for (int id : button.viewIds) {
                View v = checkButtonView(id);

                if (button.viewIds.length == 1 && !(v instanceof TextView)) {
                    throw new InvalidParameterException("The view of your button needs to " +
                            "extend at least the TextView class.");
                }
            }
        }

        if (button.isCloseButton && button.viewIds.length < 2) {
            throw new InvalidParameterException("To display a close button, you should use the " +
                    "asCloseButton method.");
        }

        if (button.listener == null) {
            button.listener = new HitogoDefaultButtonListener();
            Log.d(HitogoButtonBuilder.class.getName(), "Using default button listener...");
        }

        return button;
    }

    @NonNull
    private View checkButtonView(@XmlRes int viewId) {
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
