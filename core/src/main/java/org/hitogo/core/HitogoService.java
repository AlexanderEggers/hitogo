package org.hitogo.core;

import androidx.annotation.NonNull;

/**
 * A class which can be used to create a MVVM-based alert system. This service could be injected by
 * a dependency injection tool (like Dagger). This service could also be useful to create unit
 * tests which are using alerts.
 *
 * @param <T> Class which is extending Hitogo
 * @since 1.0.0
 */
public interface HitogoService<T extends Hitogo> {

    /**
     * Returns a new Hitogo factory which can be used to build alerts.
     *
     * @return a new Hitogo factory
     * @see Hitogo
     * @since 1.0.0
     */
    @NonNull
    T create();
}
