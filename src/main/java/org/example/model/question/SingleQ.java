package org.example.model.question;

import lombok.Data;
/**
 * 单选题
 * 继承选择题
 * 包含唯一答案
 */
@Data
public class SingleQ extends SelectQ{
    private String answer;
}
