package pt.isec.amov.quizectpamov.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pt.isec.amov.quizectpamov.data.dtos.QuestionDTO
import pt.isec.amov.quizectpamov.data.model.QuestionFire

class QuestionRepository {
    private val db = FirebaseFirestore.getInstance()
    private val questionCollection = db.collection("questions")

    suspend fun getAllQuestions(): List<QuestionFire>? {
        return try {
            val snapshot = questionCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                QuestionFire.fromDocument(document)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getQuestionById(id: String): QuestionFire? {
        return try {
            val document = questionCollection.document(id).get().await()
            QuestionFire.fromDocument(document)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun addQuestion(questionFire: QuestionFire): String? {
        return try {
            val documentReference = questionCollection.add(questionFire.toMap()).await()
            documentReference.id
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateQuestion(id: String, questionFire: QuestionFire): Boolean {
        return try {
            val updates = questionFire.toMap()
            questionCollection.document(id).update(updates).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteQuestion(id: String): Boolean {
        return try {
            questionCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}