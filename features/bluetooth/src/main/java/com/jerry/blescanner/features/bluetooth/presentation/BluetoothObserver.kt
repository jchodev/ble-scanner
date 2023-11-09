package com.jerry.blescanner.features.bluetooth.presentation

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

//this class is work with activity lifecycle
class BluetoothObserver(
    private val activity: ComponentActivity,
    private val bluetoothAdapter: BluetoothAdapter,
    private val stopScan:() -> Unit,
    private val scan:() -> Unit,
    private val bluetoothEnabled:(Boolean) -> Unit
): DefaultLifecycleObserver {

    private val registry: ActivityResultRegistry = activity.activityResultRegistry
    lateinit var btEnableResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        createBroadcastReceiver()
        btEnableResultLauncher = registerHandler(owner, "EnableBLE")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        Timber.d("onPause called")

        try {
            activity.unregisterReceiver(broadcastReceiver)
        } catch (_: Exception) {

        } finally {
            //bleManager.stopScan()
            stopScan()
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        Timber.d("onResume called...")

        ContextCompat.registerReceiver(
            activity, broadcastReceiver,
            IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED),
            ContextCompat.RECEIVER_EXPORTED
        )

        if (!bluetoothAdapter.isEnabled)
            launchEnableBtAdapter()
        //scan()
        //bleManager.scan()
    }

    private fun createBroadcastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {

                // activity.lifecycleScope.launch {
                val action = intent.action
                Timber.d("btadapter changed $action")

                // It means the user has changed their bluetooth state.
                if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                    Timber.d("btadapter state: ${bluetoothAdapter.state} / ${bluetoothAdapter.isEnabled}")
                    if (bluetoothAdapter.state == BluetoothAdapter.STATE_OFF) {
                        //bleManager.isScanning.value = false
                        //bleManager.stopScan()
                        launchEnableBtAdapter()
                        bluetoothEnabled(false)
                    }
                    if (bluetoothAdapter.state == BluetoothAdapter.STATE_ON) {
                        bluetoothEnabled(true)
                        //Timber.d("btadapter back on...")
                        //delay(300L)
                        //bleManager.scan()
                    }
                    //}
                }
            }


        }
    }

    private fun launchEnableBtAdapter() {
        //if (bleManager.scanEnabled) {
        try {
            val btEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            btEnableResultLauncher.launch(btEnableIntent)
        } catch (e: Exception) {
            //Timber.tag("LAUNCH_BT_ENABLE").e(e)
        }
        //}
    }

    private fun registerHandler(owner: LifecycleOwner, key: String) = registry.register(
        key,
        owner,
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            //bleManager.scan()
        }
    }

}