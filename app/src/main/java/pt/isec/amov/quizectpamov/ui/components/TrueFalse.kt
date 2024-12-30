package pt.isec.amov.quizectpamov.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.screens.QuestionTextField

@Composable
fun TrueFalse(
    questionText: String,
    onQuestionTextChange: (String) -> Unit,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    val selectCorrectAnswerText = stringResource(id = R.string.select_correct_answer)
    val trueText = stringResource(id = R.string.true_text)
    val falseText = stringResource(id = R.string.false_text)

    QuestionTextField(
        questionText = questionText,
        onQuestionTextChange = onQuestionTextChange
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(selectCorrectAnswerText, style = MaterialTheme.typography.bodyMedium)

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { onAnswerSelected("True") },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedAnswer == "True") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(trueText)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { onAnswerSelected("False") },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedAnswer == "False") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(falseText)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onDismiss,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(id = R.string.cancel))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                if (questionText.isNotEmpty() && selectedAnswer != null) {
                    onSave(questionText, "True/False: $selectedAnswer")
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(id = R.string.save))
        }
    }
}
