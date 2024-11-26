package pt.isec.amov.quizectpamov.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Quizec!",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                letterSpacing = 0.sp,
                lineHeight = 36.sp
            ),
            modifier = Modifier.padding(vertical = 64.dp)
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Enter your name") },
            modifier = Modifier.padding(vertical = 16.dp)
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Enter your email") },
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(
            onClick = {
                /* Handle button click */
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Login")
        }
    }
}