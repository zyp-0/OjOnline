package org.example.builder.question;

import org.example.model.question.QuestionParam;

public class QuestionFactoryProducer {
    public static QuestionFactory getQuestionFactory(int type, QuestionParam questionParam) {
        if (type == 1) {
            return new SingleQuestionFactory(questionParam);
        } else if (type == 2) {
            return new MultiQuestionFactory(questionParam);
        } else if (type == 3) {
            return new CodeQuestionFactory(questionParam);
        }
        return null;
    }
}
