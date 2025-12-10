package org.anime.assessment.utils.bgService

import android.os.Handler
import android.os.Looper
import org.anime.assessment.utils.Utility
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class CustomExecutorService<Params, Progress, Result> protected constructor() {
    val executor: ExecutorService?
    private val tasksRunning = CountDownLatch(1)
    var handler: Handler? = null
        get() {
            if (field == null) {
                synchronized(CustomExecutorService::class.java) {
                    field = Handler(Looper.getMainLooper())
                }
            }
            return field
        }
        private set

    init {
        executor = Executors.newCachedThreadPool { r: Runnable? ->
            val t = Thread(r)
            t.isDaemon = true
            t
        }
    }

    protected fun onPreExecute() {
        // Override this method where ever you want to perform task before background execution get started
    }

    protected abstract fun doInBackground(params: Params?): Result?
    protected abstract fun onPostExecute(result: Result?)
    protected fun onProgressUpdate(value: Progress) {
        // Override this method where ever you want update a progress result
    }

    // used for push progress report to UI
    fun publishProgress(value: Progress) {
        handler!!.post { onProgressUpdate(value) }
    }

    @JvmOverloads
    fun execute(params: Params? = null) {
        handler!!.post {
            onPreExecute()
            executor!!.execute {
                try {
                    val result = doInBackground(params)
                    handler!!.post { onPostExecute(result) }
                } catch (e: Exception) {
                    Utility.printLogConsole("##SuspendedExecutor",
                        "-" + e.message)
                }finally {
                    shutDown()
                }
            }
        }
    }

     fun shutDown() {
        if(!isCancelled) {
            executor?.shutdownNow()
        }
    }

    val isCancelled: Boolean
        get() = executor == null || executor.isTerminated || executor.isShutdown
}
