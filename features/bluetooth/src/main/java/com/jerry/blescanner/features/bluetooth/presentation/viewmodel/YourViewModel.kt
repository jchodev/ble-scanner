package com.jerry.blescanner.features.bluetooth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceDomain2
import com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceDomain3
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class YourViewModel @Inject constructor(): ViewModel() {
    private val _scannedDevicesListState = MutableStateFlow<List<BluetoothDeviceDomain3>>(emptyList())
    val scannedDevicesListState: StateFlow<List<BluetoothDeviceDomain3>> = _scannedDevicesListState

    init {
        // Example initialization of scanned devices list
        val devicesList = mutableListOf<BluetoothDeviceDomain3>()
        devicesList.add(BluetoothDeviceDomain3("Device 1", "Address 1", "10m", 10))
        devicesList.add(BluetoothDeviceDomain3("Device 2", "Address 2", "5m", 5))
        _scannedDevicesListState.value = devicesList
    }

    fun updateRssi(device: BluetoothDeviceDomain3, newRssi: Int) {

        val updatedList = _scannedDevicesListState.value.toMutableList()

        //NOT WORK!!
//        updatedList.forEach {
//            if (it.name == device.name){
//                it.apply {
//                    rssi = newRssi
//                }
//            }
//        }
//
//        _scannedDevicesListState.value = updatedList


        val deviceIndex = updatedList.indexOfFirst { it.name == device.name }
        if (deviceIndex != -1) {
            updatedList[deviceIndex] = device.copy(rssi = newRssi)
            _scannedDevicesListState.value = updatedList
        }

    }


}