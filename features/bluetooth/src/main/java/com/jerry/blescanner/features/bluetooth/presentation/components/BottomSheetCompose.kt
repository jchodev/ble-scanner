package com.jerry.blescanner.features.bluetooth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jerry.blescanner.jetpack_design_lib.theme.MyTheme
import kotlinx.coroutines.launch


/*
    There have two status:
    1. No permission -> show requirePermissionCompose
    2. Button to allow user click to start scanner
    3. Scanning -> ....
    4. Scanning completed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCompose() {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded  = true)
    val scope = rememberCoroutineScope()

    // Sheet content
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
        ) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Button(
                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                    // you must additionally handle intended state cleanup, if any.
                    onClick = {
                        scope.launch {
                            bottomSheetState.hide()
                        }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                openBottomSheet = false
                            }
                        }
                    }
                ) {
                    Text("Hide Bottom Sheet")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetComposePreview() {
    MyTheme {
        BottomSheetCompose()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetComposePreview2() {
    MyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val sheetState = rememberModalBottomSheetState()
            var isSheetOpen by rememberSaveable {
                mutableStateOf(false)
            }
            val scaffoldState = rememberBottomSheetScaffoldState()
            val scope = rememberCoroutineScope()

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
//                    Image(
//                        painter = painterResource(id = R.drawable.kermit),
//                        contentDescription = null
//                    )
                    Text("this is sheetcontent with columnScrope")
                },
                sheetPeekHeight = 20.dp
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }) {
                        Text(text = "Open sheet")
                    }
                }
            }
        }
    }
}
