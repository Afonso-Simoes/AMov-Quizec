package pt.isec.amov.quizectpamov.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.isec.amov.quizectpamov.Quizec
import pt.isec.amov.quizectpamov.ui.screens.MainScreen
import pt.isec.amov.quizectpamov.ui.theme.QuizecTPTheme

class MainActivity : ComponentActivity() {
    private val app by lazy { application as Quizec }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizecTPTheme {
                MainScreen()
            }
        }
    }
}