package com.example.composedrawermenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composedrawermenu.ui.Drawer
import com.example.composedrawermenu.ui.theme.ComposeDrawerMenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDrawerMenuTheme {
                Drawer()
            }
        }
    }
}