package com.example.doorshieldactuator.data.repository.network


object DeviceConfig {

    const val ESP32_IP = "192.168.1.100"

    const val ESP32_CAM_IP = "192.168.1.101"

    val BASE_URL =
        "http://$ESP32_IP/"

    val STREAM_URL =
        "http://$ESP32_CAM_IP:81/stream"
}