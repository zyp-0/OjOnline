package org.example.builder.exam;

import lombok.Data;
import org.example.model.exam.Exam;

import java.io.File;
import java.io.FileNotFoundException;


@Data
public abstract class ExamBuilder {
    // 存放考试文件的路径 方便后续读取
    protected String path;

    protected Exam exam = new Exam();


    public abstract void buildBasicInfo();

    public abstract void buildQuestion();

    /**
     * 检查文件路径是否合法
     */
    public void checkPath() throws FileNotFoundException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("path should not be empty");
        }
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("file not found");
        }
    }
}
