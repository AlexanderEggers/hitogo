package org.hitogo.button;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoParams {

    private HitogoButtonListener listener;

    void provideData(HitogoParamsHolder holder) {
        listener = holder.getListener();

        if(listener == null) {
            listener = new HitogoDefaultButtonListener();
        }

        onCreateParams(holder);
    }

    protected abstract void onCreateParams(HitogoParamsHolder holder);

    public HitogoButtonListener getListener() {
        return listener;
    }
}
