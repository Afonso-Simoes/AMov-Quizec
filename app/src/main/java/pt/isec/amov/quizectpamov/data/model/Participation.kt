package pt.isec.amov.quizectpamov.data.model

data class Participation (
    val userId: String,
    val quizId: String,
    val answers: List<Answer>,
)
