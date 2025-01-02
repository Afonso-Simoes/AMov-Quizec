package pt.isec.amov.quizectpamov.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import pt.isec.amov.quizectpamov.data.dtos.UserDTO
import pt.isec.amov.quizectpamov.data.repository.UserRepository

fun FirebaseUser.toUser() : UserDTO {
    val displayName = this.displayName ?: ""
    val strEmail = this.email ?: "n.d."
    return UserDTO(displayName,strEmail)
}

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _user = mutableStateOf(userRepository.currentUser?.toUser())
    val user : MutableState<UserDTO?>
        get() = _user

    private val _error = mutableStateOf<String?>(null)
    val error : MutableState<String?>
        get() = _error

    fun createUserWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onResult(false) // Return false for invalid input
            return
        }

        viewModelScope.launch {
            userRepository.createUserWithEmail(email, password) { exception ->
                if (exception == null) {
                    _user.value = userRepository.currentUser?.toUser()
                    onResult(true) // Return true if successful
                } else {
                    _error.value = exception.message
                    onResult(false) // Return false in case of an error
                }
            }
        }
    }

    fun signInWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        if (email.isBlank() || password.isBlank()){
            onResult(false) // Return false for invalid input
            return
        }
        viewModelScope.launch {
            userRepository.signInWithEmail(email, password) { exception ->
                if (exception == null) {
                    _user.value = userRepository.currentUser?.toUser()
                    onResult(true)
                } else {
                    _error.value = exception.message
                    onResult(false)
                }
            }
        }
    }


    fun signOut() {
        userRepository.signOut()
        _user.value = null
        _error.value = null
    }

}
