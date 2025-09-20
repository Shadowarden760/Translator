package com.homeapps.translator.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.homeapps.translator.R
import com.homeapps.translator.common.navigation.AppRoute

sealed class NavigationBarSection(
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int,
    val routeName: String
) {
    data object Home: NavigationBarSection(
        title = R.string.text_home_section,
        icon = R.drawable.ic_home,
        routeName = AppRoute.Home.routeName
    )

    data object Favourites: NavigationBarSection(
        title = R.string.text_favourite_section,
        icon = R.drawable.ic_favourite,
        routeName = AppRoute.Favourite.routeName
    )

    companion object {
        val sections = listOf(Home, Favourites)
    }
}