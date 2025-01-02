package pt.isec.amov.quizectpamov.data.model

import pt.isec.amov.quizectpamov.utils.enums.QuestionType

data class Question(
    var id: String,
    val questionText: String,
    val questionType: QuestionType,
    val options: List<String>?,
    val correctAnswer: List<String>?
)