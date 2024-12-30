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

@Composable
fun WordBasedResponse(
    questionText: String,
    answerText: String,
    onQuestionTextChange: (String) -> Unit,
    onAnswerTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String, String, List<String>) -> Unit
) {
    val numberOfBlanks = remember(answerText) { answerText.split("___").size - 1 }
    var answers by remember(numberOfBlanks) { mutableStateOf(List(numberOfBlanks) { "" }) }

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
        TextField(
            value = questionText,
            onValueChange = onQuestionTextChange,
            label = { Text(stringResource(id = R.string.question_text)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.use_blanks_instruction),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodySmall
        )

        TextField(
            value = answerText,
            onValueChange = onAnswerTextChange,
            label = { Text(stringResource(id = R.string.answer))},
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(16.dp))

        val formattedAnswerText = formatResponseWithBlanks(answerText)
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
                    value = answers.getOrElse(index) { "" },
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
                    if (answers.any { it.isBlank() }) {
                        println("Preencha todos os espaços.")
                    } else {
                        onSave(questionText, answerText, answers)
                        println("Questão salva com sucesso!")
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }
}