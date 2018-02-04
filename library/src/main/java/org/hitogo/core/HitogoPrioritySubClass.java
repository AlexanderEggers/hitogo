package org.hitogo.core;

import java.util.HashSet;

/**
 * This class is used as an object to determine the different priority sub-classes that are
 * currently active/not active. The result of the object will tell the system if the requested
 * alert is allowed to be shown now or later.
 *
 * @since 1.0.0
 */
public class HitogoPrioritySubClass {

    private int subClassPriority = Integer.MAX_VALUE;
    private HashSet<Integer> subClassHashCodes = new HashSet<>();
    private boolean isSubClassVisible;

    /**
     * Returns the priority that this sub-class is representing.
     *
     * @return an Int value
     * @since 1.0.0
     */
    public int getSubClassPriority() {
        return subClassPriority;
    }

    /**
     * Sets the priority for this sub-class.
     *
     * @param subClassPriority an Int value
     * @since 1.0.0
     */
    public void setSubClassPriority(int subClassPriority) {
        this.subClassPriority = subClassPriority;
    }

    /**
     * Returns if the given alert is allowed to be displayed. This is determined one the conditions
     * if the sub-class has one visible alert already and if the given alert hashcode is part of
     * this sub-class.
     *
     * @param alertHashCode an Int value
     * @return True if the alert is allowed to be displayed, false otherwise.
     * @since 1.0.0
     */
    public boolean isAlertVisibilityAllowed(int alertHashCode) {
        return !isSubClassVisible && subClassHashCodes.contains(alertHashCode);
    }

    /**
     * Adds an alert hashcode to this sub-class.
     *
     * @param alertHashCode an Int value
     * @since 1.0.0
     */
    public void addSubClassAlertHashCode(int alertHashCode) {
        this.subClassHashCodes.add(alertHashCode);
    }

    /**
     * Sets the sub-class visibility which is true if one alert in this sub-class is visible to
     * the user.
     *
     * @param subClassVisible a boolean value
     * @since 1.0.0
     */
    public void setSubClassVisible(boolean subClassVisible) {
        if (subClassVisible) {
            isSubClassVisible = true;
        }
    }
}
