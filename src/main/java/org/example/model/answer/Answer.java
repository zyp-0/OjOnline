package org.example.model.answer;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Answer {
    private String examId;
    private String stuId;
    private long submitTime;
    // 存放题目id 和 答案
    private Map<String,String> answers;
}
