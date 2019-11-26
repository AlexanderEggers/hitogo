package org.hitogo.core;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.button.core.Button;

import java.util.List;

/**
 * A class which can be used to access certain helper methods.
 *
 * @since 1.0.0
 */
public class HitogoHelper {

    /**
     * Specifies if the given String is not empty.
     *
     * @param text a String or null
     * @return True if the given String is not empty
     */
    public boolean isNotEmpty(@Nullable String text) {
        return !isEmpty(text);
    }

    /**
     * Specifies if the given String is empty or null.
     *
     * @param text a String or null
     * @return True if the given String is empty or null, false otherwise
     */
    public boolean isEmpty(@Nullable String text) {
        return text == null || text.isEmpty();
    }

    /**
     * Measures the width and height for the given View object.
     *
     * @param activity   activity which is used to measure the given View
     * @param customView View object that needs to be measured
     * @param viewGroup  ViewGroup which is used to measure the given View
     * @see ViewGroup
     * @see View
     * @see android.view.View.MeasureSpec
     * @since 1.0.0
     */
    public void measureView(@NonNull Activity activity, @NonNull View customView,
                            @Nullable ViewGroup viewGroup) {
        int widthSpec;
        if (viewGroup != null) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getMeasuredWidth(), View.MeasureSpec.EXACTLY);
        } else {
            widthSpec = View.MeasureSpec.makeMeasureSpec(activity.getWindow().getDecorView().getMeasuredWidth(),
                    View.MeasureSpec.EXACTLY);
        }
        customView.measure(widthSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }

    /**
     * Generates and returns a hashcode for the given alert params object. The hashcode is generated
     * by using the given title, text elements, attached buttons, close button, type and state.
     *
     * @param params params object which is used to generate the hashcode
     * @return an Int
     * @since 1.0.0
     */
    public int generateAlertHashCode(@NonNull AlertParams params) {
        int hashCode = 0;

        String title = params.getTitle();
        hashCode += title != null ? title.hashCode() : 0;

        SparseArray<String> textMap = params.getTextMap();
        for (int i = 0; i < textMap.size(); i++) {
            String text = textMap.valueAt(i);
            hashCode += text.hashCode();
        }

        List<Button> buttonList = params.getButtons();
        for (Button button : buttonList) {
            hashCode += button.hashCode();
        }

        Button closeButton = params.getCloseButton();
        hashCode += closeButton != null ? closeButton.hashCode() : 0;

        hashCode += params.getType().ordinal();
        hashCode += params.getState() != null ? params.getState() : 0;

        return hashCode;
    }
}
