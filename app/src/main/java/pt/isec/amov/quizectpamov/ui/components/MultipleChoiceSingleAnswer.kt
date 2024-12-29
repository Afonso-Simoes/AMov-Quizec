package pt.isec.amov.quizectpamov.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isec.amov.quizectpamov.ui.screens.QuestionTextField


@Composable
fun MultipleChoiceSingleAnswer(
    questionText: String,
    onQuestionTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String, List<String>, Int) -> Unit
) {
    var numberOfAnswers by remember { mutableStateOf(2) }
    var answers by remember { mutableStateOf(List(numberOfAnswers) { "" }) }
    var correctAnswerIndex by remember { mutableStateOf(-1) }
    var expanded by remember { mutableStateOf(false) }

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
                Text("Número de Respostas: $numberOfAnswers")
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
                onValueChange = { text -> answers = answers.toMutableList().apply { this[index] = text } },
                label = { Text("Answer ${index + 1}") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            RadioButton(
                selected = correctAnswerIndex == index,
                onClick = { correctAnswerIndex = index }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }

    // Botões de Salvar e Cancelar
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onDismiss,
            modifier = Modifier.weight(1f)
        ) {
            Text("Cancelar")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                when {
                    answers.any { it.isBlank() } -> {
                        println("Preencha todas as respostas.")
                    }
                    correctAnswerIndex == -1 -> {
                        println("Selecione a resposta correta.")
                    }
                    else -> {
                        onSave(questionText, answers, correctAnswerIndex)
                        println("Questão salva com sucesso!")
                    }
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text("Salvar")
        }
    }
}
