package com.jerry.blescanner.features.bluetooth.presentation.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold

import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelBottomSheetM3() {

    var openBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    Button(onClick = {openBottomSheet = true}) {
        Text(text = "show bottom sheet")
    }

    if (openBottomSheet){
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = { openBottomSheet = false },
            dragHandle = {
                //header of bottom sheet
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(text = "this is title", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            }
        ) {
            BottomSheetContent (
                onHideButtonClick = {
                    scope.launch {
                        bottomSheetState.hide()
                    }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            openBottomSheet = false
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardBottomSheetM3(){
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        //default bottom sheet height
        sheetPeekHeight = 128.dp,
        sheetContent = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Swipe up to expend Sheet")
            }
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "This is sheet content")  
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }){
                    Text(text = "Click to collapse sheet")
                }
            }
        }
    ) {
        paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            //Text(text = "Scaffold Content")
            Button(onClick = {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }) {
                Text( text = "open")
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    onHideButtonClick : () -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(5){
            ListItem(
                headlineContent = {
                    Text(text = "Item ${it}")
                },
                leadingContent = {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                }
            )
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onHideButtonClick() }
            ) {
                Text(text = "Hide")
            }
        }
    }

}