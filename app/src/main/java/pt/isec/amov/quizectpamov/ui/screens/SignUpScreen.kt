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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle

@Composable
fun SignUpScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    ShowSingUpScreen(navController, userViewModel, isLandscape = isLandscape)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSingUpScreen(navController: NavHostController, userViewModel: UserViewModel, isLandscape: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }

        val errorMessage = stringResource(id = R.string.register_error)

        Text(
            text = "Create an account",
            style = WelcomeTitleStyle,
            modifier = Modifier
                .padding(vertical = if (isLandscape) 32.dp else 64.dp)
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
            value = name,
            onValueChange = { newName -> name = newName },
            label = { Text(stringResource(id = R.string.name_label)) },
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
                userViewModel.createUserWithEmail(email, password){
                        isUserLogged ->
                    if(isUserLogged){
                        navController.navigate("login")
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
            Text(stringResource(id = R.string.create_button), style = MaterialTheme.typography.bodyLarge)
        }
    }
}