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
        fun fromDocument(document: DocumentSnapshot): QuestionFire? {
            val data = document.data ?: return null

            val id = document.id
            val questionID = data["questionID"] as? String ?: return null
            val createdAt = data["created_at"] as? Long ?: return null
            val createdBy = data["created_by"] as? String ?: return null
            val typeOrdinal = data["type"] as? Long ?: return null
            val type = QuestionType.values().getOrNull(typeOrdinal.toInt()) ?: return null
            val dataMap = data["data"] as? Map<String, Any> ?: return null

            val question = when (type) {
                QuestionType.TRUE_FALSE -> TrueFalseQuestion.fromMap(dataMap)
                QuestionType.MULTIPLE_CHOICE -> MultipleChoiceSingleAnswerQuestion.fromMap(dataMap)
                QuestionType.SINGLE_CHOICE -> TODO()
                QuestionType.MATCHING -> TODO()
                QuestionType.ORDERING -> TODO()
                QuestionType.FILL_IN_THE_BLANK -> TODO()
                QuestionType.ASSOCIATION -> TODO()
                QuestionType.WORD_BASED -> TODO()
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
            val correctAnswerIndex = data["correctAnswerIndex"] as Int
            return MultipleChoiceSingleAnswerQuestion(question, options, correctAnswerIndex)
        }
    }
}