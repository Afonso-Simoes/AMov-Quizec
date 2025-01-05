package pt.isec.amov.quizectpamov.viewmodel

import androidx.lifecycle.ViewModel
import pt.isec.amov.quizectpamov.data.dtos.QuestionDTO
import pt.isec.amov.quizectpamov.data.model.Question
import pt.isec.amov.quizectpamov.data.repository.QuestionRepository
import pt.isec.amov.quizectpamov.utils.enums.QuestionType

class QuestionViewModel : ViewModel() {
    private val repository = QuestionRepository()
    private val questions = mutableListOf<Question>()


    init {
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
    }

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

    fun addQuestion(question: Question) {
    }

    fun updateQuestion(id: String, question: Question) {
    }

    fun deleteQuestion(id: String) {
    }
}
