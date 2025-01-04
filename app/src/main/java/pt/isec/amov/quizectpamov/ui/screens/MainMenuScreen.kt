package pt.isec.amov.quizectpamov.ui.screens

import pt.isec.amov.quizectpamov.viewmodel.UserViewModel
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle


@Composable
fun MainMenuScreen(
    navController: NavHostController
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    ShowMainMenuScreen(navController, isLandscape = isLandscape)
}

@Composable
fun ShowMainMenuScreen(navController: NavHostController, isLandscape: Boolean) {
    /*
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val annotatedString = buildAnnotatedString {
        append("Don't have an account? ")
        withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = "SignUp", annotation = "SignUp")
            append("Sign up")
            pop()
        }
    }
    */
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(32.dp))

        Text(
            stringResource(id = R.string.main_menu_title),
            style = WelcomeTitleStyle,
            modifier = Modifier.padding(vertical = if (isLandscape) 32.dp else 64.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = {
                navController.navigate("quizzesMenu")
            },
            modifier = Modifier
                .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                .width(290.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(id = R.string.manage_quizzes), style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                navController.navigate("questionsMenu")
            },
            modifier = Modifier
                .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                .width(290.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(id = R.string.manage_questions), style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                navController.navigate("startQuiz")
            },
            modifier = Modifier
                .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                .width(290.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(id = R.string.start_quizz_title), style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                navController.navigate("settings")
            },
            modifier = Modifier
                .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                .width(290.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(id = R.string.settings), style = MaterialTheme.typography.bodyLarge)
        }
    }
}