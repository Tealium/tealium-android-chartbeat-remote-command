package com.tealium.remotecommands.chartbeat

import android.app.Application
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ChartbeatRemoteCommandTest {
    val COMMAND_NAME_KEY = "command_name"

    @MockK
    lateinit var mockApplication: Application

    @MockK
    lateinit var mockChartbeatInstance: ChartbeatCommand

    lateinit var chartbeatRemoteCommand: ChartbeatRemoteCommand

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        chartbeatRemoteCommand = ChartbeatRemoteCommand(mockApplication)
        chartbeatRemoteCommand.chartbeatInstance = mockChartbeatInstance
    }

    @Test
    fun testSplitCommands() {
        val json = JSONObject()
        json.put("command_name", "initialize, logview, loguserpaid")
        val commands = chartbeatRemoteCommand.splitCommands(json)

        assertEquals(3, commands.count())
        assertEquals("initialize", commands[0])
        assertEquals("logview", commands[1])
        assertEquals("loguserpaid", commands[2])
    }

    @Test
    fun testLogView() {
        val payload = JSONObject()
        payload.put("view_id", "testView123")
        payload.put("view_title", "TesterView")
        payload.put(COMMAND_NAME_KEY, Commands.LOG_VIEW)

        every {
            mockChartbeatInstance.logView(any(), any())
        } just Runs

        chartbeatRemoteCommand.parseCommands(arrayOf(Commands.LOG_VIEW), payload)

        verify {
            mockChartbeatInstance.logView("testView123", "TesterView")
        }

        confirmVerified(mockChartbeatInstance)
    }

    @Test
    fun testLogUserPaid() {
        val payload = JSONObject()
        payload.put(COMMAND_NAME_KEY, Commands.LOG_USER_PAID)

        every {
            mockChartbeatInstance.logUserPaid()
        } just Runs

        chartbeatRemoteCommand.parseCommands(arrayOf(Commands.LOG_USER_PAID), payload)

        verify {
            mockChartbeatInstance.logUserPaid()
        }

        confirmVerified(mockChartbeatInstance)
    }

    @Test
    fun testLogUserLoggedIn() {
        val payload = JSONObject()
        payload.put(COMMAND_NAME_KEY, Commands.LOG_USER_LOGGED_IN)

        every {
            mockChartbeatInstance.logUserLoggedIn()
        } just Runs

        chartbeatRemoteCommand.parseCommands(arrayOf(Commands.LOG_USER_LOGGED_IN), payload)

        verify {
            mockChartbeatInstance.logUserLoggedIn()
        }

        confirmVerified(mockChartbeatInstance)
    }

    @Test
    fun testAnonymizeUser() {
        val payload = JSONObject()
        payload.put(COMMAND_NAME_KEY, Commands.LOG_USER_ANONYMOUS)

        every {
            mockChartbeatInstance.logUserAnonymous()
        } just Runs

        chartbeatRemoteCommand.parseCommands(arrayOf(Commands.LOG_USER_ANONYMOUS), payload)

        verify {
            mockChartbeatInstance.logUserAnonymous()
        }

        confirmVerified(mockChartbeatInstance)
    }
}