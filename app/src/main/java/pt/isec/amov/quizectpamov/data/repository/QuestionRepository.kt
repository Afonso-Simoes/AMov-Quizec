package pt.isec.amov.quizectpamov.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pt.isec.amov.quizectpamov.data.model.QuestionFire

class QuestionRepository {
    private val db = FirebaseFirestore.getInstance()
    private val questionCollection = db.collection("questions")

    suspend fun getAllQuestions(): List<QuestionFire>? {
        return try {
            val snapshot = questionCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                if (document.data == null) return null;
                QuestionFire.fromJson(document.id, document.data as Map<String, Object>)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getQuestionById(id: String): QuestionFire? {
        return try {
            val document = questionCollection.document(id).get().await()
            if (document.data == null) return null;
            QuestionFire.fromJson(document.id, document.data as Map<String, Object>)
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

    suspend fun updateQuestion(id: String, questionFire: Map<String, Any?>): Boolean {
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