package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel

@Composable
fun StartQuizScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    ShowStartQuizScreen(navController, isLandscape = isLandscape)
}

@Composable
fun ShowStartQuizScreen(navController: NavHostController, isLandscape: Boolean) {
    var gameCode = remember { mutableStateOf("") }

    val titlePadding = if (isLandscape) 32.dp else 16.dp
    val inputPadding = if (isLandscape) 32.dp else 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.start_quizz),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = titlePadding)
        )

        TextField(
            value = gameCode.value,
            onValueChange = { newCode -> gameCode.value = newCode },
            label = { Text(stringResource(id = R.string.enter_game_code)) },
            modifier = Modifier
                .padding(bottom = inputPadding)
                .let {
                    if (isLandscape) it.width(300.dp)
                    else it
                }
        )

        val buttonModifier = if (isLandscape) {
            Modifier
                .padding(top = 16.dp)
                .width(200.dp)
                .height(40.dp)
        } else {
            Modifier
                .padding(top = 16.dp)
        }

        Button(
            onClick = {
                if (gameCode.value.isNotEmpty()) {
                    navController.navigate("quiz/${gameCode.value}")
                } else {
                    // TODO: Mostrar mensagem de erro
                }
            },
            modifier = buttonModifier
        ) {
            Text(text = stringResource(id = R.string.start_quizz), fontSize = 16.sp)
        }
    }
}
