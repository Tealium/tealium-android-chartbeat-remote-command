package com.tealium.remotecommands.chartbeat

import android.app.Application

interface ChartbeatCommand: Application.ActivityLifecycleCallbacks {
    fun initialize(accountId: String?, siteId: String?)

    fun logView(viewId: String, viewTitle: String?)
    fun logUserPaid()
    fun logUserLoggedIn()
    fun logUserAnonymous()
}