package com.jerry.blescanner.features.bluetooth.presentation.mvi

import com.jerry.blescanner.basemodule.presentation.mvi.MviAction
import com.jerry.blescanner.basemodule.presentation.mvi.MviIntent

//intent
sealed class BluetoothPageIntent: MviIntent {
    class SetBluetoothEnabled(val value: Boolean): BluetoothPageIntent()
    class SetPermissionGranted(val value: Boolean): BluetoothPageIntent()

    object StartScan: BluetoothPageIntent()
    object StopScan: BluetoothPageIntent()
}

//action
sealed class BluetoothPageAction: MviAction {
    class SetBluetoothEnabled(val value: Boolean): BluetoothPageAction()
    class SetPermissionGranted(val value: Boolean): BluetoothPageAction()

    object StartScan: BluetoothPageAction()
    object StopScan: BluetoothPageAction()
}