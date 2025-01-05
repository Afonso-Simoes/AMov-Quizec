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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.data.model.TrueFalseQuestion
import pt.isec.amov.quizectpamov.ui.screens.questions.QuestionTextField

@Composable
fun TrueFalse(
    questionData: TrueFalseQuestion,
    onDismiss: () -> Unit,
    onSave: (TrueFalseQuestion) -> Unit,
) {
    val selectCorrectAnswerText = stringResource(id = R.string.select_correct_answer)
    val trueText = stringResource(id = R.string.true_text)
    val falseText = stringResource(id = R.string.false_text)
    val errorEmptyQuestion = stringResource(id = R.string.error_empty_question)

    val hasError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val mutableData = remember { mutableStateOf(questionData) }


    if (hasError.value) {
        Text(
            text = errorMessage.value,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    QuestionTextField(
        questionText = mutableData.value.question,
        onQuestionTextChange = { text ->
            mutableData.value = mutableData.value.copy(question = text)
        },
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(selectCorrectAnswerText, style = MaterialTheme.typography.bodyMedium)

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { mutableData.value = mutableData.value.copy(correctAnswer = true) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (mutableData.value.correctAnswer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(trueText)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { mutableData.value = mutableData.value.copy(correctAnswer = false) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!mutableData.value.correctAnswer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
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
                if (mutableData.value.question.isEmpty()) {
                    hasError.value = true
                    errorMessage.value = errorEmptyQuestion
                } else {
                    hasError.value = false
                    errorMessage.value = ""
                    onSave(mutableData.value)
                    onDismiss()
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(id = R.string.save))
        }
    }
}
