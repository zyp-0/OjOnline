package org.example.builder.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.question.Question;
import org.example.model.question.QuestionParam;

@Data
@AllArgsConstructor
public abstract class QuestionFactory {
    protected QuestionParam questionParam;

    public abstract Question createQuestion();
}
