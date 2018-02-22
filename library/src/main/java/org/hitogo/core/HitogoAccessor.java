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

public class HitogoAccessor {

    @SuppressWarnings("deprecation")
    public Spanned getHtmlText(@Nullable String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return text != null ? Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY) : new SpannableString("");
        } else {
            return text != null ? Html.fromHtml(text) : new SpannableString("");
        }
    }

    public String getString(@NonNull Context context, @StringRes int stringRes, Object...arguments) {
        return context.getString(stringRes, arguments);
    }

    public String getPlural(@NonNull Context context, @PluralsRes int pluralRes, int amount, Object...arguments) {
        return context.getResources().getQuantityString(pluralRes, amount, arguments);
    }

    public Integer getInteger(@NonNull Context context, @IntegerRes int integerRes) {
        return context.getResources().getInteger(integerRes);
    }

    public Boolean getBoolean(@NonNull Context context, @BoolRes int boolRes) {
        return context.getResources().getBoolean(boolRes);
    }

    public int getColor(@NonNull Context context, @ColorRes int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }

    @RequiresApi(26)
    public Typeface getFont(@NonNull Context context, @FontRes int fontRes) {
        return context.getResources().getFont(fontRes);
    }

    public Drawable getDrawable(@NonNull Context context, @DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(context, drawableRes);
    }

    public int getDimensionPixelSize(@NonNull Context context, int res) {
        return context.getResources().getDimensionPixelSize(res);
    }

    public int getDimensionPixelOffset(@NonNull Context context, int res) {
        return context.getResources().getDimensionPixelOffset(res);
    }

    public int[] getIntArray(@NonNull Context context, int res) {
        return context.getResources().getIntArray(res);
    }

    public float getDimension(@NonNull Context context, @DimenRes int res) {
        return context.getResources().getDimension(res);
    }
}
