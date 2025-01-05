import com.google.firebase.firestore.DocumentSnapshot
import pt.isec.amov.quizectpamov.data.model.QuestionFire

data class QuizFire(
    val id: String? = null,
    val quizID: String,
    val created_at: Long,
    val created_by: String,
    val data: List<QuestionFire>,
    val image: ByteArray? = null // Campo para armazenar a imagem em bytes
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "quizID" to quizID,
            "created_at" to created_at,
            "created_by" to created_by,
            "data" to data.map { it.toMap() },
            "image" to image?.let { android.util.Base64.encodeToString(it, android.util.Base64.DEFAULT) } // Usando Base64 do Android
        )
    }

    companion object {
        fun fromMap(id: String, data: Map<String, Any>): QuizFire? {
            val id = id
            val quizID = data["quizID"] as? String ?: return null
            val createdAt = data["created_at"] as? Long ?: return null
            val createdBy = data["created_by"] as? String ?: return null
            val questionsData = data["data"] as? List<Map<String, Any>> ?: return null

            val questions = questionsData.mapNotNull { questionMap ->
                QuestionFire.fromJson(id, questionMap)
            }

            val imageBase64 = data["image"] as? String
            val image = imageBase64?.let { android.util.Base64.decode(it, android.util.Base64.DEFAULT) } // Decodificação usando Base64 do Android

            return QuizFire(id, quizID, createdAt, createdBy, questions, image)
        }
    }
}
