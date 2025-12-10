package org.anime.assessment.utils

import org.anime.assessment.utils.Utility.printLogConsole
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    const val ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"
    const val NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val NORMAL_FORMAT_FOR_UI = "MMM dd, yyyy"

    /**
     * method to change the format from given format to resultFormat
     */
    fun callDateFormatChangeMethod(
        date: String,
        givenFormat: String,
        resultFormat: String
    ): String {
        return try {
            val inputFormat = SimpleDateFormat(givenFormat, Locale.US)
            val outputFormat = SimpleDateFormat(resultFormat, Locale.US)
            val inputDate = inputFormat.parse(date)
            val outputDateStr = outputFormat.format(inputDate!!)
            outputDateStr.toString()
        } catch (e: Exception) {
            printLogConsole(
                "##DATE",
                "callDateFormatChangeMethod - $date exception --->${e.message}"
            )
            date
        }
    }
}