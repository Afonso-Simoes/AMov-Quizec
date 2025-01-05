package pt.isec.amov.quizectpamov.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.type.DateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import pt.isec.amov.quizectpamov.data.dtos.QuestionDTO
import pt.isec.amov.quizectpamov.data.model.BaseQuestion
import pt.isec.amov.quizectpamov.data.model.Question
import pt.isec.amov.quizectpamov.data.model.QuestionFire
import pt.isec.amov.quizectpamov.data.repository.QuestionRepository
import pt.isec.amov.quizectpamov.ui.screens.SaveState
import pt.isec.amov.quizectpamov.utils.enums.QuestionType
import java.time.Instant
import java.util.Calendar
import java.util.Date

class QuestionViewModel : ViewModel() {
    private val repository = QuestionRepository()
    private val questions = mutableListOf<Question>()

    private val _saveState = MutableStateFlow<SaveState>(SaveState.Idle)
    val saveState: StateFlow<SaveState> get() = _saveState

    suspend fun getAllQuestions(): List<QuestionFire>? {
        return repository.getAllQuestions()
    }

    fun addQuestion(questionType: QuestionType, baseQuestion: BaseQuestion) {
        viewModelScope.launch {
            _saveState.value = SaveState.Loading
            val questionFire = QuestionFire(
                questionID = IDGenerator.generate(),
                created_at = System.currentTimeMillis(),
                created_by = "b1nM6Ght4FfPSTCKC4dNYXdLmYi2",
                type = questionType,
                data = baseQuestion,
            )

            val result = repository.addQuestion(questionFire)
            if (result != null) {
                _saveState.value = SaveState.Success(result)
            } else {
                _saveState.value = SaveState.Error("Falhou ao guardar pergunta")
            }
        }
    }

    suspend fun deleteQuestion(id: String): Boolean {
        return repository.deleteQuestion(id)
    }

    /*init {
        val questions1 = listOf(
            Question(
                id = "1",
                questionText = "Is the sky blue?",
                questionType = QuestionType.TRUE_FALSE,
                options = listOf("True", "False"),
                correctAnswer = listOf("True")
            ),
            Question(
                id = "2",
                questionText = "What is the capital of France?",
                questionType = QuestionType.SINGLE_CHOICE,
                options = listOf("Paris", "London", "Berlin", "Madrid", "Rome", "Lisbon"),
                correctAnswer = listOf("Paris")
            ),
            Question(
                id = "3",
                questionText = "The quick brown ___ jumps over the lazy dog.",
                questionType = QuestionType.FILL_IN_THE_BLANK,
                options = null,
                correctAnswer = listOf("fox")
            )
        )
        questions.addAll(questions1)
    }*/

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

    fun getQuestions(): List<Question> {
        return questions
    }

    fun getQuestionById(id: String): Question? {
        return questions.find { it.id == id }
    }
}
