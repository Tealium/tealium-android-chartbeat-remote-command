package com.tealium.remotecommands.chartbeat

import android.app.Application
import android.util.Log
import com.tealium.remotecommands.RemoteCommand
import org.json.JSONObject

class ChartbeatRemoteCommand(
    private val application: Application,
    private val chartbeatAccountId: String? = null,
    private val chartbeatSiteId: String? = null,
    commandId: String = DEFAULT_COMMAND_ID,
    description: String = DEFAULT_COMMAND_DESCRIPTION
): RemoteCommand(commandId, description, BuildConfig.TEALIUM_CHARTBEAT_VERSION) {

    internal var chartbeatInstance: ChartbeatCommand = ChartbeatInstance(application, chartbeatAccountId, chartbeatSiteId)

    override fun onInvoke(response: Response) {
        val payload = response.requestPayload
        val commands = splitCommands(payload)
        parseCommands(commands, payload)
    }

    internal fun parseCommands(commands: Array<String>, payload: JSONObject) {
        commands.forEach { command ->
            Log.d(BuildConfig.TAG, "Processing command: $command with payload: $payload")
            when (command) {
                Commands.INITIALIZE -> {
                    val accountId = payload.optString(Config.ACCOUNT_ID, "")
                    val siteId = payload.optString(Config.SITE_ID, "")
                    chartbeatInstance.initialize(accountId, siteId)
                }
                Commands.LOG_VIEW -> {
                    val viewId = payload.optString(View.ID, "")
                    val viewTitle = payload.optString(View.TITLE, "")
                    chartbeatInstance.logView(viewId, viewTitle)
                }
                Commands.LOG_USER_PAID -> {
                    chartbeatInstance.logUserPaid()
                }
                Commands.LOG_USER_LOGGED_IN -> {
                    chartbeatInstance.logUserLoggedIn()
                }
                Commands.LOG_USER_ANONYMOUS -> {
                    chartbeatInstance.logUserAnonymous()
                }
            }
        }
    }

    internal fun splitCommands(payload: JSONObject): Array<String> {
        val command = payload.optString(Commands.COMMAND_KEY, "")
        return command.split(Commands.SEPARATOR).map {
            it.trim().lowercase()
        }.toTypedArray()
    }

    companion object {
        const val DEFAULT_COMMAND_ID = "chartbeat"
        const val DEFAULT_COMMAND_DESCRIPTION = "Tealium-Chartbeat Remote Command"
    }
}