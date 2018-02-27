package org.hitogo.button.view;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.core.HitogoController;

/**
 * Public api interface for the ViewButtonBuilderImpl. This interface includes all methods that
 * can be used by this builder.
 *
 * @see org.hitogo.button.core.Button
 * @since 1.0.0
 */
public interface ViewButtonBuilder extends ButtonBuilder<ViewButtonBuilder, ViewButton> {

    /**
     * Adds a text element to this button which can be used inside the alert implementation.
     *
     * @param text   Text element for the button object.
     * @param viewId an Integer which is used for the view id for this text element.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    ViewButtonBuilder addText(@IdRes @Nullable Integer viewId, @Nullable String text);

    /**
     * Adds a text element to this button which can be used inside the alert implementation. The
     * string resource will be translated by the builder using the
     * HitogoAccessor.getString(context, int).
     *
     * @param textRes Text element for the alert object.
     * @param viewId  an Integer which is used for the view id for this text element.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    ViewButtonBuilder addText(@IdRes @Nullable Integer viewId, @StringRes int textRes);

    /**
     * Specifies the view for this button. The view can have two parts: icon and click. The icon is
     * the button itself which holds the text and any other <b>visible</b> elements. The click is
     * the area which is used for the ButtonListener object. This area can be larger than the button
     * to simplify the actual click for the user. This method is only using the icon view id. The
     * default click id (if the click id is null) is defined by the button params object.
     * <br><br>
     * <b>Keep in mind:</b> The CloseButton and the default ViewButton are using different
     * default click view id values (defined by their params objects)!
     *
     * @param iconId an Int value
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    ViewButtonBuilder setView(@IdRes int iconId);

    /**
     * Specifies the view for this button. The view can have two parts: icon and click. The icon is
     * the button itself which holds the text and any other <b>visible</b> elements. The click is
     * the area which is used for the ButtonListener object. This area can be larger than the button
     * to simplify the actual click for the user. This method is using the icon view id and the
     * optional parameter for the click view id. The default click id (if the click id is null) is
     * defined by the button params object.
     * <br><br>
     * <b>Keep in mind:</b> The CloseButton and the
     * default ViewButton are using different default click view id values (defined by their params
     * objects)!
     *
     * @param iconId  an Int value
     * @param clickId an Integer or null
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    ViewButtonBuilder setView(@IdRes int iconId, @IdRes @Nullable Integer clickId);

    /**
     * Adds a drawable to the button which can be used inside the alert implementation. This
     * method implementation will use the provideDefaultButtonDrawableViewId(Integer) method
     * offered by the HitogoController. This method will define the default view id for this
     * drawable. Buttons can have more than one drawable. The drawable resource will be translated
     * by the builder using the HitogoAccessor.getDrawable(int). If more than one drawable is
     * defined, the method addDrawable(Integer, int) should rather be used to include a view
     * id that can differ between the drawables.
     *
     * @param drawableRes an int which represents the drawable
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewButtonBuilder addDrawable(@DrawableRes int drawableRes);

    /**
     * Adds a drawable to the button which can be used inside the alert implementation. Buttons can
     * have more than one drawable. The drawable resource will be translated by the builder using
     * the HitogoAccessor.getDrawable(int).
     *
     * @param drawableRes an int which represents the drawable
     * @param viewId      an Integer or null
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewButtonBuilder addDrawable(@IdRes @Nullable Integer viewId, @DrawableRes int drawableRes);

    /**
     * Adds a drawable to the button which can be used inside the alert implementation. This
     * method implementation will use the provideDefaultButtonDrawableViewId(Integer) method
     * offered by the HitogoController.  This method will define the default view id for this
     * drawable. Buttons can have more than one drawable. If more than one drawable is defined, the
     * method addDrawable(Integer, Drawable) should rather be used to include a view id that can
     * differ between the drawables.
     *
     * @param drawable a Drawable
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewButtonBuilder addDrawable(@NonNull Drawable drawable);

    /**
     * Adds a drawable to the button which can be used inside the alert implementation. Buttons can
     * have more than one drawable.
     *
     * @param drawable a Drawable
     * @param viewId   an Integer or null
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewButtonBuilder addDrawable(@IdRes @Nullable Integer viewId, @Nullable Drawable drawable);
}
