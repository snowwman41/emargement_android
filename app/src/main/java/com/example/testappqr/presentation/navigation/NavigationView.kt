package com.example.testappqr.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.testappqr.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationView(
    navController: NavHostController,
    showBackButton: Boolean = false,
    title: String = "",
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
//                val cookieManager = CookieManager.getInstance()
//                cookieManager.removeAllCookies(null)
//                cookieManager.flush()
//                navController.navigate("login")
            }, containerColor = MaterialTheme.colorScheme.secondary) {
                Icon(Icons.Default.Lock, contentDescription = "Disconnect")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(title) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = actions
            )
        }, bottomBar = {
            BottomBar(navController)

        }
    ) { paddingValues ->
        Box(modifier = modifier.padding(paddingValues)) {

            content()
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.Home, "")
        },
            label = { Text(text = "Home") }, selected = currentRoute == Routes.PROFESSOR_SESSIONS,
            onClick = {
                navController.navigate(Routes.PROFESSOR_SESSIONS) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }

            })
        NavigationBarItem(icon = {
            Icon(imageVector = Icons.AutoMirrored.Filled.List, "")
        },
            label = { Text(text = "Modules") }, selected = currentRoute == Routes.PROFESSOR_MODULES,
            onClick = {
                navController.navigate(Routes.PROFESSOR_MODULES) {
                    // Pop up to the start destination of the graph to avoid building up a stack
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            })
        NavigationBarItem(icon = {
            Icon(painter = painterResource(id = R.drawable.scan_qr_code), "")
        },
            label = { Text(text = "Code") }, selected = currentRoute == Routes.PROFESSOR_CODE,
            onClick = {

                navController.navigate(Routes.PROFESSOR_CODE) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }

            })
    }
}
