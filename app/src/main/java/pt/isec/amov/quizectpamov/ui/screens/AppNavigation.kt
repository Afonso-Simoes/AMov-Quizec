package pt.isec.amov.quizectpamov.ui.screens

import pt.isec.amov.quizectpamov.viewmodel.UserViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.quizectpamov.ui.components.Association

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val userViewModel = UserViewModel() //VERIFICAR SE PRECISO INSTANCIA PARA OUTRAS SE NAO FOR CIRAR UMA UNICA
        //ON DEMAND

    NavHost(
        navController = navController,
        startDestination = "mainMenu"
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
            StartQuizScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
        composable("credits") {
            CreditsScreen()
        }
        composable("history") {
            HistoryScreen(navController, userViewModel)
        }
        composable("truefalse/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull()
            val timePerQuestion = backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    TrueFalseScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(navController,timePerQuestion, indexQuestion + 1)
                        },
                    )
                }
            }
        }


        composable("singlechoice/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull()
            val timePerQuestion = backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    SingleChoiceScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(navController,timePerQuestion, indexQuestion + 1)
                        },
                    )
                }
            }
        }

        composable("multiplechoice/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull()
            val timePerQuestion = backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    MultipleChoiceScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(navController,timePerQuestion, indexQuestion + 1)
                        },
                    )
                }
            }
        }

        composable("matching/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull()
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    MatchingScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(navController, timePerQuestion, indexQuestion + 1)
                        },
                    )
                }
            }
        }

        composable("ordering/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull()
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    OrderingScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(navController, timePerQuestion, indexQuestion + 1)
                        },
                    )
                }
            }
        }

        composable("fillintheblank/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull()
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    FillInTheBlankScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(navController, timePerQuestion, indexQuestion + 1)
                        },
                    )
                }
            }
        }

        composable("association/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull()
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    AssociationScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(navController, timePerQuestion, indexQuestion + 1)
                        },
                    )
                }
            }
        }

        composable("wordbased/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull()
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    WordBasedScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(navController, timePerQuestion, indexQuestion + 1)
                        },
                    )
                }
            }
        }

    }
}


