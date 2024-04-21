package org.example.utils;

import cn.hutool.core.io.FileUtil;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {
    @Setter
    private String path;
    List<String> examIdList = new ArrayList<>();
    List<String> studentIdList = new ArrayList<>();
    List<String> scoreList = new ArrayList<>();
    private static final ScoreDAO INSTANCE;

    static {
        INSTANCE = new ScoreDAO();
    }

    public static ScoreDAO getInstance() {
        return INSTANCE;
    }

    public void saveScore(String examId, String studentId, int score) {
        examIdList.add(examId);
        studentIdList.add(studentId);
        scoreList.add(String.valueOf(score));
    }

    public void flush() {
        StringBuilder sb = new StringBuilder();
        sb.append("examId, stuId, score").append("\n");
        for (int i = 0; i < examIdList.size(); i++) {
            sb.append(examIdList.get(i)).append(",").append(studentIdList.get(i)).append(",").append(scoreList.get(i)).append("\n");
        }
        System.err.println(sb);
        FileUtil.writeUtf8String(sb.toString(), path);
    }
}
