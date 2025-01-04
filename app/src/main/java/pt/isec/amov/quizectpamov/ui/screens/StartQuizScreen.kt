package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.getValue
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
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.data.model.Question
import pt.isec.amov.quizectpamov.utils.enums.QuestionType
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel

@Composable
fun StartQuizScreen(
    navController: NavHostController,
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    ShowStartQuizScreen(navController, isLandscape = isLandscape)
}

@Composable
fun ShowStartQuizScreen(navController: NavHostController, isLandscape: Boolean) {
    val gameCode = remember { mutableStateOf("") }
    var startQuizState by remember { mutableStateOf(false) }

    val titlePadding = if (isLandscape) 32.dp else 16.dp
    val inputPadding = if (isLandscape) 32.dp else 16.dp

    BackHandler {
        navController.navigate("mainMenu")
    }

    if (startQuizState) {
        StartQuiz(navController, 5)
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.start_quizz_title),
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
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.start_quizz), fontSize = 16.sp)
            }

        }
    }
}

@Composable
fun NavigateToQuestion(navController: NavHostController,timePerQuestion: Int, indexQuestion: Int) {
    val questionViewModel: QuestionViewModel = viewModel()
    val questions = questionViewModel.getExampleQuestions()

    if (indexQuestion >= questions.size) {
        navController.navigate("startQuiz")
        return
    }

    val question = questions[indexQuestion]

    if (question.questionType == QuestionType.TRUE_FALSE) {
        navController.navigate("truefalse/${question.id}/$timePerQuestion/$indexQuestion")
    } else if (question.questionType == QuestionType.SINGLE_CHOICE) {
        navController.navigate("singlechoice/${question.id}/$timePerQuestion/$indexQuestion")
    } else if (question.questionType == QuestionType.MULTIPLE_CHOICE) {
        navController.navigate("multiplechoice/${question.id}/$timePerQuestion/$indexQuestion")
    } else if (question.questionType == QuestionType.MATCHING) {
        navController.navigate("matching/${question.id}/$timePerQuestion/$indexQuestion")
    } else if (question.questionType == QuestionType.ORDERING) {
        navController.navigate("ordering/${question.id}/$timePerQuestion/$indexQuestion")
    } else if (question.questionType == QuestionType.FILL_IN_THE_BLANK) {
        navController.navigate("fillintheblank/${question.id}/$timePerQuestion/$indexQuestion")
    } else if (question.questionType == QuestionType.ASSOCIATION) {
        navController.navigate("association/${question.id}/$timePerQuestion/$indexQuestion")
    } else if (question.questionType == QuestionType.WORD_BASED) {
        navController.navigate("wordbased/${question.id}/$timePerQuestion/$indexQuestion")
    }
}

@Composable
fun StartQuiz(navController: NavHostController, timePerQuestion: Int) {
    val questionViewModel: QuestionViewModel = viewModel()
    val questions = questionViewModel.getExampleQuestions()

    var hasStarted by remember { mutableStateOf(false) }

    if (questions.isNotEmpty()) {
        if (!hasStarted) {
            hasStarted = true
            NavigateToQuestion(navController, timePerQuestion,0)
        }
    } else {
        Text(
            text = "No questions available.",
            modifier = Modifier.fillMaxSize(),
            fontSize = 24.sp
        )
    }
}

@Composable
fun getQuestionById(questionViewModel: QuestionViewModel, questionId: Int): Question? {
    return questionViewModel.getExampleQuestions().find { it.id == questionId.toString() }
}


