package pt.isec.amov.quizectpamov.utils.enums

import android.content.Context
import pt.isec.amov.quizectpamov.R

enum class QuestionType {
    TRUE_FALSE,
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    MATCHING,
    ORDERING,
    FILL_IN_THE_BLANK,
    ASSOCIATION,
    WORD_BASED;

    fun getLocalizedName(context: Context): String {
        return when (this) {
            TRUE_FALSE -> context.getString(R.string.true_false)
            SINGLE_CHOICE -> context.getString(R.string.multiple_choice_single_answer)
            MULTIPLE_CHOICE -> context.getString(R.string.multiple_choice_multiple_answers)
            MATCHING -> context.getString(R.string.matching)
            ORDERING -> context.getString(R.string.ordering)
            FILL_IN_THE_BLANK -> context.getString(R.string.fill_in_the_blank)
            ASSOCIATION -> context.getString(R.string.association)
            WORD_BASED -> context.getString(R.string.word_based_response)
        }
    }

}