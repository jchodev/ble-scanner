package com.jerry.blescanner.features.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(

): ViewModel() {

    private val _isReady = MutableStateFlow<Boolean?>( null)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _isReady.value = true
        }
    }

}