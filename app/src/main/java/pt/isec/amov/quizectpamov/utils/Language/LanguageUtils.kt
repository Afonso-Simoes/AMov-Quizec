package pt.isec.amov.quizectpamov.utils.Language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.util.Locale

@Composable
fun getCurrentStrings(): Map<String, String> {
    val currentLanguage = remember { Locale.getDefault().language }
    return when (currentLanguage) {
        "en" -> AppTranslations.en
        else -> AppTranslations.pt
    }
}
