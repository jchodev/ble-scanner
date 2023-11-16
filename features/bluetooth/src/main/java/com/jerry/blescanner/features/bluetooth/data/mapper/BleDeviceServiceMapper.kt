package com.jerry.blescanner.features.bluetooth.data.mapper

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import com.jerry.blescanner.features.bluetooth.constants.AllGattCharacteristics
import com.jerry.blescanner.features.bluetooth.constants.AllGattDescriptors
import com.jerry.blescanner.features.bluetooth.constants.AllGattServices
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceCharacteristic
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceDescriptor
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceService

fun BluetoothGattService.toBleDeviceService(): BleDeviceService {
    return BleDeviceService(
        uuid = uuid,
        name = AllGattServices.lookup(uuid),
        characteristics = characteristics.map { it.toBleDeviceCharacteristic() },
        service = this
    )
}

fun BluetoothGattCharacteristic.toBleDeviceCharacteristic(): BleDeviceCharacteristic {
    return BleDeviceCharacteristic(
        uuid = uuid,
        name = AllGattCharacteristics.lookup(uuid),
        bleDeviceDescriptors = descriptors.map { it.toBleDeviceDescriptor() },
        characteristic = this
    )
}

fun BluetoothGattDescriptor.toBleDeviceDescriptor(): BleDeviceDescriptor {
    return BleDeviceDescriptor(
        descriptor = this,
        uuid = uuid,
        name = AllGattDescriptors.lookup(uuid)
    )
}