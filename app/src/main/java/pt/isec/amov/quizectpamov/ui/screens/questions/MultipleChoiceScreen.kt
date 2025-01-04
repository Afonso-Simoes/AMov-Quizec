package pt.isec.amov.quizectpamov.ui.screens.questions

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pt.isec.amov.quizectpamov.R

@Composable
fun MultipleChoiceScreen(
    questionId: String,
    timePerQuestion: Int,
    onNext: @Composable () -> Unit,
    indexQuestion: Int
) {
    val questionViewModel: QuestionViewModel = viewModel()
    val question = questionViewModel.getQuestionById(questionId)
    var remainingTime by rememberSaveable { mutableIntStateOf(timePerQuestion) }
    var isTimeUp by remember { mutableStateOf(false) }
    var next by remember { mutableStateOf(false) }
    val selectedOptions = remember { mutableStateListOf<String>() }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val context = LocalContext.current

    BackHandler {
        Toast.makeText(context, R.string.error_go_back, Toast.LENGTH_SHORT).show()
    }

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
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 32.dp, end = 16.dp),
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
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    question.options?.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = option in selectedOptions,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        selectedOptions.add(option)
                                    } else {
                                        selectedOptions.remove(option)
                                    }
                                }
                            )
                            Text(
                                text = option,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    question.options?.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = option in selectedOptions,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        selectedOptions.add(option)
                                    } else {
                                        selectedOptions.remove(option)
                                    }
                                }
                            )
                            Text(
                                text = option,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    } else {
        Text(
            text = "Question not found!",
            fontSize = 18.sp,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }
}
