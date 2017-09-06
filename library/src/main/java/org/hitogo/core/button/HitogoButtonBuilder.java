package org.hitogo.core.button;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.util.Log;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.StringUtils;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoButtonBuilder {

    private HitogoButton button;
    private HitogoController controller;

    HitogoButtonBuilder(@NonNull HitogoContainer container) {
        button = new HitogoButton();
        this.controller = container.getController();
    }

    HitogoButtonBuilder(@NonNull HitogoController controller) {
        button = new HitogoButton();
        this.controller = controller;
    }

    @NonNull
    public HitogoButtonBuilder setName(@Nullable String name) {
        button.text = name;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asClickToCallButton() {
        return asClickToCallButton(controller.getDefaultCallToActionId());
    }

    @NonNull
    public HitogoButtonBuilder asClickToCallButton(@XmlRes Integer viewId) {
        button.hasButtonView = true;
        button.isCloseButton = false;
        button.viewIds = new Integer[1];
        button.viewIds[0] = viewId;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asCloseButton() {
        return asCloseButton(controller.getDefaultCloseIconId(), controller.getDefaultCloseClickId());
    }

    @NonNull
    public HitogoButtonBuilder asCloseButton(@XmlRes Integer closeIconId) {
        return asCloseButton(closeIconId, controller.getDefaultCloseClickId());
    }

    @NonNull
    public HitogoButtonBuilder asCloseButton(@XmlRes Integer closeIconId,
                                             @Nullable @XmlRes Integer optionalCloseViewId) {
        button.hasButtonView = true;
        button.isCloseButton = true;
        button.viewIds = new Integer[2];
        button.viewIds[0] = closeIconId;
        button.viewIds[1] = optionalCloseViewId != null ? optionalCloseViewId : closeIconId;
        return this;
    }

    @NonNull
    public HitogoButtonBuilder asDialogButton() {
        button.hasButtonView = false;
        button.viewIds = new Integer[0];
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
                    "display a button with only one icon, you can ignore this warning.");
        }

        if (button.hasButtonView && (button.viewIds == null || button.viewIds.length == 0)) {
            throw new InvalidParameterException("Have you forgot to add at least one view id for " +
                    "this button?");
        }

        if (button.hasButtonView) {
            for (Integer id : button.viewIds) {
                if (id == null) {
                    throw new InvalidParameterException("Button view id cannot be null.");
                }
            }
        }

        if (button.listener == null) {
            button.listener = new HitogoDefaultButtonListener();
            Log.d(HitogoButtonBuilder.class.getName(), "Using default button listener.");
        }

        return button;
    }
}
