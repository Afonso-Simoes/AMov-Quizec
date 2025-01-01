package pt.isec.amov.quizectpamov.data.model

class TrueFalseQuestionModel(
    id: String? = null,
    questionText: String,
    override val correctAnswer: Boolean
) : QuestionBase(id, questionText, "True/False", correctAnswer)