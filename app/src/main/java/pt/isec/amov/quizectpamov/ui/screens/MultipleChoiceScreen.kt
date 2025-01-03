package pt.isec.amov.quizectpamov.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MultipleChoiceScreen(questionId: Int, timePerQuestion: Int) {
    val questionViewModel: QuestionViewModel = viewModel()
    val question = getQuestionById(questionViewModel, questionId)

    if (question != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )    {
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
                        RadioButton(
                            selected = false, // You can manage the selected state as needed
                            onClick = { /* Handle option selection */ }
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
        Text(
            text = "Question not found!",
            fontSize = 18.sp,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }
}