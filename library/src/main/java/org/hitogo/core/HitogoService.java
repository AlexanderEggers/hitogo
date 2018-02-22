package org.hitogo.core;

public interface HitogoService<T extends Hitogo> {
    T create();
}
