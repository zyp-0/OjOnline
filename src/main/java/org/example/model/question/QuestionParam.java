package org.example.model.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.question.QuestionBasicInfo;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QuestionParam {
    private QuestionBasicInfo questionBasicInfo;
    private Map<String, Object> extra = new HashMap<>();

    public void addExtra(String key, Object value) {
        extra.put(key, value);
    }
}
