package com.jerry.blescanner.features.bluetooth.domain

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.Context
import androidx.compose.runtime.MutableIntState
import com.jerry.blescanner.basemodule.presentation.UiDataState
import com.jerry.blescanner.features.bluetooth.data.mapper.toBleDeviceService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

//https://medium.com/@martijn.van.welie/making-android-ble-work-part-2-47a3cdaade07
//https://stackoverflow.com/questions/25330938/android-bluetoothgatt-status-133-register-callback
@SuppressLint("MissingPermission")
class BleConnectManager @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val context: Context
) {

    private var bluetoothGatt: BluetoothGatt? = null

    //UI state
    private val _uiState = MutableStateFlow<UiDataState<List<BleDeviceService>>>(UiDataState.Initial)
    val uiState : StateFlow<UiDataState<List<BleDeviceService>>?>
        get() = _uiState.asStateFlow()

    private val _connectionState = MutableStateFlow<Int?>(null)
    val connectionState: StateFlow<Int?>
        get() = _connectionState.asStateFlow()

//    private val _deviceConnectionState = MutableStateFlow<HashMap<String, Boolean>>(hashMapOf())
//    val deviceConnectionState: StateFlow<HashMap<String, Boolean>>
//        get() = _deviceConnectionState.asStateFlow()

//    private val _bleDeviceServicesState = MutableStateFlow<List<BleDeviceService>>(emptyList())
//    val bleDeviceServicesState : StateFlow<List<BleDeviceService>>
//        get() = _bleDeviceServicesState.asStateFlow()

    private val bluetoothGattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            bluetoothGatt = gatt
            Timber.d("onConnectionStateChange::status: $status")
            _connectionState.value = status
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    //connectMessage.value = ConnectionState.CONNECTED
                    bluetoothGatt?.discoverServices()
                   // _connectionState.value = true
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    // We successfully disconnected on our own request
                   // _connectionState.value = false
                }
                else -> {
                   // _connectionState.value = false
                }
            }

            /*
                        when (newState) {
                BluetoothProfile.STATE_CONNECTING -> connectMessage.value =
                    ConnectionState.CONNECTING

                BluetoothProfile.STATE_CONNECTED -> {
                    connectMessage.value = ConnectionState.CONNECTED
                    btGatt?.discoverServices()
                }

                BluetoothProfile.STATE_DISCONNECTING -> connectMessage.value =
                    ConnectionState.DISCONNECTING

                BluetoothProfile.STATE_DISCONNECTED -> connectMessage.value =
                    ConnectionState.DISCONNECTED

                else -> connectMessage.value = ConnectionState.DISCONNECTED
            }
             */
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)

            gatt?.let {
                bluetoothGatt = gatt
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    _uiState.value = UiDataState.Success(
                        gatt.services.map {
                            it.toBleDeviceService()
                        }
                    )
//                    gatt.services?.forEach { gattService ->
//                        Timber.d("BleConnectManager::service::uuid::${gattService.uuid}::${AllGattServices.lookup(gattService.uuid)} ")
//
//                        gattService.characteristics.forEach {char->
//                            Timber.d("BleConnectManager::service::uuid::${gattService.uuid}::char::${char.uuid}::${AllGattCharacteristics.lookup(char.uuid)} ")
//
//                            char.descriptors?.forEach {descriptor->
//                                Timber.d("BleConnectManager::service::" +
//                                    "uuid::${gattService.uuid}::char::${char.uuid}::" +
//                                    "descriptor uuid::${descriptor.uuid}::" +
//                                    "descriptor ::${AllGattDescriptors.lookup(descriptor.uuid)}::"
//                                )
//                            }
//                        }
  //                  }
                }
            }
        }
    }


    fun connect(address: String){
        if (bluetoothAdapter.isEnabled) {
            try {
                val device = bluetoothAdapter.getRemoteDevice(address)
                device.connectGatt(context, false, bluetoothGattCallback, TRANSPORT_LE)
            } catch (e: Exception){

            }
        } else {
            _uiState.value = UiDataState.Failure("bluetoothAdapter is not enabled")
        }
    }

    fun disconnect(){
        try {
            _uiState.value = UiDataState.Initial
            _connectionState.value = null
            bluetoothGatt?.let { gatt ->
                gatt.disconnect()
                gatt.close()
                bluetoothGatt = null
            }
        } catch (e: Exception) {
            Timber.tag("BTGATT_CLOSE").e(e)
        } finally {
            bluetoothGatt = null
        }
    }

}