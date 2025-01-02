package pt.isec.amov.quizectpamov.data.dtos

import pt.isec.amov.quizectpamov.utils.enums.QuestionType

data class QuestionDTO(
    val questionText: String = "",
    val questionType: QuestionType = QuestionType.TRUE_FALSE,
    val options: List<String>? = null,
    val correctAnswer: List<String>? = null
)