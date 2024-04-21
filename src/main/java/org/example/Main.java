package org.example;

import org.example.builder.answer.AnswerBuilder;
import org.example.builder.exam.ExamBuilder;
import org.example.builder.exam.ExamBuilderFactory;
import org.example.builder.exam.ExamDirector;
import org.example.Executor.AnswerCalExecutor;
import org.example.model.answer.Answer;
import org.example.model.exam.Exam;
import org.example.utils.CustomThreadPool;
import org.example.utils.ScoreDAO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String casePath = args[0];
        // 题目文件夹路径
        String examsPath = casePath + System.getProperty("file.separator") + "exams";
        // 答案文件夹路径
        String answersPath = casePath + System.getProperty("file.separator") + "answers";
        // 输出文件路径
        String output = args[1];
        // TODO:在下面调用你实现的功能

        // 创建 线程为5 的 线程池
        CustomThreadPool threadPool  = new CustomThreadPool(5);

        // 读取题目和答案，生成考试对象
        // 读取题目
        Map<String,Exam> exams = new HashMap<>();
        try {
            // 读取考试文件
            File examsFolder = new File(examsPath);

            // 文件夹是否存在
            if (examsFolder.exists() && examsFolder.isDirectory()) {
                File[] examFiles = examsFolder.listFiles();
                // 遍历文件

                if (examFiles != null) {
                    for (File file : examFiles) {
                        // 获取文件后缀
                        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);;
                        // 根据文件类型创建builder对象
                        ExamBuilder builder =  ExamBuilderFactory.createExamBuilder(suffix);
                        ExamDirector director = new ExamDirector(builder);
                        Exam exam = director.constructExam(file);
                        exams.put(exam.getId(), exam);
                    }
                }
            } else {
                System.err.println("指定的文件夹不存在或不是一个文件夹");
            }

            // 设置 得分文件 输出路径
            ScoreDAO scoreDAO = ScoreDAO.getInstance();
            scoreDAO.setPath(output);

            File answersFolder = new File(answersPath);
            File[] answerFiles = answersFolder.listFiles();

            // 创建
            AnswerCalExecutor answerCalExecutor = new AnswerCalExecutor();
            answerCalExecutor.setExams(exams);
//            threadPool.submit(() -> {
                if (answerFiles != null) {
                    for (File file : answerFiles) {
                        // 如果是文件夹则跳过
                        if(file.isDirectory()){
                            continue;
                        }
                        // 创建任务，提交给线程池执行

                        // 读取作答文件
                        AnswerBuilder answerBuilder = new AnswerBuilder(file.getPath());
                        Answer answer = answerBuilder.buildAnswer();

                        // 根据考试信息和答案信息计算每个考生的 得分
                        Integer res = answerCalExecutor.calculate(answer);

                        scoreDAO.saveScore(answer.getExamId(), answer.getStuId(), res);

                        // 将考生的得分写入output文件
                    }
                    scoreDAO.flush();
                }
//            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}