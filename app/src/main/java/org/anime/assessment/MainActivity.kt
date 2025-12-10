package org.anime.assessment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import org.anime.assessment.application.MyApplication
import org.anime.assessment.constants.AppConstants
import org.anime.assessment.ui.navigation.ScreenNavigation
import org.anime.assessment.ui.theme.AnimePlayTheme
import org.anime.assessment.utils.ConnectivityIndicatorHandler
import org.anime.assessment.utils.PreferencesManager
import org.anime.assessment.utils.Utility

class MainActivity : ComponentActivity() {
    val prefManager = PreferencesManager.getInstance(MyApplication.context)
    private lateinit var connectivityIndicatorHandler: ConnectivityIndicatorHandler
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == AppConstants.ACTION_NET_ON) {
                if (Utility.isNetworkAvailable(context)) {
                    if (prefManager?.getBooleanValue(AppConstants.IS_ONLINE) == false) {
                        prefManager.setBooleanValue(AppConstants.IS_ONLINE, true)
                        Utility.showToast(
                            ContextCompat.getString(
                                this@MainActivity,
                                R.string.connection_restored
                            ), this@MainActivity
                        )
                    }
                } else {
                    prefManager?.setBooleanValue(AppConstants.IS_ONLINE, false)
                    Utility.showToast(
                        ContextCompat.getString(
                            this@MainActivity,
                            R.string.connection_lost
                        ), this@MainActivity
                    )

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            AnimePlayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding))
                    ScreenNavigation(this@MainActivity, navController)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        try {
            ContextCompat.registerReceiver(
                this,
                broadcastReceiver,
                updateIntentFilter(),
                ContextCompat.RECEIVER_EXPORTED
            )
        } catch (e: java.lang.Exception) {
            Utility.printLogConsole("##LocationBaseActivity", "-------------->" + e.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }


    /**
     * @return - Intent Action
     * Method used to set action identifier to Broadcast Receiver
     */
    private fun updateIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(AppConstants.ACTION_NET_ON)
        return intentFilter
    }


}
