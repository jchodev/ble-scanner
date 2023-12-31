package com.jerry.blescanner.features.bluetooth.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

//our class
data class BluetoothDeviceDomain(
    val name: String?,
    val address: String,
    var distance: String,
    var rssi: Int,
    var connected: Boolean = false
)

data class BluetoothDeviceDomain3(
    val name: String?,
    val address: String,
    val distance: String,
    val rssi: Int
)


//our class

class BluetoothDeviceDomain2(
    val name: String?,
    val address: String,
    var distance: String,
    rssi: Int
) {
    var rssi by mutableIntStateOf(rssi)
}
