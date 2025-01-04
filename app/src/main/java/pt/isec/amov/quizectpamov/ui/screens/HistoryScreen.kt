package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.data.model.Participation
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle
import pt.isec.amov.quizectpamov.viewmodel.ParticipationViewModel
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel
import pt.isec.amov.quizectpamov.viewmodel.QuizViewModel
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel

@Composable
fun HistoryScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val participationViewModel: ParticipationViewModel = viewModel()
    val participations = participationViewModel.getParticipations()

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
            text = stringResource(id = R.string.history_screen_title),
            style = WelcomeTitleStyle,
            modifier = Modifier.padding(bottom = 32.dp, top = 40.dp)
        )

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            participations.forEach { participation ->
                ParticipationCard(participation)
            }
        }
    }
}

@Composable
fun ParticipationCard(participation: Participation) {
    val questionViewModel: QuestionViewModel = viewModel()
    val quizViewModel: QuizViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = "User: ${participation.userId}", style = MaterialTheme.typography.bodyMedium)
        val quiz = quizViewModel.getQuizById(participation.quizId)

        if (quiz != null) {
            Text(text = "Quiz: ${quiz.name}", style = MaterialTheme.typography.bodyMedium)
        }

        participation.answers.forEach { answer ->
            Spacer(modifier = Modifier.height(8.dp))
            val question = questionViewModel.getQuestionById(answer.questionId)
            if (question != null) {
                Text(text = question.questionText)
            }
            val isCorrect =
                question?.correctAnswer?.let { answer.selectedOptions.containsAll(it) } == true && question.correctAnswer.containsAll(
                    answer.selectedOptions
                )
            Text(

                text = "${answer.selectedOptions.joinToString(", ")} - ${
                    if (isCorrect) stringResource(
                        R.string.correct
                    ) else stringResource(R.string.wrong)
                }"
            )
            Button(
                onClick = { /*TODO: Logica para duplicar a pergunta*/ },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                ),
            ) {
                Text(stringResource(id = R.string.duplicate_question), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

}
