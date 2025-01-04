package pt.isec.amov.quizectpamov.viewmodel

import androidx.lifecycle.ViewModel
import pt.isec.amov.quizectpamov.data.model.Answer
import pt.isec.amov.quizectpamov.data.model.Participation

class ParticipationViewModel : ViewModel() {

    private val participations = mutableListOf<Participation>()

    init {
        // TODO: Remover o hardcoded
        val participation1 = Participation(
            userId = "user1",
            quizId = "1",
            answers = listOf(
                Answer(
                    questionId = "1",
                    userId = "user1",
                    selectedOptions = listOf("True")
                ),
                Answer(
                    questionId = "2",
                    userId = "user1",
                    selectedOptions = listOf("London")
                )
            )
        )

        val participation2 = Participation(
            userId = "user2",
            quizId = "1",
            answers = listOf(
                Answer(
                    questionId = "1",
                    userId = "user2",
                    selectedOptions = listOf("False")
                ),
                Answer(
                    questionId = "2",
                    userId = "user2",
                    selectedOptions = listOf("Paris")
                )
            )
        )

        participations.addAll(listOf(participation1, participation2))
    }

    fun getParticipations(): List<Participation> {
        return participations
    }

}
