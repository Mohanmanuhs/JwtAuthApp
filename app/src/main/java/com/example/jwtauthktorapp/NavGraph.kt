package com.example.jwtauthktorapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jwtauthktorapp.ui.AuthScreen
import com.example.jwtauthktorapp.ui.SecretScreen

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            AuthScreen(navController = navController)
        }
        composable(Screen.Secret.route) {
            SecretScreen()
        }
    }
}