package org.example.model.question;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 多选题
 * 继承选择题
 * 包含多个答案 评分模式 固定分数 部分分数
 */
@Data
public class MultiQ extends SelectQ{
    private String[] answers;
    private String scoreMode;
    private Integer fixScore;
    private List<Integer> partialScore = new ArrayList<>();
}
