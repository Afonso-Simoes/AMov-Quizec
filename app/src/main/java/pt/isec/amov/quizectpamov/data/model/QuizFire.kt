import com.google.firebase.firestore.DocumentSnapshot
import pt.isec.amov.quizectpamov.data.model.QuestionFire

data class QuizFire(
    val id: String? = null,
    val quizID: String,
    val created_at: Long,
    val created_by: String,
    val data: List<QuestionFire>
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "quizID" to quizID,
            "created_at" to created_at,
            "created_by" to created_by,
            "data" to data.map { it.toMap() }
        )
    }

    companion object {
        fun fromDocument(document: DocumentSnapshot): QuizFire? {
            val data = document.data ?: return null
            val id = document.id
            val quizID = data["quizID"] as? String ?: return null
            val createdAt = data["created_at"] as? Long ?: return null
            val createdBy = data["created_by"] as? String ?: return null
            val questionsData = data["data"] as? List<Map<String, Any>> ?: return null

           /* val questions = questionsData.mapNotNull { questionMap ->
                val documentData = mapOf("data" to questionMap)
                QuestionFire.fromJson(0, questionMap)
            }*/

            return QuizFire(id, quizID, createdAt, createdBy, emptyList())
        }
    }
}
