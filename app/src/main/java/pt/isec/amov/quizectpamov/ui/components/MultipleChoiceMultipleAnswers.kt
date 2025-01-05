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
import pt.isec.amov.quizectpamov.data.model.MultipleChoiceMultipleAnswerQuestion
import pt.isec.amov.quizectpamov.ui.screens.questions.QuestionTextField

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MultipleChoiceMultipleAnswers(
    questionData: MultipleChoiceMultipleAnswerQuestion,
    onDismiss: () -> Unit,
    onSave: (MultipleChoiceMultipleAnswerQuestion) -> Unit
) {
    var numberOfAnswers by remember { mutableIntStateOf(2) }
    var answers by remember { mutableStateOf(List(numberOfAnswers) { "" }) }
    var correctAnswerIndexes by remember { mutableStateOf(mutableSetOf<Int>()) }
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
                            answers = List(number) { answers.getOrNull(it) ?: "" }
                            correctAnswerIndexes.retainAll(0 until number) // Remove indices out of range
                            expanded = false
                        }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    for (index in 0 until numberOfAnswers) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = answers[index],
                onValueChange = { text ->
                    answers = answers.toMutableList().apply { this[index] = text }
                    mutableData.value = mutableData.value.copy(options = answers)
                },
                label = { Text(stringResource(id = R.string.answer_label, index + 1)) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Checkbox(
                checked = correctAnswerIndexes.contains(index),
                onCheckedChange = { isChecked ->
                    correctAnswerIndexes = correctAnswerIndexes.toMutableSet().apply {
                        if (isChecked) add(index) else remove(index)
                    }
                    mutableData.value = mutableData.value.copy(correctAnswerIndexes = correctAnswerIndexes.toList())
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
                    answers.any { it.isBlank() } -> {
                        hasError.value = true
                        errorMessage.value = errorEmptyAnswer
                    }
                    correctAnswerIndexes.isEmpty() -> {
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
