package org.anime.assessment.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.Locale


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        Locale.setDefault(Locale.US)
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        lateinit var context: Context

        fun getInstance(): Context {
            return context
        }
        val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    }
}