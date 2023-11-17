package com.jerry.blescanner.features.bluetooth.domain

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import java.util.UUID

data class BleDeviceService(
    val service: BluetoothGattService? = null,
    val uuid: UUID,
    val name: String,
    val characteristics: List<BleDeviceCharacteristic>
)

data class BleDeviceCharacteristic(
    val characteristic: BluetoothGattCharacteristic? = null,
    val uuid:UUID,
    val name: String,
    val bleDeviceDescriptors: List<BleDeviceDescriptor>
)

data class BleDeviceDescriptor(
    val descriptor: BluetoothGattDescriptor? = null,
    val uuid:UUID,
    val name: String
)

