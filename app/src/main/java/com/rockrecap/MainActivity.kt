package com.rockrecap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rockrecap.data.ObserveAsEvents
import com.rockrecap.data.SnackbarController
import com.rockrecap.ui.pages.RockRecapContent
import com.rockrecap.ui.theme.RockRecapTheme
import com.rockrecap.ui.theme.White
import com.rockrecap.ui.AppViewModelProvider
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // this allows us to not have to use the inner padding parameter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RockRecapTheme {
                val snackbarHostState = remember {
                    SnackbarHostState()
                }
                val scope = rememberCoroutineScope()
                ObserveAsEvents(
                    flow = SnackbarController.events,
                    snackbarHostState
                ){ event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()

                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = SnackbarDuration.Short
                        )

                        if(result == SnackbarResult.ActionPerformed){
                            event.action?.action?.invoke()
                        }
                    }
                }
                Scaffold(
                    snackbarHost = { SnackbarHost(
                        hostState = snackbarHostState
                    ) },
                    containerColor = White,
                    modifier = Modifier
                    .fillMaxSize()
                ) {
                    val navController = rememberNavController()
//                    classTestFunction() // will test the functionality of the object used to store statistical data in the users stats page
                    RockRecapContent(navController, viewModel(factory = AppViewModelProvider.Factory))
                }
            }
        }
    }
}