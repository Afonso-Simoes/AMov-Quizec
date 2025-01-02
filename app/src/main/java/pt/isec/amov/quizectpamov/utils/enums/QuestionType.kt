package pt.isec.amov.quizectpamov.utils.enums

import android.content.Context
import pt.isec.amov.quizectpamov.R

enum class QuestionType {
    TRUE_FALSE;

    fun getLocalizedName(context: Context): String {
        return when (this) {
            TRUE_FALSE -> context.getString(R.string.true_false)
        }
    }
}