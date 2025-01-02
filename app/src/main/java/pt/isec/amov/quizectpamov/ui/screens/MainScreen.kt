package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle

@Composable
fun MainScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    ShowMainScreen(navController, isLandscape = isLandscape, userViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMainScreen(navController: NavHostController, isLandscape: Boolean, userViewModel: UserViewModel) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) } // Controla o estado do dialog

    val errorMessage = stringResource(id = R.string.login_error)

    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.signup_prompt) + " ")
        withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = "SignUp", annotation = "SignUp")
            append(stringResource(id = R.string.signup_action))
            pop()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_message),
            style = WelcomeTitleStyle,
            modifier = Modifier.padding(vertical = if (isLandscape) 32.dp else 64.dp)
        )

        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text(stringResource(id = R.string.email_label)) },
            modifier = Modifier
                .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                focusedLabelColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            )
        )
        TextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            label = { Text(stringResource(id = R.string.pass_label)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                focusedLabelColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            )
        )

        Button(
            onClick = {
                userViewModel.signInWithEmail(email, password) { isUserCreated ->
                    if (isUserCreated) {
                        navController.navigate("mainMenu")
                    } else {
                        isError = true
                    }
                }
            },
            modifier = Modifier
                .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                .width(290.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Text(stringResource(id = R.string.enter_button), style = MaterialTheme.typography.bodyLarge)
        }

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        navController.navigate("signup")
                    }
            },
            modifier = Modifier.padding(vertical = if (isLandscape) 32.dp else 64.dp)
        )

        Button(
            onClick = { navController.navigate("credits") },
            modifier = Modifier
                .padding(top = 100.dp)
                .width(290.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(id = R.string.credits_button), style = MaterialTheme.typography.bodyLarge)
        }

    }

}
