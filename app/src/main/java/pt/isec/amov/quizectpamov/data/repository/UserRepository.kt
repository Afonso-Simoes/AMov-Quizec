package pt.isec.amov.quizectpamov.data.repository

import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository {

    private val auth by lazy { Firebase.auth }
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    fun createUserWithEmail(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                onResult(result.exception)
            }
    }

    fun signInWithEmail(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                onResult(result.exception)
            }
    }

    fun signOut() {
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }
}