package pt.isec.amov.quizectpamov.ui.screens

import UserViewModel
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle
import pt.isec.amov.quizectpamov.utils.Language.getCurrentStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val strings = getCurrentStrings()
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

        Text(
            text = strings["welcome"] ?: "Welcome!",
            style = WelcomeTitleStyle,
            modifier = Modifier
                .padding(vertical = 64.dp)
        )
        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text(strings["enter_email"] ?: "Enter your email") },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )
        TextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            label = { Text(strings["enter_password"] ?: "Enter your password") },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )
        Button(
            onClick = {
                userViewModel.signIn(email, password);
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .width(290.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Text(strings["login"] ?: "Login", style = MaterialTheme.typography.bodyLarge)
        }
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        navController.navigate("signup")
                    }
            },
            modifier = Modifier
                .padding(vertical = 64.dp)
        )
    }
}
