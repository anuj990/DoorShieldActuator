package com.example.doorshieldactuator.data.repository.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

data class DoorStatusResponse(
    val door: String,
    val motion: Boolean,
    val connected: Boolean
)

data class MotionResponse(
    val motion: Boolean
)

interface DoorApi {

    @GET("/")
    suspend fun ping(): Response<String>

    @GET("status")
    suspend fun getStatus(): Response<DoorStatusResponse>

    @POST("lock")
    suspend fun lockDoor(): Response<Unit>

    @POST("unlock")
    suspend fun unlockDoor(): Response<Unit>

    @GET("motion")
    suspend fun getMotion(): Response<MotionResponse>
}