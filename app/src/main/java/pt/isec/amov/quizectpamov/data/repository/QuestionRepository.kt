package pt.isec.amov.quizectpamov.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pt.isec.amov.quizectpamov.data.dtos.QuestionDTO

class QuestionRepository {
    private val db = FirebaseFirestore.getInstance()
    private val questionCollection = db.collection("questions")

    suspend fun addQuestion(question: QuestionDTO): Boolean {
        return try {
            val documentReference = questionCollection.add(question).await()
            documentReference.id // Retorna o ID gerado
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getAllQuestions(): List<QuestionDTO> {
        return try {
            questionCollection.get().await().toObjects(QuestionDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getQuestionById(id: String): QuestionDTO? {
        return try {
            questionCollection.document(id).get().await().toObject(QuestionDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateQuestion(id: String, question: QuestionDTO): Boolean {
        return try {
            questionCollection.document(id).set(question).await()
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