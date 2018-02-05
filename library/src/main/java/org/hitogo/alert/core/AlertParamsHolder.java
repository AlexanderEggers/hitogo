package org.hitogo.alert.core;

import android.support.annotation.NonNull;
import android.transition.Transition;
import android.util.SparseArray;
import android.view.View;

import org.hitogo.core.HitogoAnimation;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoParamsHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is implemented to provide values to the AlertParams object via the builder system.
 *
 * @since 1.0.0
 * @see AlertParams
 * @see AlertBuilderImpl
 */
@SuppressWarnings("WeakerAccess")
public class AlertParamsHolder extends HitogoParamsHolder {

    private HitogoAnimation animation;
    private List<Button> callToActionButtons;
    private Button closeButton;
    private List<VisibilityListener> listener;
    private SparseArray<String> textMap;
    private List<Transition> transitions = new ArrayList<>();
    private View.OnTouchListener onTouchListener;

    public void provideAnimation(HitogoAnimation animation) {
        this.animation = animation;
    }

    public void provideOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public void provideVisibilityListener(List<VisibilityListener> listener) {
        this.listener = listener;
    }

    public void provideTransition(Transition transition) {
        this.transitions.add(transition);
    }

    public void provideButtons(List<Button> buttonList) {
        this.callToActionButtons = buttonList;
    }

    public void provideTextMap(SparseArray<String> textMap) {
        this.textMap = textMap;
    }

    public void provideCloseButton(Button button) {
        this.closeButton = button;
    }

    public SparseArray<String> getTextMap() {
        return textMap;
    }

    public HitogoAnimation getAnimation() {
        return animation;
    }

    public List<Button> getButtons() {
        return callToActionButtons;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public List<VisibilityListener> getVisibilityListener() {
        return listener;
    }

    public View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    @NonNull
    public List<Transition> getTransitions() {
        return transitions;
    }
}
