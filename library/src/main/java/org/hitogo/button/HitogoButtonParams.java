package org.hitogo.button;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoButtonParams extends HitogoParams {

    private String text;
    private int[] viewIds;
    private boolean hasButtonView;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        text = holder.getString("text");
        viewIds = holder.getIntList("viewIds");
        hasButtonView = holder.getBoolean("hasButtonView");
    }

    public String getText() {
        return text;
    }

    public int[] getViewIds() {
        return viewIds;
    }

    public boolean hasButtonView() {
        return hasButtonView;
    }
}
