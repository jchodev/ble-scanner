package com.jerry.blescanner.features.splash.presentation


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jerry.blescanner.features.bluetooth.presentation.BluetoothActivity
import com.jerry.blescanner.features.splash.presentation.viewmodel.SplashViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isReady != null
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isReady.collectLatest {
                    it?.let {
                        startActivity(Intent(this@SplashActivity, BluetoothActivity::class.java))
                        this@SplashActivity.finish()
                    }

                }
            }
        }
    }
}
