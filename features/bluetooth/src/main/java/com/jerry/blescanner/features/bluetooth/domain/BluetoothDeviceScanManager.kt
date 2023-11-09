package com.jerry.blescanner.features.bluetooth.domain

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

//ref: https://github.com/MatthiasKerat/BLETutorialYt/blob/FinalApp/app/src/main/java/com/example/bletutorial/data/ble/TemperatureAndHumidityBLEReceiveManager.kt
class BluetoothDeviceScanManager @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val scanSettingsWrapper: ScanSettingsWrapper
) {
    private val scanSettings = scanSettingsWrapper
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private val _scannedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val scannedDevices: StateFlow<List<BluetoothDevice>>
        get() = _scannedDevices.asStateFlow()


    private val _finishScan = MutableStateFlow<Boolean>(false)
    val finishScan: StateFlow<Boolean>
        get() = _finishScan.asStateFlow()

    val timer = object: CountDownTimer(5000, 1000) {
        override fun onTick(p0: Long) {
            //TODO("Not yet implemented")
        }

        override fun onFinish() {
            _finishScan.value = true
            stopScan()
        }
    }

    val scanCallback = object : ScanCallback() {

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
            timer.cancel()
            bluetoothAdapter.bluetoothLeScanner?.stopScan(scanCallback)
        } catch (e: Exception){
            //
        }
    }

    @SuppressLint("MissingPermission")
    private fun addScanResult(result: ScanResult) {

        if (scannedDevices.value.none { it.address == result.device.address }){
            Timber.d(
                "BluetoothDeviceScanManager::addScanResult::${result}"
            )
            _finishScan.value = false
            timer.cancel()
            timer.start()

            _scannedDevices.value = _scannedDevices.value.plus(
                result.device
            )
        }
    }

}