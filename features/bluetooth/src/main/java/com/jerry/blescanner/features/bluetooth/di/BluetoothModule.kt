package com.jerry.blescanner.features.bluetooth.di


import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.jerry.blescanner.features.bluetooth.domain.BleConnectManager
import com.jerry.blescanner.features.bluetooth.domain.ScanSettingsWrapper
import com.jerry.blescanner.features.bluetooth.domain.ScanSettingsWrapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {

    @Provides
    @Singleton
    fun provideScanSettingsWrapper(): ScanSettingsWrapper {
        return ScanSettingsWrapperImpl()
    }

    @Provides
    @Singleton
    fun provideBleConnectManager(
        bluetoothAdapter: BluetoothAdapter,
        @ApplicationContext context: Context
    ): BleConnectManager {
        return BleConnectManager(bluetoothAdapter = bluetoothAdapter, context = context)
    }


}