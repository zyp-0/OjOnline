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
    public String execute(String className, String[] args, long timeLimit) throws IOException, InterruptedException {
        // 编写代码执行逻辑，执行编译好的 Java 类并传入参数
        // 调用命令行命令来执行代码
        String Path = PathUtil.getClassPath();
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", Path, className);
        Process process = processBuilder.start();

        // 记录执行开始时间
        long startTime = System.currentTimeMillis();

        // 创建新线程来等待执行完成
        Thread timeoutThread = new Thread(() -> {
            try {
                Thread.sleep(timeLimit); // 等待超时时间
                process.destroyForcibly(); // 强制终止进程
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 重新设置中断标志
            }
        });
        timeoutThread.start();

        // 读取代码执行的输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = errorReader.readLine()) != null) {
            // 处理编译错误信息
            System.out.println(line);
        }

        // 等待执行完成
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Execution failed");
        }

        // 计算执行时间
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // 判断是否超时（考虑容差范围）
        long timeDifference = executionTime - timeLimit;
        if (timeDifference > 100) { // 设置容差范围为100毫秒
            throw new RuntimeException("Execution time exceeded");
        }

        timeoutThread.interrupt(); // 执行完成则终止超时线程

        return output.toString();
    }
}