package pt.isec.amov.quizectpamov.ui.screens.questions

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import pt.isec.amov.quizectpamov.data.model.AssociationQuestion
import pt.isec.amov.quizectpamov.data.model.FillInTheBlankQuestion
import pt.isec.amov.quizectpamov.data.model.MatchingQuestion
import pt.isec.amov.quizectpamov.data.model.MultipleChoiceMultipleAnswerQuestion
import pt.isec.amov.quizectpamov.data.model.MultipleChoiceSingleAnswerQuestion
import pt.isec.amov.quizectpamov.data.model.OrderingQuestion
import pt.isec.amov.quizectpamov.data.model.QuestionFire
import pt.isec.amov.quizectpamov.data.model.TrueFalseQuestion
import pt.isec.amov.quizectpamov.data.model.WordBasedQuestion
import pt.isec.amov.quizectpamov.ui.components.Association
import pt.isec.amov.quizectpamov.ui.components.FillInTheBlank
import pt.isec.amov.quizectpamov.ui.components.Matching
import pt.isec.amov.quizectpamov.ui.components.MultipleChoiceMultipleAnswers
import pt.isec.amov.quizectpamov.ui.components.MultipleChoiceSingleAnswer
import pt.isec.amov.quizectpamov.ui.components.Ordering
import pt.isec.amov.quizectpamov.ui.components.TrueFalse
import pt.isec.amov.quizectpamov.ui.components.WordBasedResponse
import pt.isec.amov.quizectpamov.ui.screens.SaveState
import pt.isec.amov.quizectpamov.utils.enums.QuestionType
import pt.isec.amov.quizectpamov.viewmodel.QuestionViewModel

@Composable
fun AddQuestion(
    viewModel: QuestionViewModel,
    onDismiss: () -> Unit,
    currentQuestion: String,
    currentType: String,
    onAddQuestion: () -> Unit,
    editableQuestion: QuestionFire?
) {
    val questionText by remember { mutableStateOf(currentQuestion) }
    var questionType by remember { mutableStateOf(currentType) }
    var expanded by remember { mutableStateOf(false) }

    val saveState by viewModel.saveState.collectAsState()

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
            .padding(
                horizontal = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) 100.dp else 16.dp,
                vertical = 16.dp
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.add_edit_question),
            style = MaterialTheme.typography.titleLarge
        )

        QuestionTypeDropdown(
            types = types,
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            selectedType = questionType,
            onTypeSelected = { type -> questionType = type }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (saveState) {
            is SaveState.Idle -> {
                when (questionType) {
                    stringResource(id = R.string.true_false) -> {
                        TrueFalse(
                            questionData = if (editableQuestion != null
                                && editableQuestion.data is TrueFalseQuestion
                            ) {
                                editableQuestion.data
                            } else {
                                TrueFalseQuestion(questionText, false)
                            },
                            onDismiss = onDismiss,
                            onSave = { data ->
                                if (editableQuestion != null && editableQuestion.id != null) {
                                    viewModel.updateQuestion(editableQuestion.id, data)
                                } else {
                                    viewModel.addQuestion(QuestionType.TRUE_FALSE, data)
                                }

                                onAddQuestion()
                                viewModel.resetSaveState()
                            }
                        )
                    }

                    //TODO: Esta a dar bronca
                    stringResource(id = R.string.multiple_choice_single_answer) -> {
                        MultipleChoiceSingleAnswer(

                            questionData = MultipleChoiceSingleAnswerQuestion(
                                questionText,
                                listOf(),
                                0
                            ),
                            onDismiss = onDismiss,
                            onSave = { data ->
                                viewModel.addQuestion(QuestionType.SINGLE_CHOICE, data)
                                onAddQuestion()
                                viewModel.resetSaveState()
                            }
                        )
                    }

                    stringResource(id = R.string.multiple_choice_multiple_answers) -> {
                        MultipleChoiceMultipleAnswers(
                            questionData = if (editableQuestion != null
                                && editableQuestion.data is MultipleChoiceMultipleAnswerQuestion
                            ) {
                                editableQuestion.data
                            } else {
                                MultipleChoiceMultipleAnswerQuestion(
                                    questionText,
                                    listOf(),
                                    listOf()
                                )
                            },
                            onDismiss = onDismiss,
                            onSave = { data ->
                                if (editableQuestion != null && editableQuestion.id != null) {
                                    viewModel.updateQuestion(editableQuestion.id, data)
                                } else {
                                    viewModel.addQuestion(QuestionType.MULTIPLE_CHOICE, data)
                                }
                                onAddQuestion()
                                viewModel.resetSaveState()
                            }
                        )
                    }

                    stringResource(id = R.string.matching) -> {
                        Matching(
                            questionData = if (editableQuestion != null
                                && editableQuestion.data is MatchingQuestion
                            ) {
                                editableQuestion.data
                            } else {
                                MatchingQuestion(
                                    questionText,
                                    listOf(),
                                    listOf(),
                                    listOf()
                                )
                            },
                            onDismiss = onDismiss,
                            onSave = { data ->
                                if (editableQuestion != null && editableQuestion.id != null) {
                                    viewModel.updateQuestion(editableQuestion.id, data)
                                } else {
                                    viewModel.addQuestion(QuestionType.MATCHING, data)
                                }
                                onAddQuestion()
                                viewModel.resetSaveState()
                            }
                        )
                    }

                    stringResource(id = R.string.ordering) -> {
                        Ordering(
                            questionData = if (editableQuestion != null
                                && editableQuestion.data is OrderingQuestion
                            ) {
                                editableQuestion.data
                            } else {
                                OrderingQuestion(questionText,
                                    listOf(),
                                    listOf()
                                )
                            },
                            onDismiss = onDismiss,
                            onSave = { data ->
                                if (editableQuestion != null && editableQuestion.id != null) {
                                    viewModel.updateQuestion(editableQuestion.id, data)
                                } else {
                                    viewModel.addQuestion(QuestionType.ORDERING, data)
                                }
                                onAddQuestion()
                                viewModel.resetSaveState()
                            }
                        )
                    }

                    stringResource(id = R.string.fill_in_the_blank) -> {
                        FillInTheBlank(
                            questionData = if (editableQuestion != null
                                && editableQuestion.data is FillInTheBlankQuestion
                            ) {
                                editableQuestion.data
                            } else {
                                FillInTheBlankQuestion(questionText, listOf())
                            },
                            onDismiss = onDismiss,
                            onSave = { data ->
                                if (editableQuestion != null && editableQuestion.id != null) {
                                    viewModel.updateQuestion(editableQuestion.id, data)
                                } else {
                                    viewModel.addQuestion(QuestionType.FILL_IN_THE_BLANK, data)
                                }
                                onAddQuestion()
                                viewModel.resetSaveState()
                            }
                        )
                    }

                    stringResource(id = R.string.association) -> {
                        Association(
                            questionData = if (editableQuestion != null
                                && editableQuestion.data is AssociationQuestion
                            ) {
                                editableQuestion.data
                            } else {
                                AssociationQuestion(
                                    questionText,
                                    listOf(),
                                    listOf(),
                                    listOf()
                                )
                            },
                            onDismiss = onDismiss,
                            onSave = { data ->
                                if (editableQuestion != null && editableQuestion.id != null) {
                                    viewModel.updateQuestion(editableQuestion.id, data)
                                } else {
                                    viewModel.addQuestion(QuestionType.ASSOCIATION, data)
                                }
                                onAddQuestion()
                                viewModel.resetSaveState()
                            }
                        )
                    }

                    stringResource(id = R.string.word_based_response) -> {
                        WordBasedResponse(
                            questionData = if (editableQuestion != null
                                && editableQuestion.data is WordBasedQuestion
                            ) {
                                editableQuestion.data
                            } else {
                                WordBasedQuestion(questionText, "", listOf())
                            },
                            onDismiss = onDismiss,
                            onSave = { data ->
                                if (editableQuestion != null && editableQuestion.id != null) {
                                    viewModel.updateQuestion(editableQuestion.id, data)
                                } else {
                                    viewModel.addQuestion(QuestionType.WORD_BASED, data)
                                }
                                onAddQuestion()
                                viewModel.resetSaveState()
                            }
                        )
                    }
                }
            }

            is SaveState.Loading -> {
                CircularProgressIndicator()
            }

            is SaveState.Success -> {
                val documentId = (saveState as SaveState.Success).documentId
                onAddQuestion()
                viewModel.resetSaveState()
            }

            is SaveState.Error -> {
                val message = (saveState as SaveState.Error).message
                Text("Error: $message")
                viewModel.resetSaveState()
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

