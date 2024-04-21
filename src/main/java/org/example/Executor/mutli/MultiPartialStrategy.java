package org.example.Executor.mutli;

import org.example.Executor.CalculateStrategy;
import org.example.model.question.MultiQ;
import org.example.model.question.Question;
import org.example.utils.AnswerUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 在partial给分模式下，按照partialScore给定的部分分确定得分，
 * ⽐如partialScore为[5, 10, 5]
 * 回答BD时得 10+5=15 分。
 */
public class MultiPartialStrategy implements CalculateStrategy {
    @Override
    public int calculateScore(Question question, String answer) {
        if (answer == null) {
            return 0;
        }
        MultiQ multiQ = (MultiQ) question;
        int score = 0;
        // optionsNum 代表有几个选项，比如有4个，那么question和answer就从A比到B，question里有，answer里没有的就是错的；question里没有，answer里有的，直接0分
        int optionNum = multiQ.getOptions().length;
        String rightAnswer = Arrays.stream(multiQ.getAnswers()).sorted().reduce("", String::concat);
        int index = 0;
        Map<String, Integer> partialScore = new HashMap<>();
        // answer 是abd，score 是 1 2 2 一一对应的，不是完整的4个选项
        for (int i = 0; i < optionNum; i++) {
            String o = AnswerUtil.integerToOptionString(i);
            if (rightAnswer.contains(o)) {
                partialScore.put(o, multiQ.getPartialScore().get(index));
                index++;
            }
        }
        for (int i = 0; i < optionNum; i++) {
            String o = AnswerUtil.integerToOptionString(i);
            if (rightAnswer.contains(o) && answer.contains(o)) {
                score += partialScore.get(o);
            } else if (!rightAnswer.contains(o) && answer.contains(o)) {
                return 0;
            }
        }
        return score;
    }
}
