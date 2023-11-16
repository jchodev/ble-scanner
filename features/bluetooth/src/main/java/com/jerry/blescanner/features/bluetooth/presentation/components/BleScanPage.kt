package com.jerry.blescanner.features.bluetooth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jerry.blescanner.features.bluetooth.presentation.state.BottomViewState
import com.jerry.blescanner.features.bluetooth.presentation.viewmodel.BluetoothViewModel
import com.jerry.blescanner.features.bluetooth.utils.BluetoothStopSource

@Composable
fun BleScanPage(
    viewModel: BluetoothViewModel,
    connect: (String) -> Unit,
    disconnect: () -> Unit,
    onPermissionsGranted: () -> Unit,
    permissionsNonGranted: () -> Unit,
    clickSearch:() -> Unit,
    stopScan:(BluetoothStopSource) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //StandardBottomSheetM3()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f, true)
                .background(Color.White)
        ) {
            DevicesList(
                devicesState = viewModel.scannedDevicesListState.collectAsStateWithLifecycle(),
                connect = connect,
                disconnect = disconnect
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f, true)
                .background(Color.LightGray,  RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            BottomAreaCompose(
                onPermissionsGranted = onPermissionsGranted,
                permissionsNonGranted = permissionsNonGranted,
                clickSearch = clickSearch,
                bottomViewState = viewModel.bottomViewState.collectAsStateWithLifecycle(),
                stopScan = stopScan,
            )
        }
    }
}