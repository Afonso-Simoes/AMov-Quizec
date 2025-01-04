package pt.isec.amov.quizectpamov.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun Ordering(
    questionText: String,
    onQuestionTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String, List<String>) -> Unit
) {
    var numberOfOptions by remember { mutableIntStateOf(2) }
    var options by remember { mutableStateOf(List(numberOfOptions) { "" }) }
    var expanded by remember { mutableStateOf(false) }
    val errorEmptyQuestion = stringResource(id = R.string.error_empty_question)
    val errorEmptyAnswer = stringResource(id = R.string.error_empty_answer)

    val hasError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

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
            questionText = questionText,
            onQuestionTextChange = onQuestionTextChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { expanded = true }) {
                    Text(stringResource(id = R.string.number_of_itens, numberOfOptions))
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
                                numberOfOptions = number
                                options = List(number) { options.getOrNull(it) ?: "" }
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        options.forEachIndexed { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                IconButton(
                    onClick = {
                        if (index > 0) {
                            options = options.toMutableList().apply {
                                val temp = this[index]
                                this[index] = this[index - 1]
                                this[index - 1] = temp
                            }
                        }
                    }
                ) {
                    Icon(Icons.Default.ArrowUpward, contentDescription = "Mover para cima")
                }

                TextField(
                    value = option,
                    onValueChange = { text ->
                        options = options.toMutableList().apply { this[index] = text }
                    },
                    label = { Text(stringResource(id = R.string.option_label, index + 1)) },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        if (index < options.size - 1) {
                            options = options.toMutableList().apply {
                                val temp = this[index]
                                this[index] = this[index + 1]
                                this[index + 1] = temp
                            }
                        }
                    }
                ) {
                    Icon(Icons.Default.ArrowDownward, contentDescription = "Mover para baixo")
                }
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
                    if (questionText.isBlank()) {
                        hasError.value = true
                        errorMessage.value = errorEmptyQuestion
                    } else if (options.any { it.isBlank() }) {
                        hasError.value = true
                        errorMessage.value = errorEmptyAnswer
                    } else {
                        onSave(questionText, options.filter { it.isNotBlank() })
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
