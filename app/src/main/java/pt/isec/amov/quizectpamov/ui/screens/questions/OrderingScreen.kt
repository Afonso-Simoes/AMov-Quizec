package pt.isec.amov.quizectpamov.ui.screens.questions

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel

@Composable
fun OrderingScreen(
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
    val options by remember { mutableStateOf(question?.options?.shuffled()?.toMutableList() ?: mutableListOf()) }

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

    if(question != null) {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
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
                        text = stringResource(R.string.remaining_time, remainingTime),                        fontSize = 18.sp,
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
                    options.forEachIndexed { index, option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "${index + 1}. $option",
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.wrapContentWidth()
                            ) {
                                if (index > 0) {
                                    Text(
                                        text = "↑",
                                        fontSize = 30.sp,
                                        modifier = Modifier
                                            .padding(start = 8.dp, end = 250.dp, top = 8.dp, bottom = 8.dp)
                                            .clickable {
                                                options.swap(index, index - 1)
                                            }
                                    )
                                }
                                if (index < options.size - 1) {
                                    Text(
                                        text = "↓",
                                        fontSize = 30.sp,
                                        modifier = Modifier
                                            .padding(start = 4.dp, end = 250.dp, top = 4.dp, bottom = 4.dp)
                                            .clickable {
                                                options.swap(index, index + 1)
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
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
                        text = stringResource(R.string.remaining_time, remainingTime),                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = question.questionText,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        options.forEachIndexed { index, option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "${index + 1}. $option",
                                    fontSize = 18.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Column {
                                    if (index > 0) {
                                        Text(
                                            text = "↑",
                                            fontSize = 30.sp,
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .clickable {
                                                    options.swap(index, index - 1)
                                                }
                                        )
                                    }
                                    if (index < options.size - 1) {
                                        Text(
                                            text = "↓",
                                            fontSize = 30.sp,
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .clickable {
                                                    options.swap(index, index + 1)
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


private fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}
