package org.example.utils.codeProcessor;

import java.io.IOException;

public interface CodeProcessor {
    /**
     * 预处理代码
     * @param codeFilePath 代码文件路径
     */
    void preprocess(String codeFilePath) throws Exception;

    String execute(String className, String[] args, long timeLimit) throws IOException, InterruptedException;
}
