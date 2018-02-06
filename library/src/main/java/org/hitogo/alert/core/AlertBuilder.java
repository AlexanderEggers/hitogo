package org.hitogo.alert.core;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoUtils;

public interface AlertBuilder<B extends AlertBuilderBase, A extends Alert> extends AlertBuilderBase<B, A> {

    /**
     * Adds a title to this alert which can be used inside the alert implementation. This method
     * implementation will use the provideDefaultTitleViewId(Integer) method offered by the
     * HitogoController. This method will define the default view id for this title.
     *
     * @param title Title for the alert.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    B setTitle(@NonNull String title);

    /**
     * Adds a title resource to this alert which can be used inside the alert implementation. This
     * method implementation will use the provideDefaultTitleViewId(Integer) method offered by the
     * HitogoController. This method will define the default view id for this title. The string
     * resource will be translated by the builder using the HitogoUtils.getStringRes(int).
     *
     * @param titleRes String resource which is used for the title.
     * @return Builder object which has called this method.
     * @see HitogoUtils
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    B setTitle(@StringRes int titleRes);

    /**
     * Adds a title resource and it's related view id to this alert which can be used inside the
     * alert implementation. The string resource will be translated by the builder using the
     * HitogoUtils.getStringRes(int).
     *
     * @param viewId   View id which is going to use the title (optional).
     * @param titleRes String resource which is used for the title.
     * @return Builder object which has called this method.
     * @see HitogoUtils
     * @since 1.0.0
     */
    @NonNull
    B setTitle(@IdRes @Nullable Integer viewId, @StringRes int titleRes);

    /**
     * Adds a title to this alert which can be used inside the alert implementation.
     *
     * @param viewId View id which is going to use the title (optional).
     * @param title  Title for the alert.
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    B setTitle(@IdRes @Nullable Integer viewId, @NonNull String title);

    /**
     * Adds a text element to this alert which can be used inside the alert implementation. This
     * method implementation will use the provideDefaultTextViewId(Integer) method offered by the
     * HitogoController. This method will define the default view id for this text element. Alerts
     * can have more than one text element. If more than one text element is defined, the method
     * addText(Integer, String) should rather be used to include a view id that can differ between
     * the text elements.
     *
     * @param text Text element for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    B addText(@NonNull String text);

    /**
     * Adds a text string resource to this alert which can be used inside the alert implementation.
     * This method implementation will use the provideDefaultTextViewId(Integer) method offered by
     * the HitogoController. This method will define the default view id for this text element.
     * Alerts can have more than one text element. If more than one text element is defined, the
     * method addText(Integer, int) should rather be used to include a view id that can differ
     * between the text elements. The string resource will be translated by the builder using the
     * HitogoUtils.getStringRes(int).
     *
     * @param textRes Text element for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @see HitogoUtils
     * @since 1.0.0
     */
    @NonNull
    B addText(@StringRes int textRes);

    /**
     * Adds a text string resource to this alert which can be used inside the alert implementation.
     * Alerts can have more than one text element. If more than one text element is defined, each
     * text element will need it's own view (id). The string resource will be translated by the
     * builder using the HitogoUtils.getStringRes(int).
     *
     * @param viewId  View id which is going to use the text element (optional).
     * @param textRes Text element for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoUtils
     * @since 1.0.0
     */
    @NonNull
    B addText(@IdRes @Nullable Integer viewId, @StringRes int textRes);

    /**
     * Adds a text element to this alert which can be used inside the alert implementation. Alerts
     * can have more than one text element. If more than one text element is defined, each text
     * element will need it's own view (id).
     *
     * @param viewId View id which is going to use the text element (optional).
     * @param text   Text element for the alert object.
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    B addText(@IdRes @Nullable Integer viewId, @NonNull String text);

    /**
     * Sets a custom layout resource id for the alert. Usually this method should only be when
     * using an unique layout for this certain alert. Otherwise the more general method
     * provide(...)Layout offered by the HitogoController should be used to define the common cases
     * using alert states.
     *
     * @param layoutRes Layout res id for the alert.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    B setLayout(@NonNull @LayoutRes int layoutRes);
}
