package pt.isec.amov.quizectpamov.ui.screens

import UserViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        Spacer(modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            stringResource(id = R.string.main_menu_title),
            style = WelcomeTitleStyle
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                navController.navigate("quizzesMenu")
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
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
                .padding(vertical = 16.dp)
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
                .padding(vertical = 16.dp)
                .width(290.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(id = R.string.start_quizz), style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                navController.navigate("settings")
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
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