package org.hitogo.core;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoUtils {

    private HitogoUtils() {
        throw new IllegalStateException("Methods can only be accessed by static methods.");
    }

    public static boolean isNotEmpty(@NonNull String text) {
        return !text.isEmpty();
    }

    public static boolean isEmpty(@NonNull String text) {
        return text.isEmpty();
    }

    public static void measureView(@NonNull Activity activity, @NonNull View customView,
                                   @Nullable ViewGroup viewGroup) {
        int widthSpec;
        if (null != viewGroup) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getMeasuredWidth(), View.MeasureSpec.AT_MOST);
        } else {
            widthSpec = View.MeasureSpec.makeMeasureSpec(activity.getWindow().getDecorView().getMeasuredWidth(),
                    View.MeasureSpec.AT_MOST);
        }
        customView.measure(widthSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }
}
