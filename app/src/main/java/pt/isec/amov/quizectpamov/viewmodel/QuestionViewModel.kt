package pt.isec.amov.quizectpamov.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    //TODO: Remover o hardcoded
//    fun getExampleQuestions(): List<Question> {
//        return listOf(
//            // P01 - Perguntas de Sim/Não, Verdadeiro/Falso
//            Question(
//                id = "1",
//                questionText = "Is the sky blue?",
//                questionType = QuestionType.TRUE_FALSE,
//                options = listOf("True", "False"),
//                correctAnswer = listOf("True")
//            ),
//            //P02 - Perguntas de escolha múltipla com apenas uma resposta certa
//            Question(
//                id = "2",
//                questionText = "What is the capital of France?",
//                questionType = QuestionType.SINGLE_CHOICE,
//                options = listOf("Paris", "London", "Berlin", "Madrid", "Rome", "Lisbon"),
//                correctAnswer = listOf("Paris")
//            ),
//            // P03 - Perguntas de escolha múltipla com mais de uma resposta correta
//            Question(
//                id = "3",
//                questionText = "Which of the following are programming languages?",
//                questionType = QuestionType.MULTIPLE_CHOICE,
//                options = listOf("Kotlin", "Python", "HTML", "CSS"),
//                correctAnswer = listOf("Kotlin", "Python")
//            ),
//            // P04 - Perguntas de correspondência
//            Question(
//                id = "4",
//                questionText = "Match the countries with their capitals.",
//                questionType = QuestionType.MATCHING,
//                options = listOf("France - Paris", "Germany - Berlin", "Spain - Madrid", "Italy - Rome"),
//                correctAnswer = listOf("France - Paris", "Germany - Berlin", "Spain - Madrid", "Italy - Rome")
//            ),
//            // P05 - Perguntas de ordenação
//            Question(
//                id = "5",
//                questionText = "Order the planets from closest to farthest from the sun.",
//                questionType = QuestionType.ORDERING,
//                options = listOf("Mercury", "Venus", "Earth", "Mars"),
//                correctAnswer = listOf("Mercury", "Venus", "Earth", "Mars")
//            ),
//            // P06 - Perguntas de preenchimento de espaços em branco
//            Question(
//                id = "6",
//                questionText = "The capital of Japan is ____.",
//                questionType = QuestionType.FILL_IN_THE_BLANK,
//                options = listOf("Tokyo"),
//                correctAnswer = listOf("Tokyo")
//            ),
//            // P07 - Perguntas de associação
//            Question(
//                id = "7",
//                questionText = "Match the animal to its habitat.",
//                questionType = QuestionType.ASSOCIATION,
//                options = listOf("Lion - Savanna", "Penguin - Antarctica", "Kangaroo - Australia"),
//                correctAnswer = listOf("Lion - Savanna", "Penguin - Antarctica", "Kangaroo - Australia")
//            ),
//            // P08 - Perguntas com resposta baseada na indicação de palavras
//            Question(
//                id = "8",
//                questionText = "List the primary colors.",
//                questionType = QuestionType.WORD_BASED,
//                options = listOf("R__","B__" ,"Y__ "),
//                correctAnswer = listOf("Red , Blue , Yellow")
//            )
//        )
//    }

}
