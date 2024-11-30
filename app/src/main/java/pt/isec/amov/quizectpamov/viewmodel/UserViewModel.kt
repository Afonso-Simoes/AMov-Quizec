import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.isec.amov.quizectpamov.data.model.User
import pt.isec.amov.quizectpamov.data.repository.UserRepository

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    ///VERIFICAR SE PRECISO RETUNAR PARA REDIRECIONAR VISTA OU REDIRECIONAR AQUI NAO SEI
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoggedIn.value = userRepository.logIn(email, password)
            } catch (e: Exception) {
                _errorMessage.value = "Erro: ${e.message}"
            }
        }
    }

    ///VERIFICAR SE PRECISO RETUNAR PARA REDIRECIONAR VISTA OU REDIRECIONAR AQUI NAO SEI
    fun signIn(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoggedIn.value = userRepository.signUp(
                    User(
                        id = "",
                        name = name,
                        email = email,
                        password = password
                    )
                )

                if (!_isLoggedIn.value) {
                    _errorMessage.value = "Erro no registro"
                } else {
                    _errorMessage.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erro: ${e.message}"
            }
        }
    }
}
