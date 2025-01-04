package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel

@Composable
fun WordBasedScreen(
    questionId: Int,
    timePerQuestion: Int,
    onNext: @Composable () -> Unit,
    indexQuestion: Int
) {
    val questionViewModel: QuestionViewModel = viewModel()
    val question = getQuestionById(questionViewModel, questionId)
    var remainingTime by rememberSaveable { mutableIntStateOf(timePerQuestion) }
    var isTimeUp by remember { mutableStateOf(false) }
    var next by remember { mutableStateOf(false) }

    val context = LocalContext.current

    BackHandler {
        Toast.makeText(context, R.string.error_go_back, Toast.LENGTH_SHORT).show()
    }

    var userAnswers by remember {
        mutableStateOf(
            question?.options?.map { "" } ?: listOf()
        )
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        while (remainingTime > 0) {
            delay(1000L)
            remainingTime--
        }
        isTimeUp = true
    }

    if (isTimeUp && !next) {
        next = true
        onNext()
    }

    if (question != null) {
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        progress = { remainingTime / timePerQuestion.toFloat() },
                        modifier = Modifier.size(80.dp),
                    )
                    Text(
                        text = "$remainingTime seconds",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                Column(
                    modifier = Modifier.weight(2f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = question.questionText,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    question.options?.forEachIndexed { index, option ->
                        OutlinedTextField(
                            value = userAnswers[index],
                            onValueChange = { newValue ->
                                userAnswers = userAnswers.toMutableList().apply { this[index] = newValue }
                            },
                            label = { Text(option) },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(bottom = 16.dp)
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    progress = { remainingTime / timePerQuestion.toFloat() },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(50.dp),
                )
                Text(
                    text = "$remainingTime seconds",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = question.questionText,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                question.options?.forEachIndexed { index, option ->
                    OutlinedTextField(
                        value = userAnswers[index],
                        onValueChange = { newValue ->
                            userAnswers = userAnswers.toMutableList().apply { this[index] = newValue }
                        },
                        label = { Text(option) },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}
