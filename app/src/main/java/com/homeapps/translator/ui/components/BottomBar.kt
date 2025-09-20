package com.homeapps.translator.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navHostController: NavHostController) {
    val backStackEntry = navHostController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination
    NavigationBar {
        NavigationBarSection.sections.forEach { section ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == section.routeName
            } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(section.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(section.title),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = selected,
                onClick = {
                    navHostController.navigate(section.routeName) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}