package pherus.health.config

import android.content.Context
import android.net.ConnectivityManager
import pherus.health.BuildConfig

object Config {
    const val appName = "Pherus - Health"
    const val appRoot = BuildConfig.APPLICATION_ID
    const val appVer = BuildConfig.VERSION_CODE

    const val dnsPrimary = "8.8.8.8"
    const val dnsSecondary = "1.1.1.1"

    val filterWord = mutableListOf(
        "fuck you", "mother fucker", "cork sucker", "idiot"
    )

    fun isNetworkAvailable(context: Context): Boolean {
        val cnManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return false
    }
}