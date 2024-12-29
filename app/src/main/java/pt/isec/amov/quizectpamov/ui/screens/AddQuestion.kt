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
import pt.isec.amov.quizectpamov.ui.components.MultipleChoiceSingleAnswer
import pt.isec.amov.quizectpamov.ui.components.TrueFalse

@Composable
fun AddQuestion(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
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
        stringResource(id = R.string.multiple_choice_multiple_answers)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) 100.dp else 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.add_edit_question), style = MaterialTheme.typography.titleLarge)

        QuestionTypeDropdown(
            types = types,
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            selectedType = questionType,
            onTypeSelected = { type ->
                questionType = type
                trueFalseAnswer = null
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (questionType == stringResource(id = R.string.true_false)) {
            TrueFalse(
                questionText = questionText,
                onQuestionTextChange = { questionText = it },
                selectedAnswer = trueFalseAnswer,
                onAnswerSelected = { answer -> trueFalseAnswer = answer },
                onDismiss = onDismiss,
                onSave = onSave
            )
        } else if (questionType == stringResource(id = R.string.multiple_choice_single_answer)) {
            MultipleChoiceSingleAnswer(
                questionText = questionText,
                onQuestionTextChange = { questionText = it },
                onDismiss = onDismiss,
                onSave = { questionText, answers, correctAnswerIndex -> }
            )
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
            label = { Text("Select Type") },
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
        label = { Text("Question Text") },
        modifier = Modifier.fillMaxWidth()
    )
}

