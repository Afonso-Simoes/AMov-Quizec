package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.data.model.Question
import pt.isec.amov.quizectpamov.utils.enums.QuestionType
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel

@Composable
fun StartQuizScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    ShowStartQuizScreen(navController, isLandscape = isLandscape)
}

@Composable
fun ShowStartQuizScreen(navController: NavHostController, isLandscape: Boolean) {
    val gameCode = remember { mutableStateOf("") }
    val questionViewModel: QuestionViewModel = viewModel()
    val questions = questionViewModel.getExampleQuestions()
    var startQuizState by remember { mutableStateOf(false) }

    val titlePadding = if (isLandscape) 32.dp else 16.dp
    val inputPadding = if (isLandscape) 32.dp else 16.dp

    if (startQuizState) {
        StartQuiz(navController, questions, 5)
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.start_quizz),
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = titlePadding)
            )

            TextField(
                value = gameCode.value,
                onValueChange = { newCode -> gameCode.value = newCode },
                label = { Text(stringResource(id = R.string.enter_game_code)) },
                modifier = Modifier
                    .padding(bottom = inputPadding)
                    .let {
                        if (isLandscape) it.width(300.dp)
                        else it
                    }
            )

            val buttonModifier = if (isLandscape) {
                Modifier
                    .padding(top = 16.dp)
                    .width(200.dp)
                    .height(40.dp)
            } else {
                Modifier
                    .padding(top = 16.dp)
            }

            Button(
                onClick = {
                    if (gameCode.value == "1") {
                        startQuizState = true
                    } else {
                        // TODO: Mostar mensagem de erro
                    }
                },
                modifier = buttonModifier
            ) {
                Text(text = stringResource(id = R.string.start_quizz), fontSize = 16.sp)
            }

        }
    }
}

@Composable
fun StartQuiz(navController: NavHostController, questions: List<Question>, timePerQuestion: Int) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(currentIndex) {
        if (currentIndex < questions.size) {
            val currentQuestion = questions[currentIndex]
            println("Navigating to question: ${currentQuestion.id} of type: ${currentQuestion.questionType}")
            when (currentQuestion.questionType) {
                QuestionType.TRUE_FALSE -> navController.navigate("truefalse/${currentQuestion.id}/$timePerQuestion")
                QuestionType.SINGLE_CHOICE -> navController.navigate("singlechoice/${currentQuestion.id}/$timePerQuestion")
                QuestionType.MULTIPLE_CHOICE -> navController.navigate("multiplechoice/${currentQuestion.id}/$timePerQuestion")
                QuestionType.MATCHING -> navController.navigate("matching/${currentQuestion.id}/$timePerQuestion")
                QuestionType.ORDERING -> navController.navigate("ordering/${currentQuestion.id}/$timePerQuestion")
                QuestionType.FILL_IN_THE_BLANK -> navController.navigate("fillintheblank/${currentQuestion.id}/$timePerQuestion")
                QuestionType.ASSOCIATION -> navController.navigate("association/${currentQuestion.id}/$timePerQuestion")
                QuestionType.WORD_BASED -> navController.navigate("wordbased/${currentQuestion.id}/$timePerQuestion")
            }
            //TODO: Isto nao funciona idk why nao lhe aptece ir para a proxima pergunta
            delay(1000L)
            currentIndex++
            println("Current index after increment: $currentIndex")
        } else {
            println("All questions navigated.")
        }
    }

    if (currentIndex >= questions.size) {
        Text(
            text = "Quiz finished!",
            modifier = Modifier.fillMaxSize(),
            fontSize = 24.sp
        )
    }
}

fun getQuestionById(questionViewModel: QuestionViewModel, questionId: Int): Question? {
    return questionViewModel.getExampleQuestions().find { it.id == questionId.toString() }
}
