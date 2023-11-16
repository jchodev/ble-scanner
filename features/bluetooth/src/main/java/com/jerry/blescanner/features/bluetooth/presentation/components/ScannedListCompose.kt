package com.jerry.blescanner.features.bluetooth.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerry.blescanner.features.bluetooth.domain.BluetoothDeviceDomain

@Composable
fun DevicesList(
    modifier : Modifier = Modifier,
    connect: (String) -> Unit,
    disconnect: () -> Unit,
    devicesState: State<List<BluetoothDeviceDomain>?>
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items (devicesState.value ?: emptyList()) {
            DevicesItem(
                device = it,
                connect = connect,
                disconnect = disconnect
            )
        }
    }
}

@Composable
fun DevicesItem(
    modifier : Modifier = Modifier,
    device: BluetoothDeviceDomain,
    connect: (String) -> Unit,
    disconnect: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            Text(text = "Name: ${device.name}")
            Text(text = "Address: ${device.address}")
            Text(text = "Distance: ${device.distance} (Base on signal)")
            Text(text = "RSSI: ${device.rssi}")
            if (device.connected) {
                Button(onClick = { disconnect }) {
                    Text(text = "Discount!")
                }
            }
            else {
                Button(onClick = { connect.invoke(device.address) }) {
                    Text(text = "Connect Me")
                }
            }
        }

    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun DevicesItemPreview(){
    DevicesItem(
        device = BluetoothDeviceDomain(
            name = "this is name",
            address = "this is address",
            distance = "10 m",
            rssi = 10,
        ),
        connect = {},
        disconnect = {}
    )
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun DevicesListPreview(){
    //State<HashMap<String,BluetoothDeviceDomain>?>
    val list = mutableStateOf<List<BluetoothDeviceDomain>?>(
        listOf(
            BluetoothDeviceDomain(
                name = "this is name2",
                address = "this is address",
                distance = "10 m",
                rssi = 10
            ),
            BluetoothDeviceDomain(
                name = "this is name2",
                address = "this is address2",
                distance = "10 m",
                rssi =11
            )
        )
    )


    DevicesList(
        devicesState = list,
        connect = {},
        disconnect = {}
    )
}