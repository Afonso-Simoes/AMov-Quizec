package pt.isec.amov.quizectpamov.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel

@Composable
fun QuizzesMenuScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var isQuizFormVisible by remember { mutableStateOf(false) }
    var quizName by remember { mutableStateOf("") }
    var selectedQuestions by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quizzes Menu",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { isQuizFormVisible = !isQuizFormVisible },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Quiz")
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (isQuizFormVisible) {
            TextField(
                value = quizName,
                onValueChange = { quizName = it },
                label = { Text("Quiz Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TODO: Question selection list


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    if (quizName.isNotBlank() && selectedQuestions.isNotEmpty()) {
                        println("Quiz Saved: $quizName with questions: $selectedQuestions")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Quiz")
            }
        }
    }
}
