package com.jerry.blescanner.features.bluetooth.domain

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import java.util.UUID

data class BleDeviceService(
    val service: BluetoothGattService,
    val uuid: UUID,
    val name: String,
    val characteristics: List<BleDeviceCharacteristic>
)

data class BleDeviceCharacteristic(
    val characteristic: BluetoothGattCharacteristic,
    val uuid:UUID,
    val name: String,
    val bleDeviceDescriptors: List<BleDeviceDescriptor>
)

data class BleDeviceDescriptor(
    val descriptor: BluetoothGattDescriptor,
    val uuid:UUID,
    val name: String
)

