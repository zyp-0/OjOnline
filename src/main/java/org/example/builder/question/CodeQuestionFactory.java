package org.example.builder.question;

import org.example.model.question.QuestionParam;
import org.example.model.question.CodeQ;
import org.example.model.question.Question;

public class CodeQuestionFactory extends QuestionFactory{
    public CodeQuestionFactory(QuestionParam questionParam) {
        super(questionParam);
    }
    @Override
    public Question createQuestion() {
        CodeQ codeQ = new CodeQ();
        codeQ.setQuestionBasicInfo(questionParam.getQuestionBasicInfo());
        return codeQ;
    }
}
