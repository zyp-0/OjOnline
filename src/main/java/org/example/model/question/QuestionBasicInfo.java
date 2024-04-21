package org.example.model.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBasicInfo{
    private String id;
    private int type;
    private String question;
    private int points;
}
