package pt.isec.amov.quizectpamov.data.model

import com.google.firebase.firestore.DocumentSnapshot
import pt.isec.amov.quizectpamov.utils.enums.QuestionType

data class QuestionFire(
    val id: String? = null,
    val questionID: String,
    val created_at: Long,
    val created_by: String,
    val type: QuestionType,
    val data: BaseQuestion
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "questionID" to questionID,
            "created_at" to created_at,
            "created_by" to created_by,
            "type" to type.ordinal,
            "data" to data.toMap()
        )
    }

    companion object {
        fun fromJson(id: String, data: Map<String, Any>): QuestionFire? {
            val id = id
            val questionID = data["questionID"] as? String ?: return null
            val createdAt = data["created_at"] as? Long ?: return null
            val createdBy = data["created_by"] as? String ?: return null
            val typeOrdinal = data["type"] as? Long ?: return null
            val type = QuestionType.values().getOrNull(typeOrdinal.toInt()) ?: return null
            val dataMap = data["data"] as? Map<String, Any> ?: return null

            val question = when (type) {
                QuestionType.TRUE_FALSE -> TrueFalseQuestion.fromMap(dataMap)
                QuestionType.SINGLE_CHOICE -> MultipleChoiceSingleAnswerQuestion.fromMap(dataMap)
                QuestionType.MULTIPLE_CHOICE -> MultipleChoiceMultipleAnswerQuestion.fromMap(dataMap)
                QuestionType.MATCHING -> MatchingQuestion.fromMap(dataMap)
                QuestionType.ORDERING -> OrderingQuestion.fromMap(dataMap)
                QuestionType.FILL_IN_THE_BLANK -> FillInTheBlankQuestion.fromMap(dataMap)
                QuestionType.ASSOCIATION -> AssociationQuestion.fromMap(dataMap)
                QuestionType.WORD_BASED -> WordBasedQuestion.fromMap(dataMap)
            } ?: return null

            return QuestionFire(id, questionID, createdAt, createdBy, type, question)
        }
    }
}

interface BaseQuestion {
    val question: String
    fun toMap(): Map<String, Any?>
}

data class TrueFalseQuestion(
    override var question: String,
    var correctAnswer: Boolean
) : BaseQuestion {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "correctAnswer" to correctAnswer
        )
    }

    companion object {
        fun fromMap(data: Map<String, Any>): TrueFalseQuestion {
            val question = data["question"] as String
            val correctAnswer = data["correctAnswer"] as Boolean
            return TrueFalseQuestion(question, correctAnswer)
        }
    }
}

data class MultipleChoiceSingleAnswerQuestion(
    override val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
) : BaseQuestion {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "options" to options,
            "correctAnswerIndex" to correctAnswerIndex
        )
    }

    companion object {
        fun fromMap(data: Map<String, Any>): BaseQuestion {
            val question = data["question"] as String
            val options = data["options"] as List<String>
            val correctAnswerIndex = data["correctAnswerIndex"].toString().toInt()
            return MultipleChoiceSingleAnswerQuestion(question, options, correctAnswerIndex)
        }
    }
}

data class MultipleChoiceMultipleAnswerQuestion(
    override val question: String,
    val options: List<String>,
    val correctAnswerIndexes: List<Int>
) : BaseQuestion {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "options" to options,
            "correctAnswerIndices" to correctAnswerIndexes
        )
    }

    companion object {
        fun fromMap(data: Map<String, Any>): BaseQuestion {
            val question = data["question"] as String
            val options = data["options"] as List<String>
            val correctAnswerIndexes = data["correctAnswerIndices"] as List<Int>
            return MultipleChoiceMultipleAnswerQuestion(question, options, correctAnswerIndexes)
        }
    }
}

data class MatchingQuestion(
    override val question: String,
    val leftOptions: List<String>,
    val rightOptions: List<String>,
    val correctMatches: List<Pair<Int, Int>>
) : BaseQuestion {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "leftOptions" to leftOptions,
            "rightOptions" to rightOptions,
            "correctMatches" to correctMatches
        )
    }

    companion object {
        fun fromMap(data: Map<String, Any>): BaseQuestion {
            val question = data["question"] as String
            val leftOptions = data["leftOptions"] as List<String>
            val rightOptions = data["rightOptions"] as List<String>
            val correctMatches = data["correctMatches"] as List<Pair<Int, Int>>
            return MatchingQuestion(question, leftOptions, rightOptions, correctMatches)
        }
    }
}

data class OrderingQuestion(
    override val question: String,
    val options: List<String>,
    val correctOrder: List<Int>
) : BaseQuestion {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "options" to options,
            "correctOrder" to correctOrder
        )
    }

    companion object {
        fun fromMap(data: Map<String, Any>): BaseQuestion {
            val question = data["question"] as String
            val options = data["options"] as List<String>
            val correctOrder = data["correctOrder"] as List<Int>
            return OrderingQuestion(question, options, correctOrder)
        }
    }
}

data class FillInTheBlankQuestion(
    override val question: String,
    val correctAnswers: List<String>
) : BaseQuestion {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "correctAnswers" to correctAnswers
        )
    }

    companion object {
        fun fromMap(data: Map<String, Any>): BaseQuestion {
            val question = data["question"] as String
            val correctAnswers = data["correctAnswers"] as List<String>
            return FillInTheBlankQuestion(question, correctAnswers)
        }
    }
}

data class AssociationQuestion(
    override val question: String,
    val leftOptions: List<String>,
    val rightOptions: List<String>,
    val correctMatches: List<Pair<Int, Int>>
) : BaseQuestion {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "leftOptions" to leftOptions,
            "rightOptions" to rightOptions,
            "correctMatches" to correctMatches
        )
    }

    companion object {
        fun fromMap(data: Map<String, Any>): BaseQuestion {
            val question = data["question"] as String
            val leftOptions = data["leftOptions"] as List<String>
            val rightOptions = data["rightOptions"] as List<String>
            val correctMatches = data["correctMatches"] as List<Pair<Int, Int>>
            return AssociationQuestion(question, leftOptions, rightOptions, correctMatches)
        }
    }
}

data class WordBasedQuestion(
    override val question: String,
    val initialAnswerText: String,
    val answers: List<String>
) : BaseQuestion {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "question" to question,
            "initialAnswerText" to initialAnswerText,
            "answers" to answers
        )
    }

    companion object {
        fun fromMap(data: Map<String, Any>): BaseQuestion {
            val question = data["question"] as String
            val initialAnswerText = data["initialAnswerText"] as String
            val answers = data["answers"] as List<String>
            return WordBasedQuestion(question, initialAnswerText, answers)
        }
    }
}