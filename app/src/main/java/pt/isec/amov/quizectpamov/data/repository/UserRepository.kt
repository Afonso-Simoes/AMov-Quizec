package pt.isec.amov.quizectpamov.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pt.isec.amov.quizectpamov.data.model.User


class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun logIn(email: String, password: String): Boolean {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun signUp(user: User): Boolean {
        try {
            val result = auth.createUserWithEmailAndPassword(user.email, user.name).await()
            user.id = result.user!!.uid
            firestore.collection("users").document(user.id).set(user).await()
            return true
        } catch (e: Exception) {
            return false
        }
    }
}