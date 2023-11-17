package com.jerry.blescanner.features.bluetooth.domain

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.util.Log
import com.jerry.blescanner.features.bluetooth.data.mapper.toBleDeviceService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject


//https://medium.com/@martijn.van.welie/making-android-ble-work-part-2-47a3cdaade07

// for getValue was @Deprecated in BluetoothGattCharacteristic
//https://stackoverflow.com/questions/25330938/android-bluetoothgatt-status-133-register-callback


//example got the value from BLE
/*
1
如果要接受 Bluetooth device 的 byebalue，您應該從 BluetoothGattCharacteristic 來取得。BluetoothGattCharacteristic 是用來存儲數據的，而 BluetoothGattService 和 BluetoothGattDescriptor 都是 GATT 模型的組成部分，不直接存儲數據。

具體來說，您可以使用 BluetoothGattCharacteristic 的 getValue() 方法來取得它的值。該方法返回一個 byte[] 數組，其中包含特徵的值。您可以使用該數組來解析數據。

例如，如果您要取得心率監測器的當前心率，您可以使用以下代碼：

BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb"));

int heartRate = characteristic.getValue()[0];

這段代碼會取得心率監測器的 GATT 服務中的 00002a37-0000-1000-8000-00805f9b34fb 特徵。該特徵用來存儲當前心率。代碼會使用 getValue() 方法來取得特徵的值，並將其存儲在 heartRate 變量中。
 */
//https://github.com/marcolivierarsenault/PolarHeartRateApplication/blob/master/app/src/main/java/org/marco45/polarheartmonitor/H7ConnectThread.java

@SuppressLint("MissingPermission")
class BleConnectManager @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val context: Context
) {

    private var bluetoothGatt: BluetoothGatt? = null

    //UI state
    private val _serviceListState = MutableStateFlow<List<BleDeviceService>?>(null)
    val serviceListState :  StateFlow<List<BleDeviceService>?>
        get() = _serviceListState.asStateFlow()

    private val _connectionState = MutableStateFlow<Int?>(null)
    val connectionState: StateFlow<Int?>
        get() = _connectionState.asStateFlow()

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

        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
            }

            val value = characteristic.value
            Timber.d("onCharacteristicRead::status: $value")
        }


        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)

            gatt?.let {
                bluetoothGatt = gatt
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    _serviceListState.value = gatt.services.map {
                        it.toBleDeviceService()
                    }

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
            //TODO error statue
            //_uiState.value = UiDataState.Failure("bluetoothAdapter is not enabled")
        }
    }

    fun disconnect(){
        try {
            _serviceListState.value = emptyList()
           // _uiState.value = UiDataState.Initial
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