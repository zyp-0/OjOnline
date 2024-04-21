package org.example.calculator;

import org.example.calculator.code.FullScoreCodeStrategy;
import org.example.calculator.mutli.MultiFixStrategy;
import org.example.calculator.mutli.MultiNotingStrategy;
import org.example.calculator.mutli.MultiPartialStrategy;
import org.example.calculator.single.SingleStrategy;
import org.example.model.question.MultiQ;
import org.example.model.question.Question;

public class StrategyFactory {
    public static CalculateStrategy getStrategy(Question question) {
        if (question.getQuestionBasicInfo().getType() == 1) {
            return new SingleStrategy();
        } else if (question.getQuestionBasicInfo().getType() == 3) {
            return new FullScoreCodeStrategy();
        } else if (question.getQuestionBasicInfo().getType() == 2) {
            MultiQ multiQ = (MultiQ) question;
            String mode = multiQ.getScoreMode();
            switch (mode) {
                case "nothing":
                    return new MultiNotingStrategy();
                case "fix":
                    return new MultiFixStrategy();
                case "partial":
                    return new MultiPartialStrategy();
                default:
                    System.err.println("未知的多选题评分模式");
                    break;
            }
        } else {
            System.err.println("未知的题目类型");
        }
        return null;
    }
}
