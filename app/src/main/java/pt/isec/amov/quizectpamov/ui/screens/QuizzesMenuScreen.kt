package pt.isec.amov.quizectpamov.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import pt.isec.amov.quizectpamov.viewmodel.UserViewModel

@Composable
fun QuizzesMenuScreen (
    navController: NavHostController,
    userViewModel: UserViewModel
){
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    ShowQuizzesMenuScreen(navController, isLandscape = isLandscape)
}

@Composable
fun ShowQuizzesMenuScreen(
    navController: NavHostController,
    isLandscape: Boolean
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Text(
            stringResource(id = R.string.main_menu_quizzes_title),
            style = WelcomeTitleStyle,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = if (isLandscape) 32.dp else 64.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate("createQuiz")
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
                Text(
                    stringResource(id = R.string.create_quiz),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                onClick = {
                    navController.navigate("editQuiz")
                },
                modifier = Modifier
                    .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                    .width(290.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(id = R.string.edit_quiz),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                onClick = {
                    navController.navigate("duplicateQuiz")
                },
                modifier = Modifier
                    .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                    .width(290.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(id = R.string.duplicate_quiz),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                onClick = {
                    navController.navigate("deleteQuiz")
                },
                modifier = Modifier
                    .padding(vertical = if (isLandscape) 8.dp else 16.dp)
                    .width(290.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(id = R.string.delete_quiz),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(id = R.string.navigate_back),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Button(
                onClick = { navController.navigate("loginScreen") },
                //TODO: Implementar logout
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(id = R.string.logout),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}