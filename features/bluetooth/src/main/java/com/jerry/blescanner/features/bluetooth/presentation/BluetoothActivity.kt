package com.jerry.blescanner.features.bluetooth.presentation

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jerry.blescanner.features.bluetooth.presentation.components.BottomAreaCompose
import com.jerry.blescanner.features.bluetooth.presentation.components.DevicesList
import com.jerry.blescanner.features.bluetooth.presentation.mvi.BluetoothPageIntent
import com.jerry.blescanner.features.bluetooth.presentation.viewmodel.BluetoothViewModel
import com.jerry.blescanner.features.bluetooth.presentation.viewmodel.YourViewModel
import com.jerry.blescanner.features.bluetooth.utils.BluetoothStopSource
import com.jerry.blescanner.jetpack_design_lib.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class BluetoothActivity : ComponentActivity() {

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter


    private val viewModel by viewModels<BluetoothViewModel>()

    //for testing only
    private val yourViewModel by viewModels<YourViewModel>()

    private val onPermissionGranted: () -> Unit = {
        //viewModel.startScan()
        viewModel.sendIntent(BluetoothPageIntent.SetPermissionGranted(true))
    }

    private val permissionsNonGranted: () -> Unit = {
        //viewModel.stopScan()
        viewModel.sendIntent(BluetoothPageIntent.SetPermissionGranted(false))
    }

    private val startScanBluetooth: () -> Unit = {
        Timber.d("BluetoothActivity::startScanBluetooth")
        //viewModel.stopScan()
        viewModel.sendIntent(BluetoothPageIntent.StartScan)
    }

    private val stopScanBluetooth: (BluetoothStopSource) -> Unit = {
        Timber.d("BluetoothActivity::stopScanBluetooth")
        viewModel.sendIntent(BluetoothPageIntent.StopScan(it))
    }

    private val bluetoothEnabled: (Boolean) -> Unit = {
        Timber.d("BluetoothActivity::scannBluetooh")
        viewModel.sendIntent(BluetoothPageIntent.SetBluetoothEnabled(it))
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initIntent()

        val vusionBluetoothObserver = BluetoothObserver(
            activity = this,
            bluetoothAdapter = bluetoothAdapter,
            stopScan = stopScanBluetooth,
            bluetoothEnabled = bluetoothEnabled
        )
        this.lifecycle.addObserver(vusionBluetoothObserver)

//        setContent{
//            DevicesList2()
//        }

        setContent {
            MyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        //StandardBottomSheetM3()
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(7f, true)
                        ) {
                            DevicesList(
                                devicesState = viewModel.scannedDevicesListState.collectAsStateWithLifecycle()
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f, true)
                                .background(Color.Red)
                        ) {
                            BottomAreaCompose(
                                allPermissionsGranted = onPermissionGranted,
                                permissionsNonGranted = permissionsNonGranted,
                                clickSearch = startScanBluetooth,
                                bottomViewState = viewModel.bottomViewState.collectAsStateWithLifecycle(),
                                stopScan = stopScanBluetooth,
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun DevicesList2() {
        val scannedDevicesList by yourViewModel.scannedDevicesListState.collectAsState()

        Column {
            for (device in scannedDevicesList) {
                Text(text = "Name: ${device.name}")
                Text(text = "Address: ${device.address}")
                Text(text = "Distance: ${device.distance}")
                Text(text = "RSSI: ${device.rssi}")

                Button(
                    onClick = {
                        // Update the RSSI value
                        yourViewModel.updateRssi(device, (0..10).random())
                    }
                ) {
                    Text("Increase RSSI")
                }
            }
        }
//
//        LaunchedEffect(scannedDevicesList) {
//            // Observe changes in the scannedDevicesList
//            scannedDevicesList.forEach { device ->
//                // Perform any necessary side effects here
//                println("Device Name: ${device.name}, RSSI: ${device.rssi}")
//            }
//        }
    }
}