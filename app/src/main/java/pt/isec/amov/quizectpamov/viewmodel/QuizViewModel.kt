package pt.isec.amov.quizectpamov.viewmodel

import androidx.lifecycle.ViewModel
import pt.isec.amov.quizectpamov.data.model.Question
import pt.isec.amov.quizectpamov.data.model.Quiz
import pt.isec.amov.quizectpamov.utils.enums.QuestionType

class QuizViewModel : ViewModel() {
    private val quizzes = mutableListOf<Quiz>()

    init {
        //TODO: Remover o hardcoded
        val quiz1 = Quiz(
            id = "1",
            name = "Sample Quiz 1",
            description = "A quiz with basic true/false and single choice questions.",
            questions = listOf(
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
                )
            )
        )

        quizzes.addAll(listOf(quiz1))
    }

    fun addQuiz(questionario: Quiz) {
        quizzes.add(questionario)
    }

    fun getQuizzes(): List<Quiz> {
        return quizzes
    }

    fun getQuizById(id: String): Quiz? {
        return quizzes.find { it.id == id }
    }

}