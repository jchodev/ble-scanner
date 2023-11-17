package com.jerry.blescanner.features.bluetooth.presentation.components.connect

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceCharacteristic
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceDescriptor
import com.jerry.blescanner.features.bluetooth.domain.BleDeviceService
import com.jerry.blescanner.jetpack_design_lib.common.indicator.DotsIndicator
import com.jerry.blescanner.jetpack_design_lib.theme.MyTheme
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BleConnectServiceViewPager(
    serviceListState: State<List<BleDeviceService>?>
) {
    serviceListState.value?.let {
        val pageCount = it.size
        val pagerState = rememberPagerState { pageCount }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalPager(state = pagerState) {
                BleConnectTable(
                    bleDeviceService = serviceListState.value!![it]
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            DotsIndicator(
                totalDots = pageCount,
                selectedIndex = pagerState.currentPage,
                selectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unSelectedColor = MaterialTheme.colorScheme.surfaceVariant
            )

        }


    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun BleConnectServiceViewPagerPreview() {
    val listState = mutableStateOf<List<BleDeviceService>?>(null)

    listState.value = listOf(
        BleDeviceService(
            name = "this is name",
            uuid = UUID.fromString("this is UUID"),
            characteristics = listOf(
                BleDeviceCharacteristic(
                    name = "this is char 1",
                    uuid = UUID.fromString("this is char UUID 1"),
                    bleDeviceDescriptors = listOf(
                        BleDeviceDescriptor(
                            name = "this is desc 1",
                            uuid = UUID.fromString("this is desc UUID 1"),
                        )
                    )
                )
            )
        )
    )
    MyTheme {
        BleConnectServiceViewPager(
            serviceListState = listState
        )
    }

}