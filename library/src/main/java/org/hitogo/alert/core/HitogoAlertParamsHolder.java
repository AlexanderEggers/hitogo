package org.hitogo.alert.core;

import android.transition.Transition;
import android.util.SparseArray;
import android.view.View;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.HitogoParamsHolder;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class HitogoAlertParamsHolder extends HitogoParamsHolder {

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> callToActionButtons;
    private HitogoButton closeButton;
    private HitogoVisibilityListener listener;
    private SparseArray<String> textMap;
    private List<Transition> transitions = new ArrayList<>();
    private List<Object> customObjects = new ArrayList<>();
    private View.OnTouchListener onTouchListener;

    public final void provideAnimation(HitogoAnimation animation) {
        this.hitogoAnimation = animation;
    }

    public void provideOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    final void provideVisibilityListener(HitogoVisibilityListener listener) {
        this.listener = listener;
    }

    public final void provideTransition(Transition transition) {
        this.transitions.add(transition);
    }

    public final void provideCustomObject(Object object) {
        this.customObjects.add(object);
    }

    public final void provideButtons(List<HitogoButton> buttonList) {
        this.callToActionButtons = buttonList;
    }

    public void provideTextMap(SparseArray<String> textMap) {
        this.textMap = textMap;
    }

    public final void provideCloseButton(HitogoButton button) {
        this.closeButton = button;
    }

    SparseArray<String> getTextMap() {
        return textMap;
    }

    HitogoAnimation getAnimation() {
        return hitogoAnimation;
    }

    List<HitogoButton> getButtons() {
        return callToActionButtons;
    }

    HitogoButton getCloseButton() {
        return closeButton;
    }

    HitogoVisibilityListener getVisibilityListener() {
        return listener;
    }

    View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    List<Transition> getTransitions() {
        return transitions;
    }

    List<Object> getCustomObjects() {
        return customObjects;
    }
}
