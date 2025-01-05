package pt.isec.amov.quizectpamov.ui.screens

sealed class SaveState {
    object Idle : SaveState()
    object Loading : SaveState()
    data class Success(val documentId: String) : SaveState()
    data class Error(val message: String) : SaveState()
}