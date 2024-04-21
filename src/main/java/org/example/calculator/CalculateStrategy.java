package org.example.calculator;

import org.example.model.question.Question;

public interface CalculateStrategy {
    int calculateScore(Question question, String answer);
}
