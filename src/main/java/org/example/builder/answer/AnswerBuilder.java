package org.example.builder.answer;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import org.example.model.answer.Answer;


import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AnswerBuilder {
    private String path;

    public Answer buildAnswer() {
        JSONObject jsonObject = JSONUtil.parseObj(FileUtil.readUtf8String(path));
        String examId = jsonObject.getStr("examId");
        String studentId = jsonObject.getStr("stuId");
        Long submitTime = jsonObject.getLong("submitTime");
        JSONArray answers = jsonObject.getJSONArray("answers");
        Map<String, String> answerMap = new HashMap<>();
        for (int i = 0; i < answers.size(); i++) {
            JSONObject answer = answers.getJSONObject(i);
            String questionId = answer.getStr("id");
            String answer1 = answer.getStr("answer");
            answerMap.put(questionId, answer1);
        }
        return Answer.builder()
                .examId(examId)
                .stuId(studentId)
                .submitTime(submitTime)
                .answers(answerMap)
                .build();
    }
}
