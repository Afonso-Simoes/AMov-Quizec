package pt.isec.amov.quizectpamov.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.screens.questions.QuestionTextField

@Composable
fun Matching(
    questionText: String,
    onQuestionTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String, List<Pair<String, String>>) -> Unit
) {
    var numberOfPairs by remember { mutableIntStateOf(2) }
    var pairs by remember { mutableStateOf(List(numberOfPairs) { "" to "" }) }
    var expanded by remember { mutableStateOf(false) }
    val errorEmptyQuestion = stringResource(id = R.string.error_empty_question)
    val errorEmptyPair = stringResource(id = R.string.error_empty_pair)

    val hasError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    if (hasError.value) {
        Text(
            text = errorMessage.value,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    QuestionTextField(
        questionText = questionText,
        onQuestionTextChange = onQuestionTextChange
    )

    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { expanded = true }) {
                Text(stringResource(id = R.string.number_of_pairs, numberOfPairs))
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(200.dp)
            ) {
                (2..6).forEach { number ->
                    DropdownMenuItem(
                        text = { Text(number.toString()) },
                        onClick = {
                            numberOfPairs = number
                            pairs = List(number) { pairs.getOrNull(it) ?: ("" to "") }
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    for (index in 0 until numberOfPairs) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = pairs[index].first,
                onValueChange = { text ->
                    pairs = pairs.toMutableList().apply { this[index] = text to pairs[index].second }
                },
                label = { Text(stringResource(id = R.string.left_column_label, index + 1)) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = pairs[index].second,
                onValueChange = { text ->
                    pairs = pairs.toMutableList().apply { this[index] = pairs[index].first to text }
                },
                label = { Text(stringResource(id = R.string.right_column_label, index + 1)) },
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
                if (questionText.isBlank()) {
                    hasError.value = true
                    errorMessage.value = errorEmptyQuestion
                } else if (pairs.any { it.first.isBlank() || it.second.isBlank() }) {
                    hasError.value = true
                    errorMessage.value = errorEmptyPair
                } else {
                    onSave(questionText, pairs)
                    onDismiss()
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(id = R.string.save))
        }
    }
}
