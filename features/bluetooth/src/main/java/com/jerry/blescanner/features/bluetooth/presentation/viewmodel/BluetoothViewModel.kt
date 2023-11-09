package com.jerry.blescanner.features.bluetooth.presentation.viewmodel

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.viewModelScope
import com.jerry.blescanner.basemodule.presentation.mvi.BaseViewModel
import com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceScanManager
import com.jerry.blescanner.features.bluetooth.presentation.mvi.BluetoothPageAction
import com.jerry.blescanner.features.bluetooth.presentation.mvi.BluetoothPageIntent
import com.jerry.blescanner.features.bluetooth.presentation.state.BottomViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val bluetoothDeviceScanManager: BluetoothDeviceScanManager
): BaseViewModel<BluetoothPageIntent, BluetoothPageAction>() {

    //Bluetooth Status {user can enabled from setting or somewhere
    private val _bluetoothEnabledState = MutableStateFlow<Boolean>(false)
    val bluetoothEnabledState = _bluetoothEnabledState.asStateFlow()

    //permissions granted status
    private val _premissionsGrantedState = MutableStateFlow<Boolean>(false)
    val premissionsGrantedState = _premissionsGrantedState.asStateFlow()

    //BottomViewState
    private val _bottomViewState = MutableStateFlow<BottomViewState>(BottomViewState.NoPermission)
    val bottomViewState = _bottomViewState.asStateFlow()

    private val _deviceListState = MutableStateFlow<List<BluetoothDevice>?>(null)
    val deviceListState = _deviceListState.asStateFlow()

    override suspend fun handleIntent(intent: BluetoothPageIntent): List<BluetoothPageAction> {
        return when (intent){
            //BluetoothPageAction.SetPermissionGranted(intent.value),
            is BluetoothPageIntent.SetBluetoothEnabled -> {
                val list = listOf(
                    BluetoothPageAction.SetBluetoothEnabled(intent.value),
                )

                if (!intent.value){
                    list.plus(BluetoothPageAction.StopScan)
                }
                list
            }
            is BluetoothPageIntent.SetPermissionGranted -> {
                val list = listOf(
                    BluetoothPageAction.SetPermissionGranted(intent.value),
                )

                if (!intent.value){
                    list.plus(BluetoothPageAction.StopScan)
                }
                list
            }
            is BluetoothPageIntent.StartScan -> listOf(BluetoothPageAction.StartScan)
            is BluetoothPageIntent.StopScan -> listOf(BluetoothPageAction.StopScan)
        }
    }

    override suspend fun handleAction(action: BluetoothPageAction) {
        when (action){
            is BluetoothPageAction.SetBluetoothEnabled -> {
                setBluetoothEnabled(value = action.value)
            }
            is BluetoothPageAction.SetPermissionGranted -> {
                setPremissionsGrantedState(value = action.value)
            }
            is BluetoothPageAction.StartScan -> {
                startScan()
            }
            is BluetoothPageAction.StopScan -> {
                stopScan()
            }
        }
    }

    override suspend fun handleIntentActionsTracker(
        intent: BluetoothPageIntent,
        actions: List<BluetoothPageAction>
    ) {
        //TODO("Not yet implemented")
    }

    private fun startScan() {
        viewModelScope.launch {
            bluetoothDeviceScanManager.scannedDevices.collect {
                Timber.d("BluetoothViewModel::bluetoothDeviceUseCase.scannedDevices.collect ::${it}")
                _deviceListState.value = it
                //_deviceListUIState.value = UiDataState.Success(it)
            }
        }

        viewModelScope.launch {
            bluetoothDeviceScanManager.finishScan.collect {
                Timber.d("BluetoothViewModel::bluetoothDeviceUseCase.scannedDevices.collect ::${it}")
                if (it==true){
                    _bottomViewState.value = BottomViewState.GrantedPermission
                }
                else {
                    _bottomViewState.value = BottomViewState.Scanning
                }
            }
        }
        _deviceListState.value = null
        bluetoothDeviceScanManager.startScan()
    }

    private fun stopScan() {
        Timber.d("BluetoothViewModel::stopScan")
        _deviceListState.value = null
        bluetoothDeviceScanManager.stopScan()
    }

    private fun setBluetoothEnabled(value : Boolean){
        _bluetoothEnabledState.value = value
    }

    private fun setPremissionsGrantedState(value : Boolean){
        _premissionsGrantedState.value = value
        _bottomViewState.value =  if  (value)  BottomViewState.GrantedPermission else  BottomViewState.NoPermission
    }
}