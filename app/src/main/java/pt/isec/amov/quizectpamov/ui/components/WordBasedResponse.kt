package pt.isec.amov.quizectpamov.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.data.model.WordBasedQuestion
import pt.isec.amov.quizectpamov.ui.screens.questions.QuestionTextField

@Composable
fun WordBasedResponse(
    questionData: WordBasedQuestion,
    onDismiss: () -> Unit,
    onSave: (WordBasedQuestion) -> Unit
) {
    val mutableData = remember { mutableStateOf(questionData) }
    var answerText by remember { mutableStateOf("") }
    val numberOfBlanks = remember(answerText) { mutableData.value.initialAnswerText.split("___").size - 1 }
    var answers by remember(numberOfBlanks) { mutableStateOf(List(numberOfBlanks) { "" }) }
    val errorEmptyQuestion = stringResource(id = R.string.error_empty_question)
    val errorEmptyAnswer = stringResource(id = R.string.error_empty_answer)
    val noBlanksError = stringResource(id = R.string.no_blanks_error)

    val hasError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }


    @Composable
    fun formatResponseWithBlanks(text: String): String {
        var formattedText = text
        for (i in 0 until numberOfBlanks) {
            val blankLabel = stringResource(id = R.string.blank_space_label, i + 1)
            formattedText = formattedText.replaceFirst("___", blankLabel)
        }
        return formattedText
    }

    Column(modifier = Modifier.fillMaxWidth()) {
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
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.use_blanks_instruction),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodySmall
        )

        TextField(
            value = mutableData.value.initialAnswerText,
            onValueChange = {
                answerText = it
                mutableData.value = mutableData.value.copy(initialAnswerText = it)
            },
            label = { Text(stringResource(id = R.string.answer)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(16.dp))

        val formattedAnswerText = formatResponseWithBlanks(mutableData.value.initialAnswerText)

        Text(
            text = formattedAnswerText,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        for (index in 0 until numberOfBlanks) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = if (index < mutableData.value.answers.size) mutableData.value.answers[index] else "",
                    onValueChange = { text ->
                        answers = answers.toMutableList().apply { this[index] = text }
                    },
                    label = { Text(stringResource(id = R.string.blank_space_label, index + 1)) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

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
                    if (mutableData.value.question.isBlank()) {
                        hasError.value = true
                        errorMessage.value = errorEmptyQuestion
                    } else if (answerText.isBlank()) {
                        hasError.value = true
                        errorMessage.value = errorEmptyAnswer
                    } else if (numberOfBlanks == 0) {
                        hasError.value = true
                        errorMessage.value = noBlanksError
                    } else if (answers.any { it.isBlank() }) {
                        hasError.value = true
                        errorMessage.value = errorEmptyAnswer
                    } else {
                        mutableData.value = mutableData.value.copy(answers = answers)
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
}