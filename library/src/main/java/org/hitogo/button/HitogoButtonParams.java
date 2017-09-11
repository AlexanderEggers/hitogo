package org.hitogo.button;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoButtonParams extends HitogoParams {

    private String text;
    private int[] viewIds;
    private boolean hasButtonView;
    private boolean closingAfterExecute;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        text = holder.getString("text");
        viewIds = holder.getIntList("viewIds");
        hasButtonView = holder.getBoolean("hasButtonView");
        closingAfterExecute = holder.getBoolean("closingAfterExecute");
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

    public boolean isClosingAfterExecute() {
        return closingAfterExecute;
    }
}
