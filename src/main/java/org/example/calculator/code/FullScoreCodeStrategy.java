package org.example.calculator.code;

import org.example.calculator.CalculateStrategy;
import org.example.model.question.Question;

public class FullScoreCodeStrategy implements CalculateStrategy {
    @Override
    public int calculateScore(Question question, String answer) {
        return question.getScore();
    }
}
