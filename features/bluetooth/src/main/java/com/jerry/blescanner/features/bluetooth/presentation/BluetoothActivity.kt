package com.jerry.blescanner.features.bluetooth.presentation


import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jerry.blescanner.features.bluetooth.presentation.components.connect.BleConnectPage
import com.jerry.blescanner.features.bluetooth.presentation.components.BleScanPage
import com.jerry.blescanner.features.bluetooth.presentation.mvi.BluetoothPageIntent
import com.jerry.blescanner.features.bluetooth.presentation.viewmodel.BluetoothConnectViewModel
import com.jerry.blescanner.features.bluetooth.presentation.viewmodel.BluetoothViewModel
import com.jerry.blescanner.features.bluetooth.presentation.viewmodel.YourViewModel
import com.jerry.blescanner.features.bluetooth.utils.BluetoothStopSource
import com.jerry.blescanner.jetpack_design_lib.theme.MyTheme
import com.polidea.rxandroidble3.RxBleClient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


const val ADDRESS: String = "address"
const val CONNECT_URL: String = "%s/?%s=%s"
@AndroidEntryPoint
class BluetoothActivity : ComponentActivity() {

    enum class Route {
        SCAN, CONNECT
    }

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var rxBleClient : RxBleClient
    private lateinit var navController: NavHostController

    private val viewModel by viewModels<BluetoothViewModel>()
    private val bluetoothConnectViewModel by viewModels<BluetoothConnectViewModel>()

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

    private val connect: (String) -> Unit ={

        viewModel.sendIntent(BluetoothPageIntent.StopScan(BluetoothStopSource.CLICK_CONNECT))

        navController.navigate(
            getConnectRoute(address = it)
        )

    }

    private val disconnect: () -> Unit ={
        //val ble = BLE(componentActivity = this)
        bluetoothConnectViewModel.disconnect()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxBleClient = RxBleClient.create(this)

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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                            title = {
                                Text("BLE Scan", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        )
                    }
                ) {

                    navController = rememberNavController()

                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = Route.SCAN.toString()
                    ){
                        //scan page
                        composable(
                            route = Route.SCAN.toString()
                        ) {
                            BleScanPage(
                                viewModel = viewModel,
                                connect = connect,
                                disconnect = disconnect,
                                onPermissionsGranted = onPermissionGranted,
                                permissionsNonGranted = permissionsNonGranted,
                                clickSearch = startScanBluetooth,
                                stopScan = stopScanBluetooth
                            )
                        }

                        //connect page
                        composable(
                            route = getConnectRoute(address = "{address}"),
                            arguments = listOf(
                                navArgument(ADDRESS) {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val address = backStackEntry.arguments?.getString(ADDRESS) ?: ""
                            BleConnectPage(
                                address = address,
                                bluetoothConnectViewModel = bluetoothConnectViewModel
                            )
                        }
                    }
                }
            }
        }
    }


    private fun getConnectRoute(address: String): String{
        return String.format(
            CONNECT_URL,
            Route.CONNECT.toString(),
            ADDRESS,
            address
        )
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