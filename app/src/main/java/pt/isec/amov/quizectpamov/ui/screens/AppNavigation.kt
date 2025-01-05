package pt.isec.amov.quizectpamov.ui.screens

import pt.isec.amov.quizectpamov.viewmodel.UserViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.quizectpamov.ui.screens.questions.AssociationScreen
import pt.isec.amov.quizectpamov.ui.screens.questions.FillInTheBlankScreen
import pt.isec.amov.quizectpamov.ui.screens.questions.MatchingScreen
import pt.isec.amov.quizectpamov.ui.screens.questions.MultipleChoiceScreen
import pt.isec.amov.quizectpamov.ui.screens.questions.OrderingScreen
import pt.isec.amov.quizectpamov.ui.screens.questions.SingleChoiceScreen
import pt.isec.amov.quizectpamov.ui.screens.questions.TrueFalseScreen
import pt.isec.amov.quizectpamov.ui.screens.questions.WordBasedScreen
import pt.isec.amov.quizectpamov.ui.screens.reports.GraficsScreen
import pt.isec.amov.quizectpamov.ui.screens.reports.MostUsedAnswersScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val userViewModel =
        UserViewModel() //VERIFICAR SE PRECISO INSTANCIA PARA OUTRAS SE NAO FOR CIRAR UMA UNICA
    //ON DEMAND

    var start = "login";
    if (userViewModel.user != null) {
        start = "mainMenu"
    }

    NavHost(
        navController = navController,
        startDestination = start
    ) {
        composable("login") {
            MainScreen(navController, userViewModel)
        }
        composable("signup") {
            SignUpScreen(navController, userViewModel)
        }
        composable("mainMenu") {
            MainMenuScreen(navController)
        }
        composable("quizzesMenu") {
            QuizzesMenuScreen()
        }
        composable("questionsMenu") {
            QuestionsMenuScreen()
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
            val questionId = backStackEntry.arguments?.getString("questionId")
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    TrueFalseScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(
                                navController,
                                timePerQuestion,
                                indexQuestion + 1,
                                true
                            )
                        },
                    )
                }
            }
        }


        composable("singlechoice/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    SingleChoiceScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(
                                navController,
                                timePerQuestion,
                                indexQuestion + 1,
                                true
                            )
                        },
                    )
                }
            }
        }

        composable("multiplechoice/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()

            if (questionId != null && timePerQuestion != null) {
                if (indexQuestion != null) {
                    MultipleChoiceScreen(
                        questionId = questionId,
                        timePerQuestion = timePerQuestion,
                        indexQuestion = indexQuestion,
                        onNext = {
                            NavigateToQuestion(
                                navController,
                                timePerQuestion,
                                indexQuestion + 1,
                                true
                            )
                        },
                    )
                }
            }
        }

        composable("matching/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
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
                            NavigateToQuestion(
                                navController,
                                timePerQuestion,
                                indexQuestion + 1,
                                true
                            )
                        },
                    )
                }
            }
        }

        composable("ordering/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
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
                            NavigateToQuestion(
                                navController,
                                timePerQuestion,
                                indexQuestion + 1,
                                true
                            )
                        },
                    )
                }
            }
        }

        composable("fillintheblank/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
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
                            NavigateToQuestion(
                                navController,
                                timePerQuestion,
                                indexQuestion + 1,
                                true
                            )
                        },
                    )
                }
            }
        }

        composable("association/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
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
                            NavigateToQuestion(
                                navController,
                                timePerQuestion,
                                indexQuestion + 1,
                                true
                            )
                        },
                    )
                }
            }
        }

        composable("wordbased/{questionId}/{timePerQuestion}/{indexQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
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
                            NavigateToQuestion(
                                navController,
                                timePerQuestion,
                                indexQuestion + 1,
                                true
                            )
                        },
                    )
                }
            }
        }

        composable("grafics/{questionId}/{indexQuestion}/{timePerQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()

            if (questionId != null && indexQuestion != null) {
                if (timePerQuestion != null) {
                    GraficsScreen(
                        questionId = questionId,
                        indexQuestion = indexQuestion,
                        timePerQuestion = timePerQuestion,
                        onNext = {
                            NavigateToQuestion(navController, timePerQuestion, indexQuestion, false)
                        },
                    )
                }
            }
        }
        composable("mostusedanswers/{questionId}/{indexQuestion}/{timePerQuestion}") { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
            val indexQuestion = backStackEntry.arguments?.getString("indexQuestion")?.toIntOrNull()
            val timePerQuestion =
                backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull()

            if (questionId != null && indexQuestion != null) {
                if (timePerQuestion != null) {
                    MostUsedAnswersScreen(
                        questionId = questionId,
                        indexQuestion = indexQuestion,
                        timePerQuestion = timePerQuestion,
                        onNext = {
                            NavigateToQuestion(navController, timePerQuestion, indexQuestion, false)
                        },
                    )
                }
            }
        }
    }
}




