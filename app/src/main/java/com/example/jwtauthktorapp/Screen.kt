package com.example.jwtauthktorapp

sealed class Screen(val route:String) {
    data object Secret:Screen("secret_screen")
    data object Main:Screen("main_screen")
}