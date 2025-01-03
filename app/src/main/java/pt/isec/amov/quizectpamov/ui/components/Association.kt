package pt.isec.amov.quizectpamov.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import coil.compose.rememberAsyncImagePainter
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.screens.QuestionTextField

@Composable
fun Association(
    questionText: String,
    onQuestionTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String, List<Pair<String?, String>>) -> Unit
) {
    var numberOfPairs by remember { mutableIntStateOf(2) }
    var pairs by remember { mutableStateOf(
        List(numberOfPairs) { Triple(true, null as String?, "") }
    ) }
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

        Button(
            onClick = { expanded = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
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
                        pairs = List(number) { index ->
                            pairs.getOrNull(index) ?: Triple(true, null, "")
                        }
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        pairs.forEachIndexed { index, (isConceptLeft, leftValue, rightValue) ->
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            pairs = pairs.mapIndexed { i, pair ->
                                if (i == index) pair.copy(first = !isConceptLeft, second = null) else pair
                            }
                        },
                        modifier = Modifier.size(140.dp, 40.dp)
                    ) {
                        Text(
                            text = if (isConceptLeft)
                                stringResource(id = R.string.concept_image_label)
                            else
                                stringResource(id = R.string.concept_concept_label)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    if (isConceptLeft) {
                        TextField(
                            value = leftValue ?: "",
                            onValueChange = { text ->
                                pairs = pairs.mapIndexed { i, pair ->
                                    if (i == index) pair.copy(second = text) else pair
                                }
                            },
                            label = { Text(stringResource(id = R.string.left_column_label, index + 1)) },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                            uri?.let {
                                pairs = pairs.mapIndexed { i, pair ->
                                    if (i == index) pair.copy(second = it.toString()) else pair
                                }
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Button(
                                onClick = { launcher.launch("image/*") },
                                modifier = Modifier.size(140.dp, 40.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.add_label)
                                )
                            }

                            if (leftValue != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Image(
                                    painter = rememberAsyncImagePainter(leftValue),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = rightValue,
                    onValueChange = { text ->
                        pairs = pairs.mapIndexed { i, pair ->
                            if (i == index) pair.copy(third = text) else pair
                        }
                    },
                    label = { Text(stringResource(id = R.string.right_column_label, index + 1)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onDismiss, modifier = Modifier.size(120.dp, 40.dp)) {
                Text(stringResource(id = R.string.cancel))
            }

            Button(
                onClick = {
                    if (questionText.isBlank()) {
                        hasError.value = true
                        errorMessage.value = errorEmptyQuestion
                    } else if (pairs.any { it.second.isNullOrBlank() || it.third.isBlank() }) {
                        hasError.value = true
                        errorMessage.value = errorEmptyAnswer
                    } else {
                        onSave(questionText, pairs.map { it.second to it.third })
                        onDismiss()
                    }
                },
                modifier = Modifier.size(120.dp, 40.dp)
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }
}
