package com.jerry.blescanner.features.bluetooth.presentation.components.connect


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Modifier

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceService
import com.jerry.blescanner.features.bluetooth.presentation.viewmodel.BluetoothConnectViewModel
import com.jerry.blescanner.jetpack_design_lib.common.loading.CommonLoading

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BleConnectPage(
    address: String,
    bluetoothConnectViewModel: BluetoothConnectViewModel
) {
    val serviceListState = bluetoothConnectViewModel.serviceListState.collectAsStateWithLifecycle()
    val connectionState = bluetoothConnectViewModel.connectionState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = address){
       bluetoothConnectViewModel.connectMe(address)
    }
    DisposableEffect(Unit){
        onDispose {
            bluetoothConnectViewModel.disconnect()
        }
    }

    val statue = if (connectionState.value == 0){
        "Connected"
    } else {
        "Connecting..."
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //connection
        Text(text = "Connected Statue: ${statue}")

        //UI
        if (serviceListState.value?.isNotEmpty() == true){
            BleConnectServiceViewPager(
                serviceListState = serviceListState
            )
        }
        else {
            CommonLoading()
        }
    }
}


@Composable
fun ServiceItem(
    bleDeviceService: BleDeviceService
) {
    Column {
        Text(text = "Service: ${bleDeviceService.name} (${bleDeviceService.uuid}" )
    }

    bleDeviceService.characteristics.forEach { char->
        Column {
            Text(text = " Characteristic: ${char.name} (${char.uuid}" )
        }
        char.bleDeviceDescriptors.forEach {bleDeviceDescriptor ->
            Column {
                Text(text = "  DeviceDescriptor: ${bleDeviceDescriptor.name} (${bleDeviceDescriptor.uuid}" )
            }
        }
    }
}