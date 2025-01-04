package pt.isec.amov.quizectpamov.data.model

data class Answer(
    val questionId: String,
    val userId: String,
    val selectedOptions: List<String>
)