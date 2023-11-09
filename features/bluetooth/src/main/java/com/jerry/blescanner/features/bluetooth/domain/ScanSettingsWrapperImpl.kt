package com.jerry.blescanner.features.bluetooth.domain

import android.bluetooth.le.ScanSettings
import javax.inject.Inject

class ScanSettingsWrapperImpl @Inject constructor() : ScanSettingsWrapper {
    private val builder = ScanSettings.Builder()

    override fun setScanMode(mode: Int): ScanSettingsWrapper {
        builder.setScanMode(mode)
        return this
    }

    // Implement other necessary methods from ScanSettings.Builder

    override fun build(): ScanSettings {
        return builder.build()
    }
}