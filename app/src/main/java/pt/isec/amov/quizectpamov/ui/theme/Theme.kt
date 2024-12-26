package pt.isec.amov.quizectpamov.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = YellowKahoot,
    secondary = BlueKahoot,
    tertiary = GreenKahoot,
    inversePrimary = RedKahoot,
    background = DarkBackground,
    onSurfaceVariant = PurpleKahoot,
    error = RedError
)

private val LightColorScheme = lightColorScheme(
    primary = YellowKahoot,
    secondary = BlueKahoot,
    tertiary = GreenKahoot,
    inversePrimary = RedKahoot,
    background = GrayKahoot,
    onSurfaceVariant = PurpleKahoot,
    error = RedError
)

@Composable
fun QuizecTPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}