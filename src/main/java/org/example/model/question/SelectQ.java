package org.example.model.question;

import lombok.Data;

/**
 * 选择题 继承题目类 包含选项
 */
@Data
public class SelectQ extends Question{
    protected String[] options;
}