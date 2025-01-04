package pt.isec.amov.quizectpamov.data.model

data class Quiz(
    val id: String,
    val name: String,
    val questions: List<Question>
)