package com.homeapps.translator.common.navigation

sealed class AppRoute(val routeName: String) {
    data object Home: AppRoute(routeName = "home")
    data object Favourite: AppRoute(routeName = "favourite")
}