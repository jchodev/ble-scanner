package com.jerry.blescanner.features.bluetooth.presentation.state

sealed class BottomViewState {
    object NoPermission : BottomViewState()
    object GrantedPermission : BottomViewState()
    object Scanning : BottomViewState()
}