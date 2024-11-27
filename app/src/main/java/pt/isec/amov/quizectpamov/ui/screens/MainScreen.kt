package pt.isec.amov.quizectpamov.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle
import pt.isec.amov.quizectpamov.utils.Language.getCurrentStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val strings = getCurrentStrings()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        Text(
            text = strings["welcome"] ?: "Welcome!",
            style = WelcomeTitleStyle,
            modifier = Modifier.padding(vertical = 64.dp)
        )
        TextField(
            value = name,
            onValueChange = { newName -> name = newName },
            label = { Text(strings["enter_name"] ?: "Enter your name") },
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
        Button(
            onClick = {
                /* Handle button click */
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
    }
}
