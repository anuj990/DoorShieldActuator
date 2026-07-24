package com.example.doorshieldactuator.data.repository.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

data class DoorStatusResponse(
    val locked: Boolean
)

interface DoorApi {

    @GET("status")
    suspend fun getStatus(): Response<DoorStatusResponse>

    @POST("lock")
    suspend fun lockDoor(): Response<Unit>

    @POST("unlock")
    suspend fun unlockDoor(): Response<Unit>
}