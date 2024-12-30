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
import pt.isec.amov.quizectpamov.ui.screens.QuestionTextField

@Composable
fun FillInTheBlank(
    questionText: String,
    onQuestionTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String, List<String>) -> Unit
) {
    val numberOfBlanks = remember(questionText) { questionText.split("___").size - 1 }
    var answers by remember(numberOfBlanks) { mutableStateOf(List(numberOfBlanks) { "" }) }

    @Composable
    fun formatQuestionWithBlanks(text: String): String {
        var formattedText = text
        for (i in 0 until numberOfBlanks) {
            val blankLabel = stringResource(id = R.string.blank_space_label, i + 1)
            formattedText = formattedText.replaceFirst("___", blankLabel)
        }
        return formattedText
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.use_blanks_instruction),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodySmall
        )

        QuestionTextField(
            questionText = questionText,
            onQuestionTextChange = onQuestionTextChange
        )

        Spacer(modifier = Modifier.height(16.dp))


        val formattedQuestionText = formatQuestionWithBlanks(questionText)
        Text(
            text = formattedQuestionText,
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
//                    if (answers.any { it.isBlank() }) {
//                        println("Preencha todos os espaços.")
//                    } else {
//                        onSave(questionText, answers)
//                        println("Questão salva com sucesso!")
//                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }
}
