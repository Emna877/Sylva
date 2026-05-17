package com.example.sylva.ui.navigation

sealed class SylvaDestination(val route: String) {
    data object Splash : SylvaDestination("splash")
    data object Home : SylvaDestination("home")
    data object Camera : SylvaDestination("camera")
    data object Gallery : SylvaDestination("gallery")
    data object Loading : SylvaDestination("loading")
    data object TreeDetails : SylvaDestination("details")
    data object History : SylvaDestination("history")
}

