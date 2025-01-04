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
        val quiz2 = Quiz(
            id = "2",
            name = "Sample Quiz 2",
            questions = listOf(
                Question(
                    id = "3",
                    questionText = "Is water wet?",
                    questionType = QuestionType.TRUE_FALSE,
                    options = listOf("True", "False"),
                    correctAnswer = listOf("True")
                ),
                Question(
                    id = "4",
                    questionText = "What is the capital of Spain?",
                    questionType = QuestionType.SINGLE_CHOICE,
                    options = listOf("Madrid", "Barcelona", "Valencia", "Seville", "Bilbao", "Malaga"),
                    correctAnswer = listOf("Madrid")
                )
            )
        )
        val quiz3 = Quiz(
            id = "3",
            name = "Sample Quiz 3",
            questions = listOf(
                Question(
                    id = "5",
                    questionText = "Is fire hot?",
                    questionType = QuestionType.TRUE_FALSE,
                    options = listOf("True", "False"),
                    correctAnswer = listOf("True")
                ),
                Question(
                    id = "6",
                    questionText = "What is the capital of Italy?",
                    questionType = QuestionType.SINGLE_CHOICE,
                    options = listOf("Rome", "Milan", "Naples", "Turin", "Palermo", "Genoa"),
                    correctAnswer = listOf("Rome")
                )
            )
        )
        val quiz4 = Quiz(
            id = "4",
            name = "Sample Quiz 4",
            questions = listOf(
                Question(
                    id = "7",
                    questionText = "Is ice cold?",
                    questionType = QuestionType.TRUE_FALSE,
                    options = listOf("True", "False"),
                    correctAnswer = listOf("True")
                ),
                Question(
                    id = "8",
                    questionText = "What is the capital of Germany?",
                    questionType = QuestionType.SINGLE_CHOICE,
                    options = listOf("Berlin", "Munich", "Frankfurt", "Hamburg", "Cologne", "Stuttgart"),
                    correctAnswer = listOf("Berlin")
                )
            )
        )
        quizzes.addAll(listOf(quiz1, quiz2, quiz3, quiz4))
    }

    fun addQuiz(questionario: Quiz) {
        quizzes.add(questionario)
    }

    fun getQuizzes(): List<Quiz> {
        return quizzes
    }

    fun getQuizzesById(id: String): Quiz? {
        return quizzes.find { it.id == id }
    }

}