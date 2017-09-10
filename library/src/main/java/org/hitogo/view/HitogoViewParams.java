package org.hitogo.view;

import android.os.Bundle;
import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoViewParams extends HitogoParams {

    private boolean showAnimation;
    private boolean isDismissible;
    private Bundle arguments;

    private String title;
    private String text;

    private Integer state;
    private Integer containerId;
    private Integer titleViewId;
    private Integer textViewId;
    private Integer layoutViewId;

    public HitogoViewParams(HitogoBuilder builder, Bundle publicBundle, Bundle privateBundle) {
        super(builder, publicBundle, privateBundle);
    }

    @Override
    protected void onCreateParams(Bundle bundle) {
        title = bundle.getString("title");
        text = bundle.getString("text");
        state = bundle.getInt("state");
        containerId = (Integer) bundle.getSerializable("containerId");
        titleViewId = (Integer) bundle.getSerializable("titleViewId");
        textViewId = (Integer) bundle.getSerializable("textViewId");
        layoutViewId = (Integer) bundle.getSerializable("layoutViewId");
    }

    @Override
    public boolean hasAnimation() {
        return showAnimation;
    }

    protected boolean isDismissible() {
        return isDismissible;
    }

    public Bundle getArguments() {
        return arguments;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Integer getState() {
        return state;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public Integer getTitleViewId() {
        return titleViewId;
    }

    public Integer getTextViewId() {
        return textViewId;
    }

    public Integer getLayoutViewId() {
        return layoutViewId;
    }
}
