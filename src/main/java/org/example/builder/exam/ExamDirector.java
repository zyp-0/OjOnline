package org.example.builder.exam;

import org.example.model.exam.Exam;


import java.io.File;
import java.io.IOException;

public class ExamDirector {
    private ExamBuilder examBuilder;

    public ExamDirector(ExamBuilder examBuilder) {
        this.examBuilder = examBuilder;
    }

    public Exam constructExam(File examFile) throws IOException{
        // 设置文件路径
        examBuilder.setPath(examFile.getPath());
        // 检查文件路径是否合法
        examBuilder.checkPath();
        // 构建基本信息
        examBuilder.buildBasicInfo();
        examBuilder.buildQuestion();

        return examBuilder.getExam();
    }
}
