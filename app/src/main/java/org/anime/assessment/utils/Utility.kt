package org.anime.assessment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.anime.assessment.application.MyApplication
import org.anime.assessment.utils.model.ErrorResponse

object Utility {
    /**
     * Method to print log on debug filter for developing purpose
     */
    fun printLogConsole(tag: String, content: String) {
        Log.d(tag, content)
    }


    /**
     * Method is return boolean values based on app is foreground or Background
     *
     * @param ctx - Current Activity
     */
    fun isNetworkAvailable(ctx: Context?): Boolean {
        var context = ctx
        if (context == null) {
            context = MyApplication.getInstance()
        }
        var status = false
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            status = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        } catch (e: java.lang.Exception) {
            Log.e("Error", e.message!!)
        }
        return status
    }

    /**
     * Method to parse error body of api
     */
    fun parseErrorMessage(errorBody: ResponseBody?): String? {
        return try {
            // Convert errorBody to a string
            val errorString = errorBody?.string() ?: "Unknown error"

            // Parse the error message (assuming it's JSON)
            val gson = Gson()
            val errorResponse = gson.fromJson(errorString, ErrorResponse::class.java)

            // Return the extracted error message
            errorResponse.message ?: "No error message provided"
        } catch (e: Exception) {
            // Fallback in case parsing fails
            null
        }
    }

    fun showToast(message:String, context: Context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}