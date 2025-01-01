package pt.isec.amov.quizectpamov.data.model

open class QuestionBase(
    val id: String? = null,
    val questionText: String,
    val questionType: String,
    open val correctAnswer: Any? = null
)
