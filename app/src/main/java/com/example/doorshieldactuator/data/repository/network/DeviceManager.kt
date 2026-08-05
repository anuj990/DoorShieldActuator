package com.example.doorshieldactuator.data.repository.network

object DeviceManager {

    var esp32Ip = "192.168.4.1"

    var esp32CamIp = "192.168.4.2"

    val baseUrl
        get() = "http://$esp32Ip/"

    val streamUrl
        get() = "http://$esp32CamIp:81/stream"
}