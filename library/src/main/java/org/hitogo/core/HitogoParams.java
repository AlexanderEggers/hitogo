package org.hitogo.core;

import android.content.Context;
import android.view.View;

import java.lang.ref.WeakReference;

public interface HitogoParams {

    WeakReference<Context> getContextRef();
    View getRootView();
    HitogoController getController();
    int getHashCode();
    int getType();
    boolean hasAnimation();
}
