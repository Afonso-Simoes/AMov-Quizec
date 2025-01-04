package pt.isec.amov.quizectpamov.ui.screens.questions

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel

@Composable
fun AssociationScreen(
    questionId: String,
    timePerQuestion: Int,
    indexQuestion: Int,
    onNext: @Composable () -> Unit
) {
    val questionViewModel: QuestionViewModel = viewModel()
    val question = questionViewModel.getQuestionById(questionId)
    var remainingTime by rememberSaveable { mutableIntStateOf(timePerQuestion) }
    var isTimeUp by remember { mutableStateOf(false) }
    var next by remember { mutableStateOf(false) }
    val matchedPairs = remember { mutableStateListOf<Pair<String, String>>() }

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
                    question.options?.forEachIndexed { _, option ->
                        val leftSide = option.split(" - ")[0]
                        val inputText = remember { mutableStateOf("") }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(
                                    if (matchedPairs.any { it.first == leftSide }) Color.LightGray else Color.Transparent
                                )
                                .padding(8.dp),
                        ) {
                            Column {
                                Text(text = leftSide, fontSize = 18.sp)
                                if (!matchedPairs.any { it.first == leftSide }) {
                                    TextField(
                                        value = inputText.value,
                                        onValueChange = { newValue ->
                                            if (newValue.all { it.isDigit() }) {
                                                inputText.value = newValue
                                                val rightIndex = newValue.toIntOrNull()?.minus(1)
                                                if (rightIndex != null && rightIndex in question.options.indices) {
                                                    val rightSide = question.options[rightIndex].split(" - ")[1]
                                                    matchedPairs.add(leftSide to rightSide)
                                                    inputText.value = ""
                                                }
                                            }
                                        },
                                        placeholder = { Text("Enter number") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    question.options?.forEachIndexed { index, option ->
                        val rightSide = option.split(" - ")[1]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.Transparent)
                                .padding(8.dp)
                        ) {
                            Text(text = "${index + 1}. $rightSide", fontSize = 18.sp)
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        question.options?.forEachIndexed { _, option ->
                            val leftSide = option.split(" - ")[0]
                            val inputText = remember { mutableStateOf("") }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(
                                        if (matchedPairs.any { it.first == leftSide }) Color.LightGray else Color.Transparent
                                    )
                                    .padding(8.dp),
                            ) {
                                Column {
                                    Text(text = leftSide, fontSize = 18.sp)
                                    if (!matchedPairs.any { it.first == leftSide }) {
                                        TextField(
                                            value = inputText.value,
                                            onValueChange = { newValue ->
                                                if (newValue.all { it.isDigit() }) {
                                                    inputText.value = newValue
                                                    val rightIndex = newValue.toIntOrNull()?.minus(1)
                                                    if (rightIndex != null && rightIndex in question.options.indices) {
                                                        val rightSide = question.options[rightIndex].split(" - ")[1]
                                                        matchedPairs.add(leftSide to rightSide)
                                                        inputText.value = ""
                                                    }
                                                }
                                            },
                                            placeholder = { Text("Enter number") },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        question.options?.forEachIndexed { index, option ->
                            val rightSide = option.split(" - ")[1]
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color.Transparent)
                                    .padding(8.dp)
                            ) {
                                Text(text = "${index + 1}. $rightSide", fontSize = 18.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
