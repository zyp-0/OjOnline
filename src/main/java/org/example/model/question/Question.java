package org.example.model.question;

import lombok.Data;

@Data
public abstract class Question {
    protected QuestionBasicInfo questionBasicInfo;

//    private int timeLimit;
//    private Map<String, String> samples;
//    private String input;
//    private String output;

    public Integer getScore(){
        return questionBasicInfo.getPoints();
    }
    public String getId(){
        return questionBasicInfo.getId();
    }
}