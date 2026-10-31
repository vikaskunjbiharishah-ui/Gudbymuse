package `in`.growurdhan.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import `in`.growurdhan.app.domain.UnconnectedWealthSummaryRepository
import `in`.growurdhan.app.ui.home.HomeScreen
import `in`.growurdhan.app.ui.home.HomeViewModel
import `in`.growurdhan.app.ui.onboarding.OnboardingScreen
import `in`.growurdhan.app.ui.theme.GudComingSoonScreen

/**
 * The only navigation in the app. Screens do not navigate by hand-rolled
 * state. The back stack belongs to the Navigation component, so the
 * system back button, deep links, and saved state behave normally.
 */
data class GudDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

val GudMainDestinations = listOf(
    GudDestination("home", "Home", Icons.Filled.Home),
    GudDestination("holdings", "Holdings", Icons.Filled.PieChart),
    GudDestination("statements", "Statements", Icons.Filled.Description),
    GudDestination("insights", "Insights", Icons.Filled.TrendingUp),
    GudDestination("family", "Family", Icons.Filled.Group),
)

@Composable
fun GudNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = "onboarding",
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in GudMainDestinations.map { it.route }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    GudMainDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = null,
                                )
                            },
                            label = { Text(destination.label) },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable("onboarding") {
                OnboardingScreen(
                    onFinished = {
                        navController.navigate("home") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    },
                )
            }
            composable("home") {
                val homeViewModel: HomeViewModel = viewModel(
                    factory = HomeViewModel.factory(
                        UnconnectedWealthSummaryRepository(),
                    ),
                )
                HomeScreen(homeViewModel)
            }
            composable("holdings") {
                GudComingSoonScreen("Holdings")
            }
            composable("statements") {
                GudComingSoonScreen("Statements")
            }
            composable("insights") {
                GudComingSoonScreen("Insights")
            }
            composable("family") {
                GudComingSoonScreen("Family")
            }
        }
    }
}
