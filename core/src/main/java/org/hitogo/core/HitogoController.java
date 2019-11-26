package org.hitogo.core;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.SparseIntArray;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.alert.dialog.DialogAlertImpl;
import org.hitogo.alert.dialog.DialogAlertParams;
import org.hitogo.alert.popup.PopupAlertImpl;
import org.hitogo.alert.popup.PopupAlertParams;
import org.hitogo.alert.snackbar.SnackbarAlertImpl;
import org.hitogo.alert.snackbar.SnackbarAlertParams;
import org.hitogo.alert.toast.ToastAlertImpl;
import org.hitogo.alert.toast.ToastAlertParams;
import org.hitogo.alert.view.ViewAlertImpl;
import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.button.close.CloseButtonParams;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonType;
import org.hitogo.button.text.TextButtonImpl;
import org.hitogo.button.text.TextButtonParams;
import org.hitogo.button.view.ViewButtonImpl;
import org.hitogo.button.view.ViewButtonParams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A class which handles all active/inactive alerts for a certain HitogoContainer instance.
 *
 * @see HitogoContainer
 * @since 1.0.0
 */
@SuppressWarnings({"unchecked"})
public abstract class HitogoController implements LifecycleObserver {

    private final Object syncLock = new Object();
    private final Object countSyncLock = new Object();

    private final LinkedList<AlertImpl> currentViews = new LinkedList<>();
    private final LinkedList<AlertImpl> currentDialogs = new LinkedList<>();
    private final LinkedList<AlertImpl> currentPopups = new LinkedList<>();
    private final LinkedList<AlertImpl> currentOthers = new LinkedList<>();

    private final List<AlertImpl> currentActiveViews = new ArrayList<>();
    private final List<AlertImpl> currentActiveDialogs = new ArrayList<>();
    private final List<AlertImpl> currentActivePopups = new ArrayList<>();
    private final List<AlertImpl> currentActiveOthers = new ArrayList<>();

    private final SparseIntArray alertCountMap = new SparseIntArray();

    private int currentHighestPriorityForViews = Integer.MAX_VALUE;
    private int currentHighestPriorityForDialogs = Integer.MAX_VALUE;
    private int currentHighestPriorityForPopups = Integer.MAX_VALUE;
    private int currentHighestPriorityForOthers = Integer.MAX_VALUE;

    /**
     * Default HitogoController constructor.
     *
     * @param lifecycle Lifecycle which is needed for the HitogoController to react to certain
     *                  lifecycle events of the HitogoContainer object.
     * @since 1.0.0
     */
    public HitogoController(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    /**
     * Prepares the show for a new alert object.
     *
     * @param alert Alert which needs to be displayed
     * @since 1.0.0
     */
    public void show(@NonNull AlertImpl alert) {
        show(alert, false);
    }

    /**
     * Prepares the show for a new alert object. The optional parameter showLater specifies if the
     * given alert should be shown later.
     *
     * @param showLater True if the alert should be displayed later, false otherwise
     * @param alert     Alert which needs to be displayed
     * @since 1.0.0
     */
    public void show(@NonNull AlertImpl alert, boolean showLater) {
        synchronized (syncLock) {
            internalShow(getCurrentAlertList(alert.getAlertType()), alert, showLater);
        }
    }

    /**
     * Prepares to show the next invisible alert from this controller instance. The given alert
     * will be closed first and used to determine the next alert.
     *
     * @param alert Alert which needs to be displayed
     * @since 1.0.0
     */
    public void showNext(@NonNull final Alert alert) {
        synchronized (syncLock) {
            if (!alert.isClosing()) {
                closeByAlert(alert);
            }

            final LinkedList<AlertImpl> currentAlerts = getCurrentAlertList(alert.getAlertType());
            if (!currentAlerts.isEmpty()) {
                searchForNextInvisibleAlert(currentAlerts, alert.getAnimationDuration());
            }
        }
    }

    /**
     * Prepares to show the next invisible alert from this controller instance. The given AlertType
     * will be used to determine the next alert.
     *
     * @param alertType AlertType which should be used to determine the next alert
     * @since 1.0.0
     */
    public void showNext(@NonNull final AlertType alertType) {
        synchronized (syncLock) {
            final LinkedList<AlertImpl> currentAlerts = getCurrentAlertList(alertType);
            if (!currentAlerts.isEmpty()) {
                searchForNextInvisibleAlert(currentAlerts, 0);
            }
        }
    }

    /**
     * Searches for the next invisible alert using the given LinkedList. <b>Internal use only.</b>
     *
     * @param currentAlerts List of alerts which are stored inside this controller for a specific
     *                      alert type
     * @param wait          time in ms that will delay the show for the next alert due to current running
     *                      animations
     * @since 1.0.0
     */
    protected void searchForNextInvisibleAlert(@NonNull final LinkedList<AlertImpl> currentAlerts, @IntRange(from = 0) final long wait) {
        int highestIncludingPriority = getCurrentHighestPriority(currentAlerts);
        for (AlertImpl alert : currentAlerts) {
            if (!isAlertAttached(alert) && (!alert.hasPriority() || alert.getPriority() <= highestIncludingPriority)) {
                if (alert.hasPriority()) {
                    setCurrentHighestPriority(alert.getAlertType(), alert.getPriority());
                }
                makeAlertVisible(alert, wait);
                break;
            }
        }
    }

    /**
     * Determines and handles the show of one alert. This is determined by the already stored
     * alerts, the given showLater and the values inside the new alert. <b>Internal use only.</b>
     *
     * @param currentObjects List of alerts which are stored inside this controller for a specific
     *                       alert type.
     * @param newAlert       alert that needs to be displayed
     * @param showLater      true if the given alert should be displayed later, false otherwise
     * @since 1.0.0
     */
    protected void internalShow(@NonNull final LinkedList<AlertImpl> currentObjects, @NonNull final AlertImpl newAlert, final boolean showLater) {
        currentObjects.addLast(newAlert);

        if (showLater) {
            return;
        }

        List<AlertImpl> currentActiveObjects = getCurrentActiveList(newAlert.getAlertType());
        long waitForClosing = 0;

        if (newAlert.hasPriority()) {
            int newAlertPrio = newAlert.getPriority();
            if (newAlertPrio < getCurrentHighestPriority(newAlert.getAlertType())) {
                setCurrentHighestPriority(newAlert.getAlertType(), newAlertPrio);
                waitForClosing = internalHideByType(newAlert.getAlertType());
            } else {
                return;
            }
        } else if (newAlert.isClosingOthers() && !currentActiveObjects.isEmpty()) {
            long waitByType = internalHideByType(newAlert.getAlertType());
            if (waitByType > waitForClosing) {
                waitForClosing = waitByType;
            }
        }

        makeAlertVisible(newAlert, waitForClosing);
    }

    /**
     * Initialises the process to make a certain alert visible. <b>Internal use only.</b>
     *
     * @param alert alert that needs to be displayed
     * @param wait  wait in ms that needs to be delayed due to running animations
     * @since 1.0.0
     */
    protected void makeAlertVisible(@NonNull final AlertImpl alert, @IntRange(from = 0) final long wait) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alert.getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED) &&
                        (!alert.hasPriority() || alert.getPriority() <= getCurrentHighestPriority(alert.getAlertType()))) {
                    internalMakeVisible(alert);
                }
            }
        }, wait);
    }

    /**
     * Determines the current highest priority in the given list. <b>Internal use only.</b>
     *
     * @param currentObjects List of alerts which are stored inside this controller for a specific
     *                       alert type.
     * @return current highest priority
     * @since 1.0.0
     */
    protected int getCurrentHighestPriority(@NonNull List<AlertImpl> currentObjects) {
        int highestIncludingPriority = Integer.MAX_VALUE;
        for (AlertImpl alert : currentObjects) {
            if (alert.hasPriority() && alert.getPriority() <= highestIncludingPriority) {
                highestIncludingPriority = alert.getPriority();
            }
        }
        return highestIncludingPriority;
    }

    /**
     * Closes all dialog alerts when the HitogoContainer is being destroyed.
     *
     * @since 1.0.0
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void closeOnDestroy() {
        closeByType(AlertType.DIALOG);
    }

    /**
     * Closes all alerts stored inside this controller.
     *
     * @return Time in ms of the longest closing animation. That value can be used to delay certain
     * action until all alerts have been closed.
     * @since 1.0.0
     */
    public long closeAll() {
        synchronized (syncLock) {
            long longestClosingAnim = 0;
            internalCloseAll(currentViews.iterator(), longestClosingAnim);
            internalCloseAll(currentDialogs.iterator(), longestClosingAnim);
            internalCloseAll(currentPopups.iterator(), longestClosingAnim);
            internalCloseAll(currentOthers.iterator(), longestClosingAnim);
            return longestClosingAnim;
        }
    }

    /**
     * Closes all alerts by the given iterator object. <b>Internal use only.</b>
     *
     * @param it             Iterator object that will be used to close it's <b>attached</b> alerts
     * @param currentLongest Time in ms of the longest closing animation. That value can be used to
     *                       delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    protected void internalCloseAll(@NonNull Iterator<AlertImpl> it, @IntRange(from = 0) long currentLongest) {
        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && object.isAttached()) {
                if (object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                internalMakeInvisible(object);
                it.remove();
            }
        }
    }

    /**
     * Closes all alerts stored inside this controller by the given AlertType.
     *
     * @param type AlertType which is used as a reference to close the alerts.
     * @return Time in ms of the longest closing animation. That value can be used to
     * delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    public long closeByType(@NonNull AlertType type) {
        synchronized (syncLock) {
            return internalCloseByType(getCurrentAlertList(type).iterator(), type, false);
        }
    }

    /**
     * Hides all alerts stored inside this controller by the given AlertType. <b>Internal use only.</b>
     *
     * @param type AlertType which is used as a reference to close the alerts.
     * @return Time in ms of the longest closing animation. That value can be used to
     * delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    private long internalHideByType(@NonNull AlertType type) {
        synchronized (syncLock) {
            return internalCloseByType(getCurrentAlertList(type).iterator(), type, true);
        }
    }

    /**
     * Hides or closes all alerts stored inside this controller by the given AlertType. The given
     * onlyHide determines if the found alerts should be closed or only hidden. Hiding a certain
     * alert makes him only invisible for the user, but the alert is still stored inside this
     * controller. <b>Internal use only.</b>
     *
     * @param it       Iterator object that will be used to close it's <b>attached</b> alerts
     * @param type     AlertType which is used as a reference to close the alerts.
     * @param onlyHide True if all found alerts should be hidden only, false if it should close
     *                 those.
     * @return Time in ms of the longest closing animation. That value can be used to
     * delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    private long internalCloseByType(@NonNull Iterator<AlertImpl> it, @NonNull AlertType type, boolean onlyHide) {
        long longestClosingAnim = 0;

        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && type == object.getAlertType() && object.isAttached()) {
                if (object.getAnimationDuration() > longestClosingAnim) {
                    longestClosingAnim = object.getAnimationDuration();
                }

                internalMakeInvisible(object);

                if (!onlyHide) {
                    it.remove();
                }
            }
        }

        return longestClosingAnim;
    }

    /**
     * Closes all alerts stored inside this controller by the given String.
     *
     * @param tag String which is used as a reference to close the alerts.
     * @return Time in ms of the longest closing animation. That value can be used to
     * delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    public long closeByTag(@NonNull String tag) {
        synchronized (syncLock) {
            long longestClosingAnim = 0;
            internalCloseByTag(currentViews.iterator(), tag, longestClosingAnim);
            internalCloseByTag(currentDialogs.iterator(), tag, longestClosingAnim);
            internalCloseByTag(currentPopups.iterator(), tag, longestClosingAnim);
            internalCloseByTag(currentOthers.iterator(), tag, longestClosingAnim);
            return longestClosingAnim;
        }
    }

    /**
     * Closes all alerts stored inside this controller by the given String. <b>Internal use only.</b>
     *
     * @param it             Iterator object that will be used to close it's <b>attached</b> alerts
     * @param tag            String which is used as a reference to close the alerts.
     * @param currentLongest Time in ms of the longest closing animation. That value can be used to
     *                       delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    private void internalCloseByTag(@NonNull Iterator<AlertImpl> it, @NonNull String tag, @IntRange(from = 0) long currentLongest) {
        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && object.isAttached() && tag.equals(object.getTag())) {
                if (object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                internalMakeInvisible(object);
                it.remove();
            }
        }
    }

    /**
     * Closes all alerts stored inside this controller by the given Enum.
     *
     * @param state Enum which is used as a reference to close the alerts.
     * @return Time in ms of the longest closing animation. That value can be used to
     * delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    public long closeByState(Enum state) {
        return closeByState(state.ordinal());
    }

    /**
     * Closes all alerts stored inside this controller by the given int.
     *
     * @param state Int value which is used as a reference to close the alerts.
     * @return Time in ms of the longest closing animation. That value can be used to
     * delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    public long closeByState(int state) {
        synchronized (syncLock) {
            long longestClosingAnim = 0;
            internalCloseByState(currentViews.iterator(), state, longestClosingAnim);
            internalCloseByState(currentDialogs.iterator(), state, longestClosingAnim);
            internalCloseByState(currentPopups.iterator(), state, longestClosingAnim);
            internalCloseByState(currentOthers.iterator(), state, longestClosingAnim);
            return longestClosingAnim;
        }
    }

    /**
     * Closes all alerts stored inside this controller by the given int value. <b>Internal use only.</b>
     *
     * @param it             Iterator object that will be used to close it's <b>attached</b> alerts
     * @param state          Int value which is used as a reference to close the alerts.
     * @param currentLongest Time in ms of the longest closing animation. That value can be used to
     *                       delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    private void internalCloseByState(@NonNull Iterator<AlertImpl> it, int state, @IntRange(from = 0) long currentLongest) {
        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object != null && object.isAttached() && object.getState() == state) {
                if (object.getAnimationDuration() > currentLongest) {
                    currentLongest = object.getAnimationDuration();
                }

                internalMakeInvisible(object);
                it.remove();
            }
        }
    }

    /**
     * Closes all alerts stored inside this controller by the given alert.
     *
     * @param alert Alert which is used as a reference to close the alerts.
     * @return Time in ms of the longest closing animation. That value can be used to
     * delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    public long closeByAlert(@NonNull Alert alert) {
        synchronized (syncLock) {
            return internalCloseByAlert(getCurrentAlertList(alert.getAlertType()).iterator(), alert);
        }
    }

    /**
     * Closes all alerts stored inside this controller by the given alert. <b>Internal use only.</b>
     *
     * @param it    Iterator object that will be used to close it's <b>attached</b> alerts
     * @param alert Alert which is used as a reference to close the alerts.
     * @return Time in ms of the longest closing animation. That value can be used to
     * delay certain action until all alerts have been closed.
     * @since 1.0.0
     */
    private long internalCloseByAlert(@NonNull Iterator<AlertImpl> it, final @NonNull Alert alert) {
        long longestClosingAnim = 0;

        while (it.hasNext()) {
            AlertImpl object = it.next();
            if (object.equals(alert)) {
                if (object.getAnimationDuration() > longestClosingAnim) {
                    longestClosingAnim = object.getAnimationDuration();
                }

                internalMakeInvisible((AlertImpl) alert);
                it.remove();
            }
        }

        return longestClosingAnim;
    }

    /**
     * Makes the given alert object visible if it is not yet been made visible to the user.
     * <b>Internal use only.</b>
     *
     * @param object Alert which needs to be displayed
     * @since 1.0.0
     */
    private void internalMakeVisible(@NonNull AlertImpl object) {
        synchronized (countSyncLock) {
            int count = alertCountMap.get(object.hashCode());
            if (count <= 0) {
                alertCountMap.put(object.hashCode(), 1);
                object.makeVisible();
                getCurrentActiveList(object.getAlertType()).add(object);
            } else {
                alertCountMap.put(object.hashCode(), count + 1);
            }
        }
    }

    /**
     * Makes the given alert object invisible if it is not yet been made invisible to the user.
     * <b>Internal use only.</b>
     *
     * @param object Alert which needs to be hidden
     * @since 1.0.0
     */
    private void internalMakeInvisible(@NonNull AlertImpl object) {
        synchronized (countSyncLock) {
            int count = alertCountMap.get(object.hashCode());
            if (count <= 1) {
                alertCountMap.put(object.hashCode(), 0);
                internalMakeActiveAlertInvisible(object);
            } else {
                alertCountMap.put(object.hashCode(), count - 1);
            }
        }
    }

    /**
     * Ensures that the right alert (the one that has been attached to the user) has been selected
     * to be closed. <b>Internal use only.</b>
     *
     * @param toBeClosedAlert Reference to the alert which needs to be closed
     * @since 1.0.0
     */
    private void internalMakeActiveAlertInvisible(@NonNull AlertImpl toBeClosedAlert) {
        Iterator<AlertImpl> iterator = getCurrentActiveList(toBeClosedAlert.getAlertType()).iterator();

        while (iterator.hasNext()) {
            AlertImpl alert = iterator.next();

            if (toBeClosedAlert.equals(alert)) {
                if (alert.hasPriority()) {
                    setCurrentHighestPriority(alert.getAlertType(), Integer.MAX_VALUE);
                }

                alert.makeInvisible();
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Specifies if the given alert has been already attached and is visible to the user.
     * <b>Internal use only.</b>
     *
     * @param alert Reference to the alert which needs to be checked
     * @return True if the given alert is already visible, false otherwise
     * @since 1.0.0
     */
    private boolean isAlertAttached(@NonNull AlertImpl alert) {
        return alertCountMap.get(alert.hashCode()) > 0;
    }

    /**
     * Returns the current stored alerts for the given AlertType. <b>Internal use only.</b>
     *
     * @param type AlertType which is used to determine the correct list
     * @return List of alerts that are currently stored
     * @since 1.0.0
     */
    @NonNull
    protected LinkedList<AlertImpl> getCurrentAlertList(@NonNull AlertType type) {
        switch (type) {
            case VIEW:
                return currentViews;
            case DIALOG:
                return currentDialogs;
            case POPUP:
                return currentPopups;
            case OTHER:
                return currentOthers;
            default:
                return new LinkedList<>();
        }
    }

    /**
     * Returns the active stored alerts for the given AlertType. Active alerts are actually visible
     * to the user. Not to confuse with the getCurrentAlertList which is providing a list of visible
     * and not yet visible alerts. <b>Internal use only.</b>
     *
     * @param type AlertType which is used to determine the correct list
     * @return List of alerts that are currently stored
     * @since 1.0.0
     */
    @NonNull
    protected List<AlertImpl> getCurrentActiveList(@NonNull AlertType type) {
        switch (type) {
            case VIEW:
                return currentActiveViews;
            case DIALOG:
                return currentActiveDialogs;
            case POPUP:
                return currentActivePopups;
            case OTHER:
                return currentActiveOthers;
            default:
                return new LinkedList<>();
        }
    }

    /**
     * Sets the current highest priority for the given AlertType. <b>Internal use only.</b>
     *
     * @param type        AlertType which is used to determine the correct priority value
     * @param newPriority new priority value
     * @since 1.0.0
     */
    protected void setCurrentHighestPriority(@NonNull AlertType type, int newPriority) {
        switch (type) {
            case VIEW:
                currentHighestPriorityForViews = newPriority;
                break;
            case DIALOG:
                currentHighestPriorityForDialogs = newPriority;
                break;
            case POPUP:
                currentHighestPriorityForPopups = newPriority;
                break;
            case OTHER:
                currentHighestPriorityForOthers = newPriority;
                break;
            default:
                break;
        }
    }

    /**
     * Returns the current highest priority for the given AlertType. <b>Internal use only.</b>
     *
     * @param type AlertType which is used to determine the correct value
     * @return current highest priority
     * @since 1.0.0
     */
    protected int getCurrentHighestPriority(@NonNull AlertType type) {
        switch (type) {
            case VIEW:
                return currentHighestPriorityForViews;
            case DIALOG:
                return currentHighestPriorityForDialogs;
            case POPUP:
                return currentHighestPriorityForPopups;
            case OTHER:
                return currentHighestPriorityForOthers;
            default:
                return Integer.MAX_VALUE;
        }
    }

    /**
     * Provides the default ViewAlert class.
     *
     * @return Class object which is extending AlertImpl
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertImpl> provideDefaultViewClass() {
        return ViewAlertImpl.class;
    }

    /**
     * Provides the default ViewAlert params class.
     *
     * @return Class object which is extending AlertParams
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertParams> provideDefaultViewParamsClass() {
        return ViewAlertParams.class;
    }

    /**
     * Provides the default DialogAlert class.
     *
     * @return Class object which is extending AlertImpl
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertImpl> provideDefaultDialogClass() {
        return DialogAlertImpl.class;
    }

    /**
     * Provides the default DialogAlert params class.
     *
     * @return Class object which is extending AlertParams
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertParams> provideDefaultDialogParamsClass() {
        return DialogAlertParams.class;
    }

    /**
     * Provides the default PopupAlert class.
     *
     * @return Class object which is extending AlertImpl
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertImpl> provideDefaultPopupClass() {
        return PopupAlertImpl.class;
    }

    /**
     * Provides the default PopupAlert params class.
     *
     * @return Class object which is extending AlertParams
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertParams> provideDefaultPopupParamsClass() {
        return PopupAlertParams.class;
    }

    /**
     * Provides the default ViewButton class.
     *
     * @return Class object which is extending ButtonImpl
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends ButtonImpl> provideDefaultViewButtonClass() {
        return ViewButtonImpl.class;
    }

    /**
     * Provides the default ViewButton params class.
     *
     * @return Class object which is extending ButtonParams
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends ButtonParams> provideDefaultViewButtonParamsClass() {
        return ViewButtonParams.class;
    }

    /**
     * Provides the default CloseButton class.
     *
     * @return Class object which is extending ButtonImpl
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends ButtonImpl> provideDefaultCloseButtonClass() {
        return ViewButtonImpl.class;
    }

    /**
     * Provides the default CloseButton params class.
     *
     * @return Class object which is extending ButtonParams
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends ButtonParams> provideDefaultCloseButtonParamsClass() {
        return CloseButtonParams.class;
    }

    /**
     * Provides the default TextButton class.
     *
     * @return Class object which is extending ButtonImpl
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends ButtonImpl> provideDefaultTextButtonClass() {
        return TextButtonImpl.class;
    }

    /**
     * Provides the default TextButton params class.
     *
     * @return Class object which is extending ButtonParams
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends ButtonParams> provideDefaultTextButtonParamsClass() {
        return TextButtonParams.class;
    }

    /**
     * Provides the default SnackbarAlert class.
     *
     * @return Class object which is extending AlertImpl
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertImpl> provideDefaultSnackbarClass() {
        return SnackbarAlertImpl.class;
    }

    /**
     * Provides the default SnackbarAlert params class.
     *
     * @return Class object which is extending AlertParams
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertParams> provideDefaultSnackbarParamsClass() {
        return SnackbarAlertParams.class;
    }

    /**
     * Provides the default ToastAlert class.
     *
     * @return Class object which is extending AlertImpl
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertImpl> provideDefaultToastClass() {
        return ToastAlertImpl.class;
    }

    /**
     * Provides the default ToastAlert params class.
     *
     * @return Class object which is extending AlertParams
     * @since 1.0.0
     */
    @NonNull
    public Class<? extends AlertParams> provideDefaultToastParamsClass() {
        return ToastAlertParams.class;
    }

    /**
     * Provides the default HitogoParamsHolder for given AlertType.
     *
     * @param alertType AlertType which is used to determine the relevant HitogoParamsHolder
     * @return a new HitogoParamsHolder
     * @since 1.0.0
     */
    @NonNull
    public <T extends HitogoParamsHolder> T provideAlertParamsHolder(@NonNull AlertType alertType) {
        return (T) new HitogoParamsHolder();
    }

    /**
     * Provides the default HitogoParamsHolder for the given ButtonType.
     *
     * @param buttonType ButtonType which is used to determine the relevant HitogoParamsHolder
     * @return a new HitogoParamsHolder
     * @since 1.0.0
     */
    @NonNull
    public <T extends HitogoParamsHolder> T provideButtonParamsHolder(@NonNull ButtonType buttonType) {
        return (T) new HitogoParamsHolder();
    }

    /**
     * Provides the default layout id for the given state. This layout is used for the ViewAlerts.
     *
     * @param state State which is used to determine the relevant layout id
     * @return an Integer or null
     * @since 1.0.0
     */
    @LayoutRes
    @Nullable
    public Integer provideViewLayout(int state) {
        return null;
    }

    /**
     * Provides the default layout id for the given state. This layout is used for the PopupAlerts.
     *
     * @param state State which is used to determine the relevant layout id
     * @return an Integer or null
     * @since 1.0.0
     */
    @LayoutRes
    @Nullable
    public Integer providePopupLayout(int state) {
        return null;
    }

    /**
     * Provides the default layout id for the given state. This layout is used for the DialogAlerts.
     *
     * @param state State which is used to determine the relevant layout id
     * @return an Integer or null
     * @since 1.0.0
     */
    @LayoutRes
    @Nullable
    public Integer provideDialogLayout(int state) {
        return null;
    }

    /**
     * Provides the default layout id for the given state. This layout is used for the OtherAlerts
     * (like ToastAlert <b>and</b> SnackbarAlert).
     *
     * @param state State which is used to determine the relevant layout id
     * @return an Integer or null
     * @since 1.0.0
     */
    @LayoutRes
    @Nullable
    public Integer provideOtherLayout(int state) {
        return null;
    }

    /**
     * Provides the default ViewGroup id for ViewAlerts which are within the regular layout.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultViewAlertLayoutContainerId() {
        return null;
    }

    /**
     * Provides the default ViewGroup id for ViewAlerts which are within the generic layout. This
     * generic layout could be from the parent activity/fragment which includes certain views
     * regardless of the page.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultViewAlertOverlayContainerId() {
        return null;
    }

    /**
     * Provides the default TextView id for the alert text if no view id has been defined.
     *
     * @param type AlertType which is used to determine the relevant default id
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultAlertTextViewId(@NonNull AlertType type) {
        return null;
    }

    /**
     * Provides the default TextView id for the alert title if no view id has been defined.
     *
     * @param type AlertType which is used to determine the relevant default id
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultAlertTitleViewId(@NonNull AlertType type) {
        return null;
    }

    /**
     * Provides the default View id for a alert drawable if no view id has been defined.
     *
     * @param type AlertType which is used to determine the relevant default id
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultAlertDrawableViewId(@NonNull AlertType type) {
        return null;
    }

    /**
     * Provides the default HitogoAnimation which can be used to animate alerts.
     *
     * @param type AlertType which is used to determine the relevant default HitogoAnimation
     * @return new HitogoAnimation or null
     * @since 1.0.0
     */
    @Nullable
    public <T extends HitogoAnimation> T provideDefaultAlertAnimation(@NonNull AlertType type) {
        return null;
    }

    /**
     * Provides the default View id for the animation ViewGroup. This ViewGroup is used to support
     * the animation implementation. It can be used to fade-in/out it's elements.
     *
     * @param type AlertType which is used to determine the relevant default id
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultAlertAnimationLayoutViewId(@NonNull AlertType type) {
        return null;
    }

    /**
     * Provides the default View id for the close button icon. The icon is the actual visible part
     * of the button for the user. The close button is only used for actions which is closing the
     * alert.
     *
     * @param type ButtonType which is used to determine the relevant default id
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultButtonCloseIconId(@NonNull ButtonType type) {
        return null;
    }

    /**
     * Provides the default View id for the close button click. The click is the invisible part
     * of the button for the user. This part is used to enlarge and simplifies the actual click
     * area for the user. The close button is only used for actions which is closing the alert.
     *
     * @param type ButtonType which is used to determine the relevant default id
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultButtonCloseClickId(@NonNull ButtonType type) {
        return null;
    }

    /**
     * Provides the default TextView id for the button text if no view id has been defined.
     *
     * @param type ButtonType which is used to determine the relevant default id
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultButtonTextViewId(@NonNull ButtonType type) {
        return null;
    }

    /**
     * Provides the default View id for a button drawable if no view id has been defined.
     *
     * @param type ButtonType which is used to determine the relevant default id
     * @return an Integer or null
     * @since 1.0.0
     */
    @Nullable
    public Integer provideDefaultButtonDrawableViewId(@NonNull ButtonType type) {
        return null;
    }

    /**
     * Provides the default HitogoHelper object for this HitogoController instance.
     *
     * @return new HitogoHelper
     * @see HitogoHelper
     * @since 1.0.0
     */
    @NonNull
    public <T extends HitogoHelper> T provideHelper() {
        return (T) new HitogoHelper();
    }

    /**
     * Provides the default HitogoAccessor object for this HitogoController instance.
     *
     * @return new HitogoAccessor
     * @see HitogoAccessor
     * @since 1.0.0
     */
    @NonNull
    public <T extends HitogoAccessor> T provideAccessor() {
        return (T) new HitogoAccessor();
    }

    /**
     * Specifies if the <b>app</b> is currently running inside a DEBUG mode. This method needs to
     * be implemented and should use the app's BuildConfig file to determine the current DEBUG mode.
     * This method is responsible for different areas of the alert library to determine if it should
     * run certain code, like onCheck for alerts and buttons. This onCheck method has the potential
     * to throw exceptions. Those exceptions are usually not wanted for the productive version of
     * the app. This method suppresses these exceptions if it <b>not</b> inside a DEBUG mode.
     *
     * @return True if the app is currently as DEBUG, false otherwise
     * @since 1.0.0
     */
    public abstract boolean provideIsDebugState();
}
