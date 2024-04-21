package org.example.Executor;

import org.example.model.question.Question;

public interface CalculateStrategy {
    int calculateScore(Question question, String answer);
}
