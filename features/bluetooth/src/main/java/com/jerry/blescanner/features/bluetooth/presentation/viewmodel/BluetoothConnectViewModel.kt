package com.jerry.blescanner.features.bluetooth.presentation.viewmodel

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothProfile
import androidx.lifecycle.viewModelScope
import com.jerry.blescanner.basemodule.presentation.UiDataState
import com.jerry.blescanner.basemodule.presentation.mvi.BaseViewModel
import com.jerry.blescanner.features.bluetooth.domain.BleConnectManager
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceService
import com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceScanManager
import com.jerry.blescanner.features.bluetooth.presentation.mvi.BluetoothConnectPageAction
import com.jerry.blescanner.features.bluetooth.presentation.mvi.BluetoothConnectPageIntent
import com.jerry.blescanner.features.bluetooth.presentation.mvi.BluetoothPageAction
import com.jerry.blescanner.features.bluetooth.presentation.mvi.BluetoothPageIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class BluetoothConnectViewModel @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val bleConnectManager: BleConnectManager
): BaseViewModel<BluetoothConnectPageIntent, BluetoothConnectPageAction>() {

    //
    private val _addressState = MutableStateFlow<String>("")
    val addressState = _addressState.asStateFlow()

    //UI state
    private val _uiState = MutableStateFlow<UiDataState<List<BleDeviceService>>?>(null)
    val uiState : StateFlow<UiDataState<List<BleDeviceService>>?>
        get() = _uiState.asStateFlow()

    //connection
    private val _connectionState = MutableStateFlow<Int?>(null)
    val connectionState: StateFlow<Int?>
        get() = _connectionState.asStateFlow()

    override suspend fun handleIntent(intent: BluetoothConnectPageIntent): List<BluetoothConnectPageAction> {
        //TODO("Not yet implemented")
        return listOf()
    }

    override suspend fun handleAction(action: BluetoothConnectPageAction) {
        //TODO("Not yet implemented")
    }

    override suspend fun handleIntentActionsTracker(
        intent: BluetoothConnectPageIntent,
        actions: List<BluetoothConnectPageAction>
    ) {
        //TODO("Not yet implemented")
    }

    @SuppressLint("MissingPermission")
    fun connectMe(address: String){

        _addressState.value = address
       bleConnectManager.connect(address)

        viewModelScope.launch {
            bleConnectManager.uiState.collect{
                _uiState.value = it
            }
        }

        viewModelScope.launch {
            bleConnectManager.connectionState.collect{
                _connectionState.value = it
            }
        }
    }

    fun disconnect(){
        //TODO
        bleConnectManager.disconnect()
    }


}