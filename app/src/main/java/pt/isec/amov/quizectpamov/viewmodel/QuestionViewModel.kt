package pt.isec.amov.quizectpamov.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.isec.amov.quizectpamov.data.dtos.QuestionDTO
import pt.isec.amov.quizectpamov.data.model.Question
import pt.isec.amov.quizectpamov.data.repository.QuestionRepository
import pt.isec.amov.quizectpamov.utils.enums.QuestionType

class QuestionViewModel : ViewModel() {
    private val repository = QuestionRepository()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> get() = _questions

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private fun Question.toDTO(): QuestionDTO {
        return QuestionDTO(
            questionText = this.questionText,
            questionType = this.questionType,
            options = this.options,
            correctAnswer = this.correctAnswer
        )
    }

    private fun QuestionDTO.toQuestion(id: String): Question {
        return Question(
            id = id,
            questionText = this.questionText,
            questionType = this.questionType,
            options = this.options,
            correctAnswer = this.correctAnswer
        )
    }

    fun fetchQuestions() {
        viewModelScope.launch {
            try {
                val fetchedQuestions = repository.getAllQuestions()
                _questions.value = fetchedQuestions.mapIndexed { index, questionDTO ->
                    questionDTO.toQuestion(index.toString()) // Usando um índice simples como ID
                }
            } catch (e: Exception) {
                _error.value = "Error fetching questions: ${e.message}"
            }
        }
    }

    fun addQuestion(question: Question) {
        viewModelScope.launch {
            try {
                val questionDTO = question.toDTO()
                val result = repository.addQuestion(questionDTO)
                if (result) fetchQuestions()
                else _error.value = "Error adding question"
            } catch (e: Exception) {
                _error.value = "Error adding question: ${e.message}"
            }
        }
    }

    fun updateQuestion(id: String, question: Question) {
        viewModelScope.launch {
            try {
                val questionDTO = question.toDTO()
                val result = repository.updateQuestion(id, questionDTO)
                if (result) fetchQuestions()
                else _error.value = "Error updating question"
            } catch (e: Exception) {
                _error.value = "Error updating question: ${e.message}"
            }
        }
    }

    fun deleteQuestion(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.deleteQuestion(id)
                if (result) fetchQuestions()
                else _error.value = "Error deleting question"
            } catch (e: Exception) {
                _error.value = "Error deleting question: ${e.message}"
            }
        }
    }


    fun getTrueFalseQuestions(): List<Question> {
        return listOf(
            Question(
                id = "1",
                questionText = "O afonfo faz surfe?",
                questionType = QuestionType.TRUE_FALSE,
                options = listOf("Verdadeiro", "Falso"),
                correctAnswer = listOf("Verdadeiro")
            ),
            Question(
                id = "2",
                questionText = "Is 2 + 2 equal to 5?",
                questionType = QuestionType.TRUE_FALSE,
                options = listOf("True", "False"),
                correctAnswer = listOf("False")
            ),
            Question(
                id = "3",
                questionText = "A",
                questionType = QuestionType.TRUE_FALSE,
                options = listOf("Verdadeiro", "Falso"),
                correctAnswer = listOf("Verdadeiro")
            ),
            Question(
                id = "4",
                questionText = "B",
                questionType = QuestionType.TRUE_FALSE,
                options = listOf("True", "False"),
                correctAnswer = listOf("False")
            ),
            Question(
                id = "5",
                questionText = "C",
                questionType = QuestionType.TRUE_FALSE,
                options = listOf("Verdadeiro", "Falso"),
                correctAnswer = listOf("Verdadeiro")
            ),
            Question(
                id = "6",
                questionText = "C",
                questionType = QuestionType.TRUE_FALSE,
                options = listOf("True", "False"),
                correctAnswer = listOf("False")
            )

        )
    }

}
