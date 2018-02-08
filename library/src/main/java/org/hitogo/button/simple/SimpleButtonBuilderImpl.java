package org.hitogo.button.simple;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilderImpl;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoContainer;

public class SimpleButtonBuilderImpl extends ButtonBuilderImpl<SimpleButtonBuilder, SimpleButton> implements SimpleButtonBuilder {

    public SimpleButtonBuilderImpl(@NonNull Class<? extends ButtonImpl> targetClass,
                                   @NonNull Class<? extends ButtonParams> paramClass,
                                   @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container);
    }
}
