package com.tealium.remotecommands.chartbeat

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.chartbeat.androidsdk.Tracker
import java.lang.ref.WeakReference

class ChartbeatInstance(
    private val application: Application,
    private val chartbeatAccountId: String? = null,
    private val chartbeatSiteId: String? = null
) : ChartbeatCommand {

    private var weakActivity: WeakReference<Activity>? = null

    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun initialize(accountId: String?, siteId: String?) {
        val acctId = accountId ?: chartbeatAccountId
        val site = siteId ?: chartbeatSiteId

        if (!acctId.isNullOrBlank() && !site.isNullOrBlank()) {
            Tracker.setupTracker(acctId, site, application)
        } else Log.d(BuildConfig.TAG, "Chartbeat Account ID and Site ID required")
    }

    override fun logView(viewId: String, viewTitle: String?) {
        if (viewId.isNotBlank()) {
            Tracker.trackView(weakActivity?.get(), viewId, viewTitle)
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

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStarted(activity: Activity) {
        weakActivity = WeakReference(activity)
    }

    override fun onActivityDestroyed(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        weakActivity = WeakReference(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        weakActivity = WeakReference(activity)
    }
}