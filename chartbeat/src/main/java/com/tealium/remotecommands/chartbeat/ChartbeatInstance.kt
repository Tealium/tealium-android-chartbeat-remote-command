package com.tealium.remotecommands.chartbeat

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.chartbeat.androidsdk.Tracker

class ChartbeatInstance(
    private val application: Application,
    private val chartbeatAccountId: String? = null,
    private val chartbeatSiteId: String? = null
) : ChartbeatCommand {

    private var currentActivity: Activity? = null

    override fun initialize(accountId: String?, siteId: String?) {
        val acctId = accountId ?: chartbeatAccountId
        val site = siteId ?: chartbeatSiteId

        if (!acctId.isNullOrBlank() && !site.isNullOrBlank()) {
            Tracker.setupTracker(acctId, site, application)
        } else Log.d(BuildConfig.TAG, "Chartbeat Account ID and Site ID required")
    }

    override fun logView(viewId: String, viewTitle: String?) {
        if (viewId.isNotBlank()) {
            Tracker.trackView(currentActivity, viewId, viewTitle)
        } else Log.d(BuildConfig.TAG, "Chartbeat View ID is required")
    }

    override fun logUserPaid() {
        Tracker.setUserPaid()
    }

    override fun logUserLoggedIn() {
        Tracker.setUserLoggedIn()
    }

    override fun logUserAnonymous() {
        Tracker.setUserAnonymous()
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}