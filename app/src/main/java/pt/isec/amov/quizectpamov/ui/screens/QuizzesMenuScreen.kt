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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.data.model.Question
import pt.isec.amov.quizectpamov.data.model.Quiz
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel
import pt.isec.amov.quizectpamov.viewmodel.QuizViewModel

@Composable
fun QuizzesMenuScreen() {
    var onDismiss by remember { mutableStateOf(false) }
    var quizName by remember { mutableStateOf("") }
    var selectedQuestions by remember { mutableStateOf(listOf<String>()) }
    val initialQuestionType = stringResource(id = R.string.true_false)
    var selectedQuestionType by remember { mutableStateOf(initialQuestionType) }
    var questionText by remember { mutableStateOf("") }
    val quizViewModel: QuizViewModel = viewModel()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val questionViewModel: QuestionViewModel = viewModel()
    val trueFalseQuestions = questionViewModel.getExampleQuestions()

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
            AddQuizForm(
                quizName = quizName,
                onQuizNameChange = { quizName = it },
                isLandscape = isLandscape,
                trueFalseQuestions = trueFalseQuestions,
                selectedQuestions = selectedQuestions,
                onQuestionClick = { newQuestionText, questionType ->
                    questionText = newQuestionText
                    selectedQuestionType = questionType
                },
                onAddQuestionClick = { questionId ->
                    selectedQuestions = selectedQuestions.toMutableList().apply {
                        if (contains(questionId)) {
                            remove(questionId)
                        } else {
                            add(questionId)
                        }
                    }
                },
                onCancelClick = {
                    onDismiss = false
                    selectedQuestions = listOf()
                },
                onSaveClick = {
                    onDismiss = false
                    selectedQuestions = listOf()
                }
            )
        }
        QuizList(quizViewModel)


    }
}

@Composable
fun AddQuizForm(
    quizName: String,
    onQuizNameChange: (String) -> Unit,
    isLandscape: Boolean,
    trueFalseQuestions: List<Question>,
    selectedQuestions: List<String>,
    onQuestionClick: (String, String) -> Unit,
    onAddQuestionClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        TextField(
            value = quizName,
            onValueChange = onQuizNameChange,
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
                            onQuestionClick(question.questionText, question.questionType.getLocalizedName(context = context))
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
                                text = stringResource(id = R.string.question_type, question.questionType.getLocalizedName(context = LocalContext.current)),
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
                            onClick = { onAddQuestionClick(question.id) },
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
                onClick = onCancelClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.cancel))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = onSaveClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }
}


@Composable
fun QuizList(viewModel: QuizViewModel) {
    val quizzes = viewModel.getQuizzes()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(quizzes) { quiz ->
            QuizCard(quiz, viewModel)
        }
    }
}

@Composable
fun QuizCard(quiz: Quiz, onQuizClick: QuizViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var questionText by remember { mutableStateOf("") }
    var selectedQuestionType by remember { mutableStateOf("") }
    var correctAnswerText by remember { mutableStateOf("") }
    var optionsText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                //TODO: Implementar o edit quando carregares no quiz
            },
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = quiz.name, style = MaterialTheme.typography.bodyLarge)

                quiz.questions.forEach { question ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                questionText = question.questionText
                                selectedQuestionType = question.questionType.getLocalizedName(context = context)
                                correctAnswerText = question.correctAnswer?.joinToString(", ") ?: ""
                                optionsText = question.options?.joinToString(", ") ?: ""
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
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stringResource(id = R.string.question_type, question.questionType.getLocalizedName(context)),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(4.dp)
            ) {
                IconButton(
                    onClick = {
                        // TODO: Implement delete quiz logic
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
                        // TODO: Implement duplicate quiz logic
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.question_details_title)) },
            text = {
                Column {
                    Text(text = stringResource(id = R.string.question_text_label, questionText))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.question_type_label, selectedQuestionType))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.correct_answer_label, correctAnswerText))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.options_label, optionsText))
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "OK")
                }
            }
        )
    }
}




