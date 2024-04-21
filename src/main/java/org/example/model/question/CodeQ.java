package org.example.model.question;

import lombok.Data;

import java.util.Map;


@Data
public class CodeQ extends Question{
    private long timeLimit;
    private Map<String, String> samples;
}