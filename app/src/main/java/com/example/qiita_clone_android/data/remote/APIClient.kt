package com.example.qiita_clone_android.data.remote

import com.example.qiita_clone_android.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val URL = "https://qiita.com/" // BaseUrlを指定

    // Okhttp
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer ${BuildConfig.QIITA_API_TOKEN}")
                .build()
            return@addInterceptor chain.proceed(request)
        }
        .build()

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setLenient()
        .create()

    // retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
}