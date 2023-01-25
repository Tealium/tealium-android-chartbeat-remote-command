package com.tealium.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TealiumHelper.trackEvent("launch", emptyMap())

        logViewButton.setOnClickListener {
            TealiumHelper.trackView("home_view", mapOf("view_name" to "Home", "view_title" to "TealiumChartbeatSampleHome"))
        }

        logUserLoggedInButton.setOnClickListener {
            TealiumHelper.trackEvent("login_success", emptyMap())
        }

        logUserPaidButton.setOnClickListener {
            TealiumHelper.trackEvent("order_confirmation", emptyMap())
        }

        logAnonymousUser.setOnClickListener {
            TealiumHelper.trackEvent("user_logout", emptyMap())
        }
    }
}