package org.example.Executor;

import lombok.AllArgsConstructor;
import org.example.model.question.Question;

/**
 * 计分器
 * 用于计算 答题得分
 * 通过策略模式实现
 */
@AllArgsConstructor
public class CalculateScorer {
    CalculateStrategy calculateStrategy;
    public int calculateScore(
            Question question, String answer) {
        return calculateStrategy.calculateScore(question, answer);
    }
}
