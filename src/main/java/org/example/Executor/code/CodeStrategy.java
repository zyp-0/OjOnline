package org.example.Executor.code;

import org.example.Executor.CalculateStrategy;
import org.example.model.question.CodeQ;
import org.example.model.question.Question;
import org.example.utils.ComplexityCalculator;
import org.example.utils.ComplexityDAO;
import org.example.utils.codeProcessor.JavaCodeProcessor;

public class CodeStrategy implements CalculateStrategy {
    @Override
    public int calculateScore(Question question, String answer) {
        if (answer == null){
            return 0;
        }
        String examId = question.getQuestionBasicInfo().getExamId();
        String[] split = answer.split("Solution");
        String Id = split[1].split("\\.")[0];
        String studentId = Id.replaceFirst(examId, "");
        String qId = question.getId();

        CodeQ codeQ = (CodeQ) question;
        JavaCodeProcessor javaCodeProcessor = new JavaCodeProcessor();
        try {
            javaCodeProcessor.preprocess(answer);

            for (String input : codeQ.getSamples().keySet()) {
                String output = codeQ.getSamples().get(input);
                try {
                    String result = javaCodeProcessor.execute(answer.substring(answer.lastIndexOf("/") + 1, answer.lastIndexOf(".")), new String[]{input}, codeQ.getTimeLimit());
                    // 任一样例不通过则返回0分
                    if (!output.equals(result)) {
                        return 0;
                    }
                } catch (RuntimeException e) {
                    // 捕获超时异常，将分数设为0
                    return 0;
                }
            }
        } catch (Exception e) {
            // 捕获编译错误异常，将分数设为0
            e.printStackTrace();
            return 0;
        }

        // 计算复杂度
        ComplexityCalculator complexityCalculator = new ComplexityCalculator();
        int complexity = complexityCalculator.evaluate(answer);
        ComplexityDAO.getInstance().saveComplexity(examId, studentId, qId, complexity);
        return question.getScore();
    }
}
