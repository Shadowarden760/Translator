package com.homeapps.translator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.homeapps.translator.ui.components.BottomBar
import com.homeapps.translator.ui.components.TranslationNavHost
import com.homeapps.translator.ui.theme.TranslatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            TranslatorTheme {
                Scaffold(
                    bottomBar = { BottomBar(navHostController) },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.Companion.fillMaxSize().padding(innerPadding)) {
                        TranslationNavHost(navController = navHostController)
                    }
                }
            }
        }
    }
}
