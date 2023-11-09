package com.jerry.blescanner.features.bluetooth.domain

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import com.jerry.blescanner.features.bluetooth.data.mapper.toBluetoothDeviceDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

//ref: https://github.com/MatthiasKerat/BLETutorialYt/blob/FinalApp/app/src/main/java/com/example/bletutorial/data/ble/TemperatureAndHumidityBLEReceiveManager.kt
class BluetoothDeviceScanManager @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val scanSettingsWrapper: ScanSettingsWrapper
) {
    private val scanSettings = scanSettingsWrapper
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private val _scannedDeviceState = MutableStateFlow<BluetoothDeviceDomain?>(null)
    val scannedDeviceState: StateFlow<BluetoothDeviceDomain?>
        get() = _scannedDeviceState.asStateFlow()



    private val _scanningState = MutableStateFlow<Boolean>(false)
    val scanningState: StateFlow<Boolean>
        get() = _scanningState.asStateFlow()

    private val scanCallback = object : ScanCallback() {

        override fun onScanFailed(errorCode: Int) {
            Timber.d("com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceUseCase::onScanFailed::errorCode::${errorCode}")
            if (errorCode == 1){
                stopScan()
                startScan()
            }
        }

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Timber.d(
                "com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceUseCase::onScanResult::callbackType::${callbackType}"+
                        "::result::${result}"
            )
            _scanningState.value = true
            addScanResult(result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            Timber.d(
                "shop.vusion.features.bluetooth.domain.usecase.com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceUseCase::onBatchScanResults::results::${results}"
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan() {
        try {
            bluetoothAdapter.bluetoothLeScanner?.startScan(null, scanSettings, scanCallback)
        } catch (e: Exception){
            //
        }
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        try {
            _scanningState.value = false
            bluetoothAdapter.bluetoothLeScanner?.stopScan(scanCallback)
        } catch (e: Exception){
            //
        }
    }

    @SuppressLint("MissingPermission")
    private fun addScanResult(result: ScanResult) {
        val rssi = result.rssi

        Timber.d( "BluetoothDeviceScanManager::addScanResult:: ${result.device.name}, " +
                "Address::${result.device.address}"+
                ", RSSI: ${result.rssi}")

        _scannedDeviceState.value = result.device.toBluetoothDeviceDomain(rssi)
    }


}