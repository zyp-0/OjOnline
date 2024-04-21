package org.example.builder.question;

import org.example.model.question.QuestionParam;
import org.example.model.question.CodeQ;
import org.example.model.question.Question;

import java.util.Map;

public class CodeQuestionFactory extends QuestionFactory{
    public CodeQuestionFactory(QuestionParam questionParam) {
        super(questionParam);
    }
    @Override
    public Question createQuestion() {
        CodeQ codeQ = new CodeQ();
        // 获取 设置问题基本信息
        codeQ.setQuestionBasicInfo(questionParam.getQuestionBasicInfo());
        // 设置 时间限制
        codeQ.setTimeLimit((Long) questionParam.getExtra().get("timeLimit"));
        // 设置 样例
        Map<String, String> samples = (Map<String, String>) questionParam.getExtra().get("samples");
        codeQ.setSamples(samples);

        return codeQ;
    }
}