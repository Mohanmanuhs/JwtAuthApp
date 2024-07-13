package com.example.jwtauthktorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.jwtauthktorapp.ui.theme.JWTAuthKtorAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JWTAuthKtorAppTheme {
                val navController = rememberNavController()
                SetUpNavGraph(navController = navController)
            }
        }
    }
}