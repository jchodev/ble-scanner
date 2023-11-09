package com.jerry.blescanner.features.bluetooth.presentation.components

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.jerry.blescanner.features.bluetooth.utils.BluetoothStopSource


@Composable
fun ScanningCompose(
    modifier : Modifier = Modifier,
    stopScan:(BluetoothStopSource) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(text="Scanning ...")
        Spacer(modifier = Modifier.height(10.dp))
        Button (onClick = {
            stopScan(BluetoothStopSource.USER_CLICK_CANCEL)
        }){
            Text(text="Stop Scan")
        }
    }
}


@Preview
@Composable
private fun ScanningComposePreview() {
    ScanningCompose(
        stopScan = {}
    )
}