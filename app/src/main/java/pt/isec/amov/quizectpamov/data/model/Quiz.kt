package pt.isec.amov.quizectpamov.data.model

import java.util.UUID

data class Quiz(
    val id: String = UUID.randomUUID().toString().take(6),
    val name: String,
    val description: String,
    val questions: List<Question>
)