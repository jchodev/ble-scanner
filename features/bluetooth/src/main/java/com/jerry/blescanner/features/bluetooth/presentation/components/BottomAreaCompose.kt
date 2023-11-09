package com.jerry.blescanner.features.bluetooth.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jerry.blescanner.features.bluetooth.presentation.state.BottomViewState
import com.jerry.blescanner.features.bluetooth.utils.permissionsBleScanList

/*
    There have two status:
    1. No permission -> show requirePermissionCompose
    2. Button to allow user click to start scanner
    3. Scanning -> ....
    4. Scanning completed
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun BottomAreaCompose(
    modifier: Modifier = Modifier,
    allPermissionsGranted: () -> Unit,
    permissionsNonGranted: () -> Unit,
    clickSearch:() -> Unit,
    bottomViewState: State<BottomViewState>
//    deviceListUIState: State<UiDataState<List<BluetoothDeviceDomain>>?>
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissionsBleScanList)

    LaunchedEffect(key1 = multiplePermissionsState.allPermissionsGranted) {
        //Permission Granted from user or not
        if (multiplePermissionsState.allPermissionsGranted) {
            allPermissionsGranted()
        }
        else {
            permissionsNonGranted()
        }
    }

    when (bottomViewState.value){
        is BottomViewState.NoPermission -> {
            RequirePermissionsCompose(multiplePermissionsState = multiplePermissionsState)
        }
        is BottomViewState.GrantedPermission -> {
            AllowScannerCompose(clickSearch = clickSearch)
        }
        is BottomViewState.Scanning -> {
            //Text(text = "Scanning")
            ScanningCompose()

        }
    }
}