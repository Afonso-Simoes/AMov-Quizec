package pt.isec.amov.quizectpamov.ui.screens.reports

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pt.isec.amov.quizectpamov.R

@Composable
fun MostUsedAnswersScreen(
    questionId: String,
    indexQuestion: Int,
    timePerQuestion: Int,
    onNext: @Composable () -> Unit
) {
    val questionResultsText = stringResource(id = R.string.question_results, indexQuestion)
    val context = LocalContext.current
    var remainingTime by rememberSaveable { mutableIntStateOf(timePerQuestion) }
    var isTimeUp by remember { mutableStateOf(false) }
    var next by remember { mutableStateOf(false) }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = questionResultsText,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}