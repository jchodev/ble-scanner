package com.jerry.blescanner.features.bluetooth.data.mapper

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.mutableStateOf
import com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceDomain


@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(rssi: Int): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address,
        rssi = rssi,
        distance = calculateDistance(rssi)
    )
}

private fun calculateDistance(rssi: Int): String {
    val txPower = -59 // //hard coded power value. Usually ranges between -59 to -65
    val ratio = rssi * 1.0 / txPower
    val distance = if (ratio < 1.0) {
        Math.pow(ratio, 10.0)
    } else {
        0.89976 * Math.pow(ratio, 7.7095) + 0.111
    }

    val formattedDistance: String = if (distance < 1.0) {
        val distanceInCm = (distance * 100).toInt()
        "$distanceInCm cm"
    } else {
        val distanceInM = "%.2f".format(distance)
        "$distanceInM m"
    }

    return formattedDistance
}