package com.jotangi.strollthroughbeimenisland.utility

import android.util.Log
import com.jotangi.strollthroughbeimenisland.api.ApiConfig
import okhttp3.OkHttpClient
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

object ApiUtility {

    private var retrofit: Retrofit? = null

    private val client: Retrofit
        get() {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(ApiConfig.URL_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit!!
        }

    val service = client.create(ApiConfig::class.java)!!

    fun apiFailureMessage(call: Any, t: Throwable): String {
        if (call is okhttp3.Call) {
            val code = t.message
            val url = call.request().url.toString()

            if (!code.isNullOrEmpty()) {
                Log.d("錯誤是：：：：：", code)
                Log.d("錯誤的 URL 是：：：：：", url)
            }
        }

        val message = when (t) {
            is ConnectionShutdownException -> "網路異常，請檢查連線設備"
            is SocketTimeoutException -> "網路連線逾時！"
            is IOException -> "網路連線失敗，請檢查網路！"
            else -> t.message!!
        }

        println("--------$message-----------")

        return message
    }
}