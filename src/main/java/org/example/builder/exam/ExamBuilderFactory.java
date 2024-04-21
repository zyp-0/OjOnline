package org.example.builder.exam;

import java.io.File;

public class ExamBuilderFactory {
    public static ExamBuilder createExamBuilder(String fileType) {

        // 根据文件类型选择合适构建器
        if (fileType.equalsIgnoreCase("json")) {
            return new JsonExamBuilder();
        } else if (fileType.equalsIgnoreCase("xml")) {
            return new XmlExamBuilder();
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}
