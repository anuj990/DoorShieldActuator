package com.example.doorshieldactuator.data.repository.network


import kotlinx.coroutines.delay
import retrofit2.Response

object MockDoorApi {

    private var locked = true

    suspend fun getStatus(): Response<DoorStatusResponse> {

        delay(500)

        return Response.success(
            DoorStatusResponse(
                locked = locked
            )
        )
    }

    suspend fun lockDoor(): Response<Unit> {

        delay(1000)

        locked = true

        return Response.success(Unit)
    }

    suspend fun unlockDoor(): Response<Unit> {

        delay(1000)

        locked = false

        return Response.success(Unit)
    }
}