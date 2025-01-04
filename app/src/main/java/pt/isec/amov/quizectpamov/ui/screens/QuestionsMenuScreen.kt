package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel

@Composable
fun QuestionsMenuScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var questionText by remember { mutableStateOf("") }
    val initialQuestionType = stringResource(id = R.string.true_false)
    var selectedQuestionType by remember { mutableStateOf(initialQuestionType) }
    val questionViewModel: QuestionViewModel = viewModel()
    val context = LocalContext.current

    val questions = questionViewModel.getExampleQuestions()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) 100.dp else 16.dp,
                vertical = 35.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.questions_menu_title),
            style = WelcomeTitleStyle,
            modifier = Modifier.padding(bottom = 32.dp, top = 40.dp)
        )

        Button(
            onClick = {
                questionText = ""
                selectedQuestionType = initialQuestionType
                showDialog = true
            },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text(text = stringResource(id = R.string.add_question))
        }

        if (showDialog) {
            AddQuestion(
                onDismiss = { showDialog = false },
                currentQuestion = questionText,
                currentType = selectedQuestionType,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(questions) { question ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            questionText = question.questionText
                            selectedQuestionType = question.questionType.getLocalizedName(context = context)
                            showDialog = true
                        },
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
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

                        Column(
                            modifier = Modifier
                                .align(Alignment.Top)
                                .padding(4.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    // TODO: Implement delete question
                                },
                                modifier = Modifier
                                    .size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            IconButton(
                                onClick = {
                                    // TODO: Implement duplicate question
                                },
                                modifier = Modifier
                                    .size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Duplicate",
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}




