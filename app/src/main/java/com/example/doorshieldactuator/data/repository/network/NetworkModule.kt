package com.example.doorshieldactuator.data.repository.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(
            EspConfig.CONNECTION_TIMEOUT,
            TimeUnit.SECONDS
        )
        .readTimeout(
            EspConfig.READ_TIMEOUT,
            TimeUnit.SECONDS
        )
        .writeTimeout(
            EspConfig.WRITE_TIMEOUT,
            TimeUnit.SECONDS
        )
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(DeviceManager.baseUrl)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    val doorApi: DoorApi =
        retrofit.create(DoorApi::class.java)
}