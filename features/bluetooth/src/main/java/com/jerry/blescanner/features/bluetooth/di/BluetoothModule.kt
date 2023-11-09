package com.jerry.blescanner.features.bluetooth.di


import com.jerry.blescanner.features.bluetooth.domain.ScanSettingsWrapper
import com.jerry.blescanner.features.bluetooth.domain.ScanSettingsWrapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

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

}