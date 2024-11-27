package pt.isec.amov.quizectpamov.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            MainScreen(navController)
        }
        composable("signup") {
            SignUpScreen(navController)
        }
//        composable("dashboard") {
//            DashboardScreen()
//        }
    }
}
