package org.example.calculator.mutli;

import org.example.calculator.CalculateStrategy;
import org.example.model.question.Question;
import org.example.model.question.MultiQ;

import java.util.Arrays;

public class MultiNotingStrategy implements CalculateStrategy {
    @Override
    public int calculateScore(Question question, String answer) {
        if (answer == null) {
            return 0;
        }
        MultiQ multiQ = (MultiQ) question;
        int score = 0;
        String rightAnswer = Arrays.stream(multiQ.getAnswers()).sorted().reduce("", String::concat);
        if (rightAnswer.equals(answer)) {
            score = multiQ.getScore();
        }
        return score;
    }
}
