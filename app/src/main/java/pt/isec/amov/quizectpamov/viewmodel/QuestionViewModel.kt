package pt.isec.amov.quizectpamov.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.isec.amov.quizectpamov.data.model.BaseQuestion
import pt.isec.amov.quizectpamov.data.model.Question
import pt.isec.amov.quizectpamov.data.model.QuestionFire
import pt.isec.amov.quizectpamov.data.repository.QuestionRepository
import pt.isec.amov.quizectpamov.ui.screens.SaveState
import pt.isec.amov.quizectpamov.utils.enums.QuestionType

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

    fun updateQuestion(questionId: String, newData: BaseQuestion) {
        viewModelScope.launch {
            try {
                val question = repository.getQuestionById(questionId)
                if (question != null) {
                    val updatedQuestion = question.copy(data = newData)
                    val updates = updatedQuestion.toMap()
                    repository.updateQuestion(questionId, updates)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun duplicateQuestion(question: QuestionFire) {
        viewModelScope.launch {
            _saveState.value = SaveState.Loading

            val duplicatedQuestion = question.copy(
                questionID = IDGenerator.generate(),
                created_at = System.currentTimeMillis(),
                created_by = "b1nM6Ght4FfPSTCKC4dNYXdLmYi2"
            )

            val result = repository.addQuestion(duplicatedQuestion)

            if (result != null) {
                _saveState.value = SaveState.Success(result)
            } else {
                _saveState.value = SaveState.Error("Falhou ao duplicar a pergunta")
            }
        }
    }


    suspend fun deleteQuestion(id: String): Boolean {
        return repository.deleteQuestion(id)
    }


    fun getQuestions(): List<Question> {
        return questions
    }

    fun getQuestionById(id: String): Question? {
        return questions.find { it.id == id }
    }

    fun resetSaveState() {
        _saveState.value = SaveState.Idle
    }
}
