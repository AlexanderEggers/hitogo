package org.hitogo.core;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FontRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;

/**
 * This class is used to provide app resources to the alert/button. This class has been designed to
 * allow proper unit testing.
 *
 * @since 1.0.0
 */
public class HitogoAccessor {

    /**
     * Generates a Spanned object for the given text.
     *
     * @param text Text which needs to be translated to a Spanned object
     * @return a new Spanned object
     * @since 1.0.0
     */
    @SuppressWarnings("deprecation")
    public Spanned getHtmlText(@Nullable String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return text != null ? Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY) : new SpannableString("");
        } else {
            return text != null ? Html.fromHtml(text) : new SpannableString("");
        }
    }

    /**
     * Returns a String based for the given resource and it's arguments.
     *
     * @param context   Context object which will be used to retrieve the String
     * @param stringRes an Int value which will be used to retrieve the String
     * @param arguments Objects that can be attached to the retrieved String
     * @return a new String
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public String getString(@NonNull Context context, @StringRes int stringRes, Object... arguments) {
        return context.getString(stringRes, arguments);
    }

    /**
     * Returns a String based for the given resource, the amount and it's arguments.
     *
     * @param context   Context object which will be used to retrieve the String
     * @param pluralRes an Int value which will be used to retrieve the String
     * @param amount    an Int value which will determine the correct plural String
     * @param arguments Objects that can be attached to the retrieved String
     * @return a new String
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public String getPlural(@NonNull Context context, @PluralsRes int pluralRes, int amount, Object... arguments) {
        return context.getResources().getQuantityString(pluralRes, amount, arguments);
    }

    /**
     * Returns an Integer based for the given resource.
     *
     * @param context    Context object which will be used to retrieve the Integer
     * @param integerRes Resource which will be used to retrieve the Integer
     * @return a new Integer
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public Integer getInteger(@NonNull Context context, @IntegerRes int integerRes) {
        return context.getResources().getInteger(integerRes);
    }

    /**
     * Returns an Boolean based for the given resource.
     *
     * @param context Context object which will be used to retrieve the Boolean
     * @param boolRes an Int value which will be used to retrieve the Boolean
     * @return a new Boolean
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public Boolean getBoolean(@NonNull Context context, @BoolRes int boolRes) {
        return context.getResources().getBoolean(boolRes);
    }

    /**
     * Returns an color int based for the given resource.
     *
     * @param context  Context object which will be used to retrieve the color int
     * @param colorRes an Int value which will be used to retrieve the color int
     * @return a new int
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public int getColor(@NonNull Context context, @ColorRes int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }

    /**
     * Returns a Typeface int based for the given resource.
     *
     * @param context Context object which will be used to retrieve the Typeface
     * @param fontRes an Int value which will be used to retrieve the Typeface
     * @return a new Typeface
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    @RequiresApi(26)
    public Typeface getFont(@NonNull Context context, @FontRes int fontRes) {
        return context.getResources().getFont(fontRes);
    }

    /**
     * Returns a Drawable int based for the given resource.
     *
     * @param context     Context object which will be used to retrieve the Drawable
     * @param drawableRes an Int value which will be used to retrieve the Drawable
     * @return a new Drawable
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public Drawable getDrawable(@NonNull Context context, @DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(context, drawableRes);
    }

    /**
     * Returns a dimension pixel size for the given resource.
     *
     * @param context Context object which will be used to retrieve the pixel size
     * @param res     an Int value which will be used to retrieve the pixel size
     * @return a new int
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public int getDimensionPixelSize(@NonNull Context context, int res) {
        return context.getResources().getDimensionPixelSize(res);
    }

    /**
     * Returns a dimension pixel offset for the given resource.
     *
     * @param context Context object which will be used to retrieve the pixel offset
     * @param res     an Int value which will be used to retrieve the pixel offset
     * @return a new int
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public int getDimensionPixelOffset(@NonNull Context context, int res) {
        return context.getResources().getDimensionPixelOffset(res);
    }

    /**
     * Returns an int array for the given resource.
     *
     * @param context Context object which will be used to retrieve the int array
     * @param res     an Int value which will be used to retrieve the int array
     * @return a new int array
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public int[] getIntArray(@NonNull Context context, int res) {
        return context.getResources().getIntArray(res);
    }

    /**
     * Returns a dimensional for the given resource.
     *
     * @param context Context object which will be used to retrieve the dimensional
     * @param res     an Int value which will be used to retrieve the dimensional
     * @return a new float
     * @throws android.content.res.Resources.NotFoundException if the given resource
     *                                                         does not exist.
     * @since 1.0.0
     */
    public float getDimension(@NonNull Context context, @DimenRes int res) {
        return context.getResources().getDimension(res);
    }
}
