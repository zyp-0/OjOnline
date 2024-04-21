package org.example.model.question;

import lombok.Data;

@Data
public abstract class Question {
    protected QuestionBasicInfo questionBasicInfo;

    public Integer getScore(){
        return questionBasicInfo.getPoints();
    }
    public String getId(){
        return questionBasicInfo.getId();
    }
}