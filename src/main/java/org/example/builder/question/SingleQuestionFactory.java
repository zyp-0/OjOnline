package org.example.builder.question;

import org.example.model.question.Question;
import org.example.model.question.SingleQ;
import org.example.utils.AnswerUtil;
import org.example.model.question.QuestionParam;

public class SingleQuestionFactory extends QuestionFactory{
    public SingleQuestionFactory(QuestionParam questionParam) {
        super(questionParam);
    }

    @Override
    public Question createQuestion() {
        SingleQ singleQ = new SingleQ();
        // 设置基本信息
        singleQ.setQuestionBasicInfo(questionParam.getQuestionBasicInfo());
        // 设置选项
        String[] options = (String[]) questionParam.getExtra().get("options");
        singleQ.setOptions(options);
        // 设置答案
        singleQ.setAnswer(AnswerUtil.optionIntegerToString((String) questionParam.getExtra().get("answer")));
        return singleQ;
    }
}
