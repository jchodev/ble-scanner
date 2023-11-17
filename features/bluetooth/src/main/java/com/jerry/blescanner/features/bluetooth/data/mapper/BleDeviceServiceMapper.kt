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

/*
BluetoothGattService（藍牙 GATT 服務）：它代表著藍牙裝置中的一個服務。服務是藍牙設備上的一個功能單元，通常包含一個或多個特徵（BluetoothGattCharacteristic）。服務可以提供設備的不同功能，例如心率監測、電池狀態等等。每個服務都有一個唯一的識別符號（UUID）來區分不同的服務。

BluetoothGattCharacteristic（藍牙 GATT 特徵）：它代表著藍牙裝置服務中的一個特徵。特徵是服務的一個特定功能，例如傳感器數據、設備配置等。特徵可以包含數據值，並且可以進行讀取和寫入操作。每個特徵也有一個唯一的 UUID 來識別它。

BluetoothGattDescriptor（藍牙 GATT 描述符）：它代表著藍牙特徵中的一個描述符。描述符提供了特徵的額外信息，例如特徵的配置設置、單位、格式等。描述符通常用於特徵的讀取和寫入操作之外的其他操作。
 */
/*
BluetoothGattService 代表一個 BLE 服務，它可以提供一個或多個特性。服務通常用於表示一個功能或設備的組件。例如，一個心率監測器可能會有一個名為 "Heart Rate" 的服務，它可以提供一個名為 "Heart Rate Measurement" 的特性。

BluetoothGattCharacteristic 代表一個 BLE 特性，它可以用來存儲數據或提供一個功能。特性通常用於表示一個設備的狀態或配置。例如，一個心率監測器可能會有一個名為 "Heart Rate Measurement" 的特性，它可以用來存儲心率數據。

BluetoothGattDescriptor 代表一個 BLE 描述符，它可以用來提供有關特性的附加信息。描述符通常用於配置特性的行為或提供有關特性的額外信息。例如，一個心率監測器可能會有一個名為 "Client Characteristic Configuration" 的描述符，它可以用來配置客戶端是否可以讀取或寫入特性。
 */
fun BluetoothGattService.toBleDeviceService(): BleDeviceService {
    return BleDeviceService(
        uuid = uuid,
        name = AllGattServices.lookup(uuid),
        characteristics = characteristics.map { it.toBleDeviceCharacteristic() },
        service = this
    )
}

fun BluetoothGattCharacteristic.toBleDeviceCharacteristic(): BleDeviceCharacteristic {
    val aaa = this.value
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