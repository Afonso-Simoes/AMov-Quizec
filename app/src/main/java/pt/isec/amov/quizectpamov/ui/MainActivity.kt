package pt.isec.amov.quizectpamov.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pt.isec.amov.quizectpamov.Quizec
import pt.isec.amov.quizectpamov.ui.screens.AppNavigation
import pt.isec.amov.quizectpamov.ui.theme.QuizecTPTheme

class MainActivity : ComponentActivity() {
    private val app by lazy { application as Quizec }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizecTPTheme {
                AppNavigation()
            }
        }
    }
}