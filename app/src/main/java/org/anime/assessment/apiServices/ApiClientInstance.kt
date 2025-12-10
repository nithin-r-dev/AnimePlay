package org.anime.assessment.apiServices

import android.annotation.SuppressLint
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@SuppressLint("StaticFieldLeak")
object ApiClientInstance {
     const val base_url = "https://api.jikan.moe/"
    private val level = HttpLoggingInterceptor.Level.BODY
    private val cookieJar = object : CookieJar {
        private val cookieStore: MutableMap<String, List<Cookie>> = HashMap()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host] = cookies
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            return cookieStore[url.host] ?: ArrayList()
        }
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder().addHeader("Content-Type", "application/json")
                .method(original.method, original.body)
            val request = requestBuilder.build()
            val response = chain.proceed(request)
            response
        }
        .cookieJar(cookieJar)
        .addInterceptor(HttpLoggingInterceptor().setLevel(level))
        .connectTimeout(180, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .build()

    val instance: APiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(APiInterface::class.java)
    }

}