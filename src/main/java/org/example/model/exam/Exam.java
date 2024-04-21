package org.example.model.exam;

import lombok.Data;
import org.example.model.question.Question;

import java.util.ArrayList;
import java.util.List;
@Data
public class Exam {
    private String id;
    private String title;
    private long startTime;
    private long endTime;
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }
}
