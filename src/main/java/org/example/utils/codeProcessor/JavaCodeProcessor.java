package org.example.utils.codeProcessor;

import org.example.utils.PathUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaCodeProcessor implements CodeProcessor {

    @Override
    public void preprocess(String codeFilePath) throws IOException, InterruptedException {
        // 编写代码预处理逻辑，将 Java 代码编译成可执行文件
        // 调用命令行命令来编译代码

        // 获取文件的可编译路径
        String Path = PathUtil.getCompilerPath(codeFilePath);

        Process process = Runtime.getRuntime().exec("javac " + Path);

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Compilation failed");
        }
    }

    @Override
    public String execute(String className, String[] args) throws IOException, InterruptedException {
        // 编写代码执行逻辑，执行编译好的 Java 类并传入参数
        // 调用命令行命令来执行代码
        String Path =  PathUtil.getClassPath();
        Process process = Runtime.getRuntime().exec("java -cp " + Path + " " + className + " " + String.join(" ", args));


        // 读取代码执行的输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        // 等待执行完成
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Execution failed");
        }

        return output.toString();
    }
}