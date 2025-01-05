package pt.isec.amov.quizectpamov.data.repository

import QuizFire
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuizRepository {
    private val db = FirebaseFirestore.getInstance()
    private val quizCollection = db.collection("quizzes")

    suspend fun getAllQuizzes(): List<QuizFire>? {
        return try {
            val snapshot = quizCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                document.data?.let { QuizFire.fromMap(document.id, it as Map<String, Any>) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getQuizById(id: String): QuizFire? {
        return try {
            val document = quizCollection.document(id).get().await()
            document.data?.let { QuizFire.fromMap(document.id, it as Map<String, Any>) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun addQuiz(quizFire: QuizFire): String? {
        return try {
            val documentReference = quizCollection.add(quizFire.toMap()).await()
            documentReference.id
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateQuiz(id: String, quizFire: Map<String, Any?>): Boolean {
        return try {
            quizCollection.document(id).update(quizFire).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteQuiz(id: String): Boolean {
        return try {
            quizCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
