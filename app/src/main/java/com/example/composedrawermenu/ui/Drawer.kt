package com.example.composedrawermenu.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composedrawermenu.R
import com.example.composedrawermenu.ui.pages.Account
import com.example.composedrawermenu.ui.pages.Favorite
import com.example.composedrawermenu.ui.pages.Settings
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(){
    val items = listOf(
        DrawerItem(Icons.Default.AccountBox, "Account") { Account() },
        DrawerItem(Icons.Default.Favorite, "Favorite") { Favorite() },
        DrawerItem(Icons.Default.Settings, "Settings") { Settings() }
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember{ mutableStateOf(items[0]) }

    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Image(
                    painter = painterResource(id = R.drawable.ava),
                    contentDescription = "ava",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(10.dp))
                items.forEach{
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = it.image, contentDescription = it.title) },
                        label = { Text(text = it.title) },
                        selected = selectedItem.value == it,
                        onClick = {
                            scope.launch {
                                selectedItem.value = it
                                drawerState.close()
                                navController.navigate(it.title)
                            }
                        }
                    )
                }
            }
    }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "menu",
                            modifier = Modifier.clickable {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                )
            }
        ) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = items[0].title
            ){
                items.forEach{item ->
                    composable(item.title){item.destination()}
                }
            }
        }
    }
}

data class DrawerItem(
    val image: ImageVector,
    val title: String,
    val destination: @Composable () -> Unit
)