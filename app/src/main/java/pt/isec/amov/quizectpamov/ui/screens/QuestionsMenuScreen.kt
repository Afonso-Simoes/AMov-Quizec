package pt.isec.amov.quizectpamov.ui.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.data.model.QuestionFire
import pt.isec.amov.quizectpamov.ui.screens.questions.AddQuestion
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel

@Composable
fun QuestionsMenuScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var editableQuestion by remember { mutableStateOf<QuestionFire?>(null) }
    var questionText by remember { mutableStateOf("") }
    val initialQuestionType = stringResource(id = R.string.true_false)
    var selectedQuestionType by remember { mutableStateOf(initialQuestionType) }
    val viewModel: QuestionViewModel = viewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val questions = remember { mutableStateOf<List<QuestionFire>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        loadQuestions(viewModel, questions) { isLoading = false }
    }

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

        if (editableQuestion == null && showDialog) {
            AddQuestion(
                viewModel = viewModel,
                onDismiss = { showDialog = false },
                currentQuestion = questionText,
                currentType = selectedQuestionType,
                onAddQuestion = {
                    coroutineScope.launch {
                        loadQuestions(viewModel, questions) { isLoading = false }
                    }
                },
                editableQuestion = null
            )
        }

        if (editableQuestion != null && showDialog) {
            AddQuestion(
                viewModel = viewModel,
                onDismiss = {
                    showDialog = false
                    editableQuestion = null
                },
                currentQuestion = editableQuestion?.data?.question.orEmpty(),
                currentType = editableQuestion?.type?.getLocalizedName(context).orEmpty(),
                onAddQuestion = {
                    coroutineScope.launch {
                        loadQuestions(viewModel, questions) { isLoading = false }
                    }
                },
                editableQuestion = editableQuestion
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(questions.value) { question ->
                    QuestionCard(
                        question = question,
                        context = context,
                        coroutineScope = coroutineScope,
                        onDelete = {
                            coroutineScope.launch {
                                viewModel.deleteQuestion(question.id ?: "")
                                questions.value =
                                    questions.value.filterNot { it.id == question.id }
                            }
                        },
                        onDuplicate = {
                            coroutineScope.launch {
                                viewModel.duplicateQuestion(question)
                                questions.value = viewModel.getAllQuestions() ?: emptyList()
                            }
                        },
                        onClick = {
                            editableQuestion = question
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionCard(
    question: QuestionFire,
    context: Context,
    coroutineScope: CoroutineScope,
    onDelete: () -> Unit,
    onDuplicate: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = question.data.question,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        id = R.string.question_type,
                        question.type.getLocalizedName(context)
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(4.dp)
            ) {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(
                    onClick = onDuplicate,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Duplicate"
                    )
                }
            }
        }
    }
}


suspend fun loadQuestions(
    viewModel: QuestionViewModel,
    questions: MutableState<List<QuestionFire>>,
    onFinished: () -> Unit
) {
    questions.value = viewModel.getAllQuestions() ?: emptyList()
    onFinished()
}