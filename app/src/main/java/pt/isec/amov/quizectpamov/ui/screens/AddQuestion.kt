package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.components.Association
import pt.isec.amov.quizectpamov.ui.components.FillInTheBlank
import pt.isec.amov.quizectpamov.ui.components.Matching
import pt.isec.amov.quizectpamov.ui.components.MultipleChoiceMultipleAnswers
import pt.isec.amov.quizectpamov.ui.components.MultipleChoiceSingleAnswer
import pt.isec.amov.quizectpamov.ui.components.Ordering
import pt.isec.amov.quizectpamov.ui.components.TrueFalse
import pt.isec.amov.quizectpamov.ui.components.WordBasedResponse

@Composable
fun AddQuestion(
    onDismiss: () -> Unit,
    currentQuestion: String,
    currentType: String
) {
    var questionText by remember { mutableStateOf(currentQuestion) }
    var questionType by remember { mutableStateOf(currentType) }
    var expanded by remember { mutableStateOf(false) }

    var trueFalseAnswer by remember { mutableStateOf<String?>(null) }

    val types = listOf(
        stringResource(id = R.string.true_false),
        stringResource(id = R.string.multiple_choice_single_answer),
        stringResource(id = R.string.multiple_choice_multiple_answers),
        stringResource(id = R.string.matching),
        stringResource(id = R.string.ordering),
        stringResource(id = R.string.fill_in_the_blank),
        stringResource(id = R.string.association),
        stringResource(id = R.string.word_based_response)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) 100.dp else 16.dp, vertical = 16.dp),        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.add_edit_question), style = MaterialTheme.typography.titleLarge)

        QuestionTypeDropdown(
            types = types,
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            selectedType = questionType,
            onTypeSelected = { type -> questionType = type }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (questionType) {
            stringResource(id = R.string.true_false) -> {
                TrueFalse(
                    questionText = questionText,
                    onQuestionTextChange = { questionText = it },
                    selectedAnswer = trueFalseAnswer,
                    onAnswerSelected = { answer -> trueFalseAnswer = answer },
                    onDismiss = onDismiss,
                    onSave = { questionText, answer ->
                        //TODO: Implementar lógica para salvar a pergunta
                    }
                )
            }
            stringResource(id = R.string.multiple_choice_single_answer) -> {
                MultipleChoiceSingleAnswer(
                    questionText = questionText,
                    onQuestionTextChange = { questionText = it },
                    onDismiss = onDismiss,
                    onSave = { questionText, answers, correctAnswerIndex ->
                        //TODO: Implementar lógica para salvar a pergunta
                    }
                )
            }
            stringResource(id = R.string.multiple_choice_multiple_answers) -> {
                MultipleChoiceMultipleAnswers(
                    questionText = questionText,
                    onQuestionTextChange = { questionText = it },
                    onDismiss = onDismiss,
                    onSave = { questionText, answers, correctAnswerIndices ->
                        //TODO: Implementar lógica para salvar a pergunta
                    }
                )
            }
            stringResource(id = R.string.matching) -> {
                Matching(
                    questionText = questionText,
                    onQuestionTextChange = { newText -> questionText = newText },
                    onDismiss = onDismiss,
                    onSave = { questionText, pairs ->
                        //TODO: Implementar lógica para salvar a pergunta
                    }
                )
            }
            stringResource(id = R.string.ordering) -> {
                Ordering(
                    questionText = questionText,
                    onQuestionTextChange = { questionText = it },
                    onDismiss = onDismiss,
                    onSave = { questionText, orderedList ->
                        //TODO: Implementar lógica para salvar a pergunta
                    }
                )
            }
            stringResource(id = R.string.fill_in_the_blank) -> {
                FillInTheBlank(
                    questionText = questionText,
                    onQuestionTextChange = { questionText = it },
                    onDismiss = onDismiss,
                    onSave = { questionText, answers ->
                        //TODO: Implementar lógica para salvar a pergunta
                    }
                )
            }
            stringResource(id = R.string.association) -> {
                Association(
                    questionText = questionText,
                    onQuestionTextChange = { newText -> questionText = newText },
                    onDismiss = onDismiss,
                    onSave = { questionText, pairs ->
                        //TODO: Implementar lógica para salvar a pergunta
                    }
                )
            }
            stringResource(id = R.string.word_based_response) -> {
                WordBasedResponse(
                    questionText = questionText,
                    initialAnswerText = "",
                    onQuestionTextChange = { questionText = it },
                    onAnswerTextChange = {  },
                    onDismiss = onDismiss,
                    onSave = { questionText, initialAnswerText, answers ->
                        //TODO: Implementar lógica para salvar a pergunta
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTypeDropdown(
    types: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        TextField(
            readOnly = true,
            value = selectedType,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.select_type)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
fun QuestionTextField(
    questionText: String,
    onQuestionTextChange: (String) -> Unit
) {
    TextField(
        value = questionText,
        onValueChange = onQuestionTextChange,
        label = { Text(stringResource(id = R.string.question_text)) },
        modifier = Modifier.fillMaxWidth()
    )
}

