package org.example.Executor;

import lombok.Setter;
import org.example.model.answer.Answer;
import org.example.model.exam.Exam;
import org.example.model.question.Question;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 计算答案得分 的执行类
 */

public class AnswerCalExecutor {
    @Setter
    private Map<String, Exam> exams;

    private static AnswerCalExecutor INSTANCE;

    static {
        INSTANCE = new AnswerCalExecutor();
    }

    public static AnswerCalExecutor getInstance() {
        return INSTANCE;
    }

    public Integer calculate(Answer Answer) {
        AtomicInteger res = new AtomicInteger();
        Long time = Answer.getSubmitTime();
        // 判断作答是否在考试时间内
        if (time > exams.get(Answer.getExamId()).getEndTime() || time < exams.get(Answer.getExamId()).getStartTime())
            return 0;

        // 把exam.getQuestions(){List<Question>}变为Map<String, Question>，key为id
        Map<String, Question> questionMap = exams.get(Answer.getExamId()).getQuestions().stream().collect(Collectors.toMap(Question::getId, Function.identity()));
        questionMap.forEach((id, question) -> {
            String answer = Answer.getAnswers().get(id);
            // 根据题目类型获取对应的策略
            CalculateStrategy calculateStrategy = StrategyFactory.getStrategy(question);
            CalculateScorer calculateScorer = new CalculateScorer(calculateStrategy);
            int score = calculateScorer.calculateScore(question, answer);

            res.addAndGet(score);
        });
        return res.get();
    }
}
