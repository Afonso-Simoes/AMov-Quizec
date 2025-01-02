package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel

@Composable
fun QuizzesMenuScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var onDismiss by remember { mutableStateOf(false) }
    var quizName by remember { mutableStateOf("") }
    var selectedQuestions by remember { mutableStateOf(listOf<String>()) }
    val initialQuestionType = stringResource(id = R.string.true_false)
    var selectedQuestionType by remember { mutableStateOf(initialQuestionType) }
    var questionText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    var isClicked by remember { mutableStateOf(false) }

    val questionViewModel: QuestionViewModel = viewModel()
    val trueFalseQuestions = questionViewModel.getTrueFalseQuestions()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) 100.dp else 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.quizzes_menu_title),
            style = WelcomeTitleStyle,
            modifier = Modifier.padding(bottom = if (isLandscape) 0.dp else 32.dp, top = 40.dp)
        )

        Button(
            onClick = { onDismiss = !onDismiss },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text(text = stringResource(id = R.string.add_quiz))
        }

        if (!isLandscape) Spacer(modifier = Modifier.height(16.dp))


        if (onDismiss) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                TextField(
                    value = quizName,
                    onValueChange = { quizName = it },
                    label = { Text("Quiz Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                )

                if (!isLandscape) Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(trueFalseQuestions) { question ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    questionText = question.questionText
                                    selectedQuestionType = question.questionType.getLocalizedName(context = context)
                                },
                        ) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 20.dp)
                                ) {
                                    Text(
                                        text = question.questionText,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = stringResource(id = R.string.question_type, question.questionType.getLocalizedName(context)),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = stringResource(id = R.string.correct_answer, question.correctAnswer?.joinToString(", ") ?: ""),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = stringResource(id = R.string.answers, question.options?.joinToString(", ") ?: ""),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        selectedQuestions = selectedQuestions.toMutableList().apply {
                                            if (contains(question.id)) {
                                                remove(question.id)
                                            } else {
                                                add(question.id)
                                            }
                                        }
                                        // TODO: Implement function to associate questions with the quiz
                                    },
                                    modifier = Modifier
                                        .background(if (selectedQuestions.contains(question.id)) Color.Green else Color.Gray, shape = CircleShape)
                                        .size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add",
                                        tint = Color.White
                                    )
                                }

                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(bottom = if (isLandscape) 0.dp else 30.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            onDismiss = false
                            selectedQuestions = listOf()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            onDismiss = false
                            selectedQuestions = listOf()
                            // TODO: Save quiz logic
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(id = R.string.save))
                    }
                }
            }
        }

    }
}
