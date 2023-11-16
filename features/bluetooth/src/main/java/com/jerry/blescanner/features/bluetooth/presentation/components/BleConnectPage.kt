package com.jerry.blescanner.features.bluetooth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jerry.blescanner.basemodule.presentation.UiDataState
import com.jerry.blescanner.features.bluetooth.presentation.viewmodel.BluetoothConnectViewModel
import com.jerry.blescanner.jetpack_design_lib.common.loading.CommonLoading

@Composable
fun BleConnectPage(
    address: String,
    bluetoothConnectViewModel: BluetoothConnectViewModel
) {
    val uiState = bluetoothConnectViewModel.uiState.collectAsStateWithLifecycle()
    val connectionState = bluetoothConnectViewModel.connectionState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = address){
       bluetoothConnectViewModel.connectMe(address)
    }
    DisposableEffect(Unit){
        onDispose {
            bluetoothConnectViewModel.disconnect()
        }
    }

    Column {
        //connection
        Text(text = connectionState.value?.toString() ?: "null")

        //UI
        when (uiState.value){
            is UiDataState.Loading -> CommonLoading()
            is UiDataState.Success -> Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = uiState.value.toString())
            }
            is UiDataState.Failure -> Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = (uiState.value as UiDataState.Failure).errorAny.toString())
            }

            else -> {}
        }
    }


}