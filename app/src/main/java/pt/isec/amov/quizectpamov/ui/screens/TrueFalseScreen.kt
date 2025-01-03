package pt.isec.amov.quizectpamov.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel

@Composable
fun TrueFalseScreen(
    questionId: Int,
    timePerQuestion: Int,
) {
    val questionViewModel: QuestionViewModel = viewModel()
    val question = getQuestionById(questionViewModel, questionId)
    var remainingTime by remember { mutableStateOf(timePerQuestion) }

    LaunchedEffect(Unit) {
        while (remainingTime > 0) {
            delay(1000L)
            remainingTime--
        }
        //TODO: Adicionar lógica de ir para a proxima pergunta
    }

    if (question != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                progress = remainingTime / timePerQuestion.toFloat(),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(50.dp)
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
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { /* Resposta correta */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(48.dp)
                        .width(150.dp)
                ) {
                    Text(text = stringResource(id = R.string.true_text), fontSize = 18.sp)
                }
                Button(
                    onClick = { /* Resposta errada */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(48.dp)
                        .width(150.dp)
                ) {
                    Text(text = stringResource(id = R.string.false_text), fontSize = 18.sp)
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
