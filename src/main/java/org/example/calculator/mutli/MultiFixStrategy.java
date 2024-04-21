package org.example.calculator.mutli;

import org.example.calculator.CalculateStrategy;
import org.example.model.question.MultiQ;
import org.example.model.question.Question;

import java.util.Arrays;

/**
 * fix score strategy
 * 答案对部分正确，给定fix score分数
 */
public class MultiFixStrategy implements CalculateStrategy {
    @Override
    public int calculateScore(Question question, String answer) {
        if (answer == null){
            return 0;
        }
        MultiQ multiQ = (MultiQ) question;
        int score = 0;
        String rightAnswer = Arrays.stream(multiQ.getAnswers()).sorted().reduce("", String::concat);
        if (rightAnswer.equals(answer)) {
            score = multiQ.getScore();
        } else if (rightAnswer.contains(answer)) {
            score = multiQ.getFixScore();
        }
        return score;
    }
}
