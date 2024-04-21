package org.example.calculator.single;

import org.example.calculator.CalculateStrategy;
import org.example.model.question.Question;
import org.example.model.question.SingleQ;

/**
 * 单选题计分策略

 */
public class SingleStrategy implements CalculateStrategy {
    @Override
    public int calculateScore(Question question, String answer) {
        SingleQ singleQ = (SingleQ) question;
        if (singleQ.getAnswer().equals(answer)) {
            return singleQ.getScore();
        }
        return 0;
    }
}
