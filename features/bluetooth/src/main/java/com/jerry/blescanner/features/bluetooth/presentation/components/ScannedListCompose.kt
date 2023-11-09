package com.jerry.blescanner.features.bluetooth.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    devicesState: State<List<BluetoothDeviceDomain>?>
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items (devicesState.value ?: emptyList()) {
            DevicesItem(
                device = it
            )
        }
    }
}

@Composable
fun DevicesItem(
    modifier : Modifier = Modifier,
    device: BluetoothDeviceDomain
) {
    OutlinedCard(
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(6f, true)
            ) {
                Text(
                    text = device.name ?: device.address,
                )
            }
            Box(
                modifier = Modifier
                    .weight(2f, true)
            ) {
                Text(
                    text = "Distance: ",
                )
            }
            Row(
                modifier = Modifier
                    .weight(1.5f, true),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = device.rssi.value.toString(),
                )
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
            rssi = mutableStateOf(10)
        )
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
                rssi = mutableStateOf(10)
            ),
            BluetoothDeviceDomain(
                name = "this is name2",
                address = "this is address2",
                distance = "10 m",
                rssi = mutableStateOf(11)
            )
        )
    )


    DevicesList(
        devicesState = list
    )
}