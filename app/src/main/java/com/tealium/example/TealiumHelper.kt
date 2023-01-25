package com.tealium.example

import android.app.Application
import android.webkit.WebView
import com.tealium.core.*
import com.tealium.dispatcher.TealiumEvent
import com.tealium.dispatcher.TealiumView
import com.tealium.remotecommanddispatcher.RemoteCommands
import com.tealium.remotecommanddispatcher.remoteCommands
import com.tealium.remotecommands.chartbeat.ChartbeatRemoteCommand
import com.tealium.tagmanagementdispatcher.TagManagement

object TealiumHelper {
    lateinit var tealium: Tealium
    val instanceName = "my_tealium_instance"

    fun initialize(application: Application) {

        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        val config = TealiumConfig(
            application,
            "tealiummobile",
            "android",
            Environment.DEV,
            dispatchers = mutableSetOf(Dispatchers.RemoteCommands, Dispatchers.TagManagement)
        ).apply {
            useRemoteLibrarySettings = true
        }

        tealium = Tealium.create(instanceName, config) {
            val chartbeatRemoteCommand = ChartbeatRemoteCommand(
                application
            )

            // JSON Remote Command - requires local filename
            remoteCommands?.add(chartbeatRemoteCommand, filename = "chartbeat.json")

            // JSON Remote Command - requires url to hosted json file
//            remoteCommands?.add(chartbeatRemoteCommand, remoteUrl = "chartbeat.json")
        }
    }

    fun trackView(viewName: String, data: Map<String, Any>? = null) {
        tealium.track(TealiumView(viewName, data))
    }

    fun trackEvent(tealiumEvent: String, data: Map<String, Any>? = null) {
        tealium.track(TealiumEvent(tealiumEvent, data))
    }
}