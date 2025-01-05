package pt.isec.amov.quizectpamov.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import pt.isec.amov.quizectpamov.data.model.BaseQuestion
import pt.isec.amov.quizectpamov.data.model.MultipleChoiceMultipleAnswerQuestion
import pt.isec.amov.quizectpamov.ui.screens.questions.QuestionTextField

@Composable
fun MultipleChoiceMultipleAnswers(
    questionData: MultipleChoiceMultipleAnswerQuestion,
    onDismiss: () -> Unit,
    onSave: (MultipleChoiceMultipleAnswerQuestion) -> Unit
) {
    var numberOfAnswers by remember { mutableIntStateOf(questionData.options.size.coerceAtLeast(2)) }
    var expanded by remember { mutableStateOf(false) }
    val errorEmptyAnswer = stringResource(id = R.string.error_empty_answer)
    val errorNoAnswerSelected = stringResource(id = R.string.error_no_answer_selected)
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

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { expanded = true }) {
                Text(stringResource(id = R.string.number_of_answers, numberOfAnswers))
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
                            numberOfAnswers = number
                            val updatedAnswers = List(number) { index ->
                                mutableData.value.options.getOrNull(index) ?: ""
                            }
                            val updatedCorrectIndexes =
                                mutableData.value.correctAnswerIndexes.filter { it < number }
                            mutableData.value = mutableData.value.copy(
                                options = updatedAnswers,
                                correctAnswerIndexes = updatedCorrectIndexes
                            )
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))


    for (index in 0 until numberOfAnswers) {
        var value = "";
        if (mutableData.value.options.size > 0) {
            value = mutableData.value.options[index];
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = value,
                onValueChange = { text ->
                    val updatedAnswers =
                        mutableData.value.options.toMutableList().apply { this[index] = text }
                    mutableData.value = mutableData.value.copy(options = updatedAnswers)
                },
                label = { Text(stringResource(id = R.string.answer_label, index + 1)) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Checkbox(
                checked = mutableData.value.correctAnswerIndexes.contains(index),
                onCheckedChange = { isChecked ->
                    val updatedCorrectIndexes =
                        mutableData.value.correctAnswerIndexes.toMutableSet().apply {
                            if (isChecked) add(index) else remove(index)
                        }
                    mutableData.value =
                        mutableData.value.copy(correctAnswerIndexes = updatedCorrectIndexes.toList())
                }
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
                when {
                    mutableData.value.question.isBlank() -> {
                        hasError.value = true
                        errorMessage.value = errorEmptyQuestion
                    }

                    mutableData.value.options.any { it.isBlank() } -> {
                        hasError.value = true
                        errorMessage.value = errorEmptyAnswer
                    }

                    mutableData.value.correctAnswerIndexes.isEmpty() -> {
                        hasError.value = true
                        errorMessage.value = errorNoAnswerSelected
                    }

                    else -> {
                        onSave(mutableData.value)
                        onDismiss()
                    }
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(id = R.string.save))
        }
    }
}