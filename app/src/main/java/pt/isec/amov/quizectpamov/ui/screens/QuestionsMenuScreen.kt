package pt.isec.amov.quizectpamov.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel

@Composable
fun QuestionsMenuScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var questionText by remember { mutableStateOf("") }
    val initialQuestionType = stringResource(id = R.string.true_false)
    var selectedQuestionType by remember { mutableStateOf(initialQuestionType) }
    var questionsList by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.questions_menu_title),
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 32.dp, top = 40.dp)
        )

        Button(
            onClick = {
                questionText = ""
                selectedQuestionType = initialQuestionType
                showDialog = true
            },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text(text = stringResource(id = R.string.add_question))
        }

        if (showDialog) {
            AddQuestion(
                onDismiss = { showDialog = false },
                onSave = { question, type ->
                    if (questionText.isNotEmpty()) {
                        questionsList = questionsList + "$question ($type)"
                        Toast.makeText(navController.context, "Question Added: $question", Toast.LENGTH_SHORT).show()
                    }
                    showDialog = false
                },
                currentQuestion = questionText,
                currentType = selectedQuestionType
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(questionsList.size) { index ->
                Text(
                    text = questionsList[index],
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            questionText = questionsList[index].split(" (")[0]
                            selectedQuestionType = questionsList[index].split(" (")[1].removeSuffix(")")
                            showDialog = true
                        },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}



