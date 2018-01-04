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

@SuppressWarnings("WeakerAccess")
public class AlertParamsHolder extends HitogoParamsHolder {

    private HitogoAnimation animation;
    private List<Button> callToActionButtons;
    private Button closeButton;
    private List<VisibilityListener> listener;
    private SparseArray<String> textMap;
    private List<Transition> transitions = new ArrayList<>();
    private View.OnTouchListener onTouchListener;

    public final void provideAnimation(HitogoAnimation animation) {
        this.animation = animation;
    }

    public void provideOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    final void provideVisibilityListener(List<VisibilityListener> listener) {
        this.listener = listener;
    }

    public final void provideTransition(Transition transition) {
        this.transitions.add(transition);
    }

    public final void provideButtons(List<Button> buttonList) {
        this.callToActionButtons = buttonList;
    }

    public void provideTextMap(SparseArray<String> textMap) {
        this.textMap = textMap;
    }

    public final void provideCloseButton(Button button) {
        this.closeButton = button;
    }

    SparseArray<String> getTextMap() {
        return textMap;
    }

    HitogoAnimation getAnimation() {
        return animation;
    }

    List<Button> getButtons() {
        return callToActionButtons;
    }

    Button getCloseButton() {
        return closeButton;
    }

    List<VisibilityListener> getVisibilityListener() {
        return listener;
    }

    View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    @NonNull
    List<Transition> getTransitions() {
        return transitions;
    }
}
