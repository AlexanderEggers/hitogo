package org.hitogo.button;

import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.view.HitogoViewBuilder;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoBuilder {

    private Class<? extends HitogoButtonObject> targetClass;
    private Class<? extends HitogoParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;

    private HitogoParamsHolder holder = new HitogoParamsHolder();

    public HitogoBuilder(@NonNull Class<? extends HitogoButtonObject> targetClass,
                         @NonNull Class<? extends HitogoParams> paramClass,
                         @NonNull HitogoContainer container) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public final HitogoButtonObject build() {
        onProvideData(holder);

        try {
            HitogoButtonObject object = targetClass.getConstructor().newInstance();
            HitogoParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder);
            object.buildButton(params);
            return object;
        } catch (Exception e) {
            Log.wtf(HitogoViewBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    protected abstract void onProvideData(HitogoParamsHolder holder);

    protected final HitogoController getController() {
        return containerRef.get().getController();
    }
}
