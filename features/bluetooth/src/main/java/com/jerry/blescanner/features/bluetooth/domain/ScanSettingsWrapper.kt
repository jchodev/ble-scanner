package com.jerry.blescanner.features.bluetooth.domain

import android.bluetooth.le.ScanSettings

interface ScanSettingsWrapper {
    fun setScanMode(mode: Int): ScanSettingsWrapper
    // Add other necessary methods from ScanSettings.Builder
    fun build(): ScanSettings
}