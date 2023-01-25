package com.tealium.remotecommands.chartbeat

object Commands {
    const val COMMAND_KEY = "command_name"
    const val SEPARATOR = ","

    const val INITIALIZE = "initialize"
    const val LOG_VIEW = "logview"
    const val LOG_USER_PAID = "loguserpaid"
    const val LOG_USER_LOGGED_IN = "loguserloggedin"
    const val LOG_USER_ANONYMOUS = "loguseranonymous"
}

object Config {
    const val ACCOUNT_ID = "account_id"
    const val SITE_ID = "site_id"
}

object View {
    const val ID = "view_id"
    const val TITLE = "view_title"
}