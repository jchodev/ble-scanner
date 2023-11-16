package com.jerry.blescanner.features.bluetooth.presentation.mvi

import com.jerry.blescanner.basemodule.presentation.mvi.MviAction
import com.jerry.blescanner.basemodule.presentation.mvi.MviIntent
import com.jerry.blescanner.features.bluetooth.utils.BluetoothStopSource

//intent
sealed class BluetoothConnectPageIntent: MviIntent {
//    class SetBluetoothEnabled(val value: Boolean): BluetoothPageIntent()
//    class SetPermissionGranted(val value: Boolean): BluetoothPageIntent()
//
//    object StartScan: BluetoothPageIntent()
//    class StopScan(val source: BluetoothStopSource): BluetoothPageIntent()
}

//action
sealed class BluetoothConnectPageAction: MviAction {
//    class SetBluetoothEnabled(val value: Boolean): BluetoothPageAction()
//    class SetPermissionGranted(val value: Boolean): BluetoothPageAction()
//
//    object StartScan: BluetoothPageAction()
//    object StopScan: BluetoothPageAction()
}