package org.hitogo.core;

/**
 * This class is used to provide all needed alert-related values which has been initialised via
 * the builder system. In comparision: The counterpart, HitogoParamsHolder, can save and
 * provide data. The communication between those two params objects is the provideData-method.
 *
 * @param <T> Class which has been used to save the builder values.
 * @param <P> Used parent class to implement the provideData method to simplify it's access inside
 *            sub-classes.
 * @see HitogoParamsHolder
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoParams<T extends HitogoParamsHolder, P extends HitogoParams> {

    /**
     * This is an internal method which is used to fill the params object with different values.
     * This method is using the HitogoParamsHolder to generate those values.
     *
     * @param holder an object which is extending HitogoParamsHolder
     * @see HitogoParamsHolder
     * @since 1.0.0
     */
    protected abstract void provideData(T holder);

    /**
     * This method should be called by the provideData to provide a public method which sub-classes
     * can use to inject their data via the HitogoParamsHolder.
     *
     * @param holder      an object which is extending HitogoParamsHolder
     * @param alertParams an object which is extending HitogoParams
     * @see HitogoParamsHolder
     * @since 1.0.0
     */
    protected abstract void onCreateParams(T holder, P alertParams);
}
