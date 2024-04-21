package org.example.calculator;

import lombok.Setter;
import org.example.model.answer.Answer;
import org.example.model.exam.Exam;
import org.example.model.question.Question;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 计算答案得分
 */

public class AnswerCal {
    @Setter
    private Map<String, Exam> exams;

    private static AnswerCal INSTANCE;

    static {
        INSTANCE = new AnswerCal();
    }

    public static AnswerCal getInstance() {
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
            int type = question.getQuestionBasicInfo().getType();

            CalculateStrategy calculateStrategy = StrategyFactory.getStrategy(question);
            CalculateScorer calculateScorer = new CalculateScorer(calculateStrategy);
            int score = calculateScorer.calculateScore(question, answer);

            res.addAndGet(score);
        });
        return res.get();
    }
}
