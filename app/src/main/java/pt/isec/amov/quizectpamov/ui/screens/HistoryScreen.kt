package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.quizectpamov.R
import pt.isec.amov.quizectpamov.ui.theme.WelcomeTitleStyle
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel

@Composable
fun HistoryScreen(navController: NavHostController, userViewModel: UserViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) 100.dp else 16.dp,
                vertical = 35.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.history_screen_title),
            style = WelcomeTitleStyle,
            modifier = Modifier.padding(bottom = 32.dp, top = 40.dp)
        )
    }

    //TODO: Listar o historico de jogos
}