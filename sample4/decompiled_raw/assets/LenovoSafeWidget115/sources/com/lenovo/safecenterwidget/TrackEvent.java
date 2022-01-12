package com.lenovo.safecenterwidget;

import android.content.Context;
import android.util.Log;
import com.lenovo.lps.reaper.sdk.AnalyticsTracker;
/* loaded from: classes.dex */
public class TrackEvent {
    static final String CATEGORY_APP_RMISSION = "appPerm";
    private static final String CATEGORY_CHARGE_SHIELD = "ChargeShield";
    static final String CATEGORY_FIVE_PROTECT = "FiveProtectSwitch";
    static final String CATEGORY_HARASS_INTERCEPT = "HarassIntercept";
    private static final String CATEGORY_HEALTH_CHECKUP = "health_checkup";
    static final String CATEGORY_PRIVACY = "privacy";
    static final String CATEGORY_SUPERTOOL = "supertool";
    static final String OTHER = "other";
    static final String TAG = "wu0wu";
    private static AnalyticsTracker tracker;

    public static void initialize(Context context) {
        try {
            tracker = AnalyticsTracker.getInstance();
            tracker.initialize(context);
        } catch (Exception e) {
            Log.i(TAG, "TrackEvent initialize Exception:" + e.toString());
        }
    }

    public static void shutdown() {
        try {
            tracker.shutdown();
        } catch (Exception e) {
            Log.i(TAG, "TrackEvent shutdown Exception:" + e.toString());
        }
    }

    public static AnalyticsTracker get(Context context) {
        if (tracker == null) {
            Log.i(TAG, "tracker==null");
            initialize(context);
        }
        return tracker;
    }

    public static void trackPause(Context context) {
        try {
            get(context).trackPause(context);
        } catch (Exception e) {
            Log.i(TAG, "TrackEvent trackPause Exception:" + e.toString());
        }
    }

    public static void trackResume(Context context) {
        try {
            get(context).trackResume(context);
        } catch (Exception e) {
            Log.i(TAG, "TrackEvent trackResume Exception:" + e.toString());
        }
    }

    public static void reportOneKeyHealthCheckup() {
        if (tracker == null) {
            Log.i(TAG, "reportOneKeyHealthCheckup tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_HEALTH_CHECKUP, "OneKeyHealthCheckup", null, 0);
        }
    }

    public static void reportCancelHealthCheckup() {
        if (tracker == null) {
            Log.i(TAG, "reportCancelHealthCheckup tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_HEALTH_CHECKUP, "cancelHealthCheckup", null, 0);
        }
    }

    public static void reportHealthOptimizeImmediately() {
        if (tracker == null) {
            Log.i(TAG, "reportHealthOptimizeImmediately tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_HEALTH_CHECKUP, "HealthOptimizeImmediately", null, 0);
        }
    }

    public static void reportProtectTrafficSwitchChange(Boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportProtectTrafficSwitchChange tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_CHARGE_SHIELD, "protect_traffic_switch_change", String.valueOf(isOn), 0);
        }
    }

    public static void reportSendSmsOnBackgroud(String packageName) {
        if (tracker == null) {
            Log.i(TAG, "reportSendSmsOnBackgroud tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_CHARGE_SHIELD, "SendSmsOnBackgroud", packageName, 0);
        }
    }

    public static void reportCallOnBackgroud(String packageName) {
        if (tracker == null) {
            Log.i(TAG, "reportCallOnBackgroud tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_CHARGE_SHIELD, "CallOnBackgroud", packageName, 0);
        }
    }

    public static void reportProtectPeepSwitchChange(Boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportProtectPeepSwitchChange tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_PRIVACY, "Protect_Peep_Switch_change", String.valueOf(isOn), 0);
        }
    }

    public static void reportProtectHarassSwitchChange(Boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportProtectHarassSwitchChange tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_HARASS_INTERCEPT, "Protect_Harass_Switch_change", String.valueOf(isOn), 0);
        }
    }

    public static void reportInterceptGarbageSMS() {
        if (tracker == null) {
            Log.i(TAG, "reportInterceptGarbageSMS tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_HARASS_INTERCEPT, "InterceptGarbageSMS", null, 0);
        }
    }

    public static void reportInterceptHarassCalls() {
        if (tracker == null) {
            Log.i(TAG, "reportInterceptHarassCalls tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_HARASS_INTERCEPT, "InterceptHarassCall", null, 0);
        }
    }

    public static void reportUninstallApp(String packageName) {
        if (tracker == null) {
            Log.i(TAG, "reportUninstallApp tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_APP_RMISSION, "UninstallApp", packageName, 0);
        }
    }

    public static void reportChildModeSwitchChange(Boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportChildModeSwitchChange tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_SUPERTOOL, "child_mode_switch_change", String.valueOf(isOn), 0);
        }
    }

    public static void reportGuestModeSwitchChange(Boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportGuestModeSwitchChange tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_SUPERTOOL, "guest_mode_switch_change", String.valueOf(isOn), 0);
        }
    }

    public static void reportEntryPrivacySpaceCount(int count) {
        if (tracker == null) {
            Log.i(TAG, "reportEntryPrivacySpaceCount tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_PRIVACY, "Entry_Privacy_Space_Count", String.valueOf(count), 0);
        }
    }

    public static void reportProtectThiefSwitchChange(Boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportProtectThiefSwitchChange tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_SUPERTOOL, "Protect_Thief_Switch_change", String.valueOf(isOn), 0);
        }
    }

    public static void reportsafePay(boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportsafePay tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_SUPERTOOL, "safepaymen_on", String.valueOf(isOn), 0);
        }
    }

    public static void reportSafeInputMethod(boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportSafeInputMethod tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_SUPERTOOL, "SafeInputMethod_on", String.valueOf(isOn), 0);
        }
    }

    public static void reportEntryLeCloudSync() {
        if (tracker == null) {
            Log.i(TAG, "reportStartLeCloudSync tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_SUPERTOOL, "EntryLeCloudSync", null, 0);
        }
    }

    public static void reportCleanMemory() {
        if (tracker == null) {
            Log.i(TAG, "reportCleanMemory tracker == null");
        } else {
            tracker.trackEvent(OTHER, "CleanMemory", null, 0);
        }
    }

    public static void reportDisableBootStartApp(String packageName) {
        if (tracker == null) {
            Log.i(TAG, "reportDisableBootStartApp tracker == null");
        } else {
            tracker.trackEvent(OTHER, "DisableBootStartApp", packageName, 0);
        }
    }

    public static void reportProtectVirusSwitchChange(Boolean isOn) {
        if (tracker == null) {
            Log.i(TAG, "reportProtectVirusSwitchChange tracker == null");
        } else {
            tracker.trackEvent(CATEGORY_FIVE_PROTECT, "Protect_Virus_Switch_change", String.valueOf(isOn), 0);
        }
    }

    public static void reportEntrySafeCenterCount(int count) {
        if (tracker == null) {
            Log.i(TAG, "reportEntrySafeCenterCount tracker == null");
        } else {
            tracker.trackEvent("home_page", "Entry_Safe_Center_Count", String.valueOf(count), 0);
        }
    }

    public static void reportClickOneKeyEndTaskCount(int count) {
        if (tracker == null) {
            Log.i(TAG, "reportClickOneKeyEndTaskCount tracker == null");
        } else {
            tracker.trackEvent("app_manager", "Click_One_Key_End_Task_Count", String.valueOf(count), 0);
        }
    }

    public static void reportTrustApp(String packageName) {
        if (tracker == null) {
            Log.i(TAG, "reportClickOneKeyEndTaskCount tracker == null");
        } else {
            tracker.trackEvent("app_permission_manager", "trust_app_packageName", packageName, 0);
        }
    }

    public static void reportWidgetKillOneApp(String packageName) {
        if (tracker == null) {
            Log.i(TAG, "reportWidgetKillOneApp tracker == null");
        } else {
            tracker.trackEvent("app_widget", "kill_one_app", packageName, 0);
        }
    }

    public static void reportWidgetKillAllApp() {
        if (tracker == null) {
            Log.i(TAG, "reportWidgetKillAllApp tracker == null");
        } else {
            tracker.trackEvent("app_widget", "kill_all_app", null, 0);
        }
    }

    public static void reportWidgetRefresh() {
        if (tracker == null) {
            Log.i(TAG, "reportWidgetRefresh tracker == null");
        } else {
            tracker.trackEvent("app_widget", "refresh", null, 0);
        }
    }

    public static void reportWidgetEntrySafeCenter() {
        if (tracker == null) {
            Log.i(TAG, "reportWidgetEntrySafeCenter tracker == null");
        } else {
            tracker.trackEvent("app_widget", "entry_safecenter", null, 0);
        }
    }
}
