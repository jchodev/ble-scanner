package com.jerry.blescanner.features.bluetooth.presentation.components.connect

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceService

@Composable
fun BleConnectTable(
    bleDeviceService : BleDeviceService
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        //title
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bleDeviceService.name,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = bleDeviceService.uuid.toString(),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            bleDeviceService.characteristics.forEachIndexed { index, char->

                ExpandableCharacteristicCard(
                    bleDeviceCharacteristic = char
                )
                Spacer(modifier = Modifier.height(10.dp))

            }
        }

    }


}