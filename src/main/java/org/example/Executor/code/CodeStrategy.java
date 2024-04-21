package org.example.Executor.code;

import org.example.Executor.CalculateStrategy;
import org.example.model.question.CodeQ;
import org.example.model.question.Question;
import org.example.utils.codeProcessor.JavaCodeProcessor;

public class CodeStrategy implements CalculateStrategy {
    @Override
    public int calculateScore(Question question, String answer) {
        if (answer == null){
            return 0;
        }

        CodeQ codeQ = (CodeQ) question;
        JavaCodeProcessor javaCodeProcessor = new JavaCodeProcessor();
        try {
            javaCodeProcessor.preprocess(answer);
            for (String input : codeQ.getSamples().keySet()) {
                String output = codeQ.getSamples().get(input);
                String result = javaCodeProcessor.execute(answer.substring(answer.lastIndexOf("/")+1 ,answer.lastIndexOf(".")), new String[]{input});
                // 任一样例不通过则返回0分
                if (!output.equals(result)) {
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return question.getScore();
    }
}
