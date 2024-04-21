package org.example.builder.question;

import org.example.model.question.QuestionParam;
import org.example.model.question.MultiQ;
import org.example.model.question.Question;
import org.example.utils.AnswerUtil;

import java.util.List;

public class MultiQuestionFactory extends QuestionFactory{
    public MultiQuestionFactory(QuestionParam questionParam) {
        super(questionParam);
    }

    @Override
    public Question createQuestion() {
        MultiQ multiQ = new MultiQ();
        // 获取 设置问题基本信息
        multiQ.setQuestionBasicInfo(questionParam.getQuestionBasicInfo());
        // 获取 设置选项
        String[] options = (String[]) questionParam.getExtra().get("options");
        multiQ.setOptions(options);

        // 将answers值转化为A、B、C等

        String[] answers = (String[]) questionParam.getExtra().get("answers");
        for (int i = 0; i < answers.length; i++) {
            answers[i] = AnswerUtil.optionIntegerToString(answers[i]);
        }
        multiQ.setAnswers(answers);

        // 获取 设置分数 答题模式
        multiQ.setScoreMode((String) questionParam.getExtra().get("scoreMode"));
        multiQ.setFixScore((Integer) questionParam.getExtra().get("fixScore"));
        // 获取 设置部分分数
        List<Integer> partialScore = (List<Integer>) questionParam.getExtra().get("partialScore");
        multiQ.setPartialScore(partialScore);
        return multiQ;
    }
}
