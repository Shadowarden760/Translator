package com.homeapps.translator.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.homeapps.translator.common.navigation.AppRoute
import com.homeapps.translator.ui.features.favourites.FavouritesScreen
import com.homeapps.translator.ui.features.main.MainScreen

@Composable
fun TranslationNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationBarSection.Home.routeName,
    ) {

        composable(AppRoute.Home.routeName) {
            MainScreen()
        }

        composable(AppRoute.Favourite.routeName) {
            FavouritesScreen()
        }
    }
}