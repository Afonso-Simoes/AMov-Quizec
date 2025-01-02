package pt.isec.amov.quizectpamov.ui.screens

import pt.isec.amov.quizectpamov.viewmodel.UserViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val userViewModel = UserViewModel(); //VERIFICAR SE PRECISO INSTANCIA PARA OUTRAS SE NAO FOR CIRAR UMA UNICA
        //ON DEMAND

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            MainScreen(navController, userViewModel)
        }
        composable("signup") {
            SignUpScreen(navController, userViewModel)
        }
        composable("mainMenu") {
            MainMenuScreen(navController, userViewModel)
        }
        composable("quizzesMenu") {
            QuizzesMenuScreen(navController,userViewModel)
        }
        composable("questionsMenu") {
            QuestionsMenuScreen(navController, userViewModel)
        }
        composable("startQuiz") {
            StartQuizScreen(navController, userViewModel)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
        composable("credits") {
            CreditsScreen()
        }
    }
}
