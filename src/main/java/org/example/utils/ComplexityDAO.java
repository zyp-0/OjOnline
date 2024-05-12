package org.example.utils;

import cn.hutool.core.io.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ComplexityDAO {
    @AllArgsConstructor
    private class Score {
        String examId;
        String studentId;
        String qId;
        int complexity;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Score) {
                Score score = (Score) obj;
                return examId.equals(score.examId) && studentId.equals(score.studentId) && qId.equals(score.qId);
            }
            return false;
        }
    }

    @Setter
    private String path;
    List<Score> scoreList = new ArrayList<>();

    private static final ComplexityDAO INSTANCE;

    static {
        INSTANCE = new ComplexityDAO();
    }

    public static ComplexityDAO getInstance() {
        return INSTANCE;
    }


    public void saveComplexity(String examId, String studentId, String qId,/*复杂度*/ int complexity) {
        Score score = new Score(examId, studentId, qId, complexity);
        if (!scoreList.contains(score)) {
            scoreList.add(score);
        }
//        for (int i = 0; i < examIdList.size(); i++) {
//            if (examIdList.get(i).equals(examId) && studentIdList.get(i).equals(studentId) && qIdList.get(i).equals(qId)) {
////                complexityList.set(i, String.valueOf(complexity));
//                return;
//            }
//        }
//        examIdList.add(examId);
//        studentIdList.add(studentId);
//        qIdList.add(String.valueOf(qId));
//        complexityList.add(String.valueOf(complexity));
    }

    public void flush() {
        StringBuilder sb = new StringBuilder();
        sb.append("examId, stuId, qId, complexity").append("\n");
        scoreList.sort((o1, o2) -> {
            if (o1.examId.equals(o2.examId)) {
                if (o1.studentId.equals(o2.studentId)) {
                    return o1.qId.compareTo(o2.qId);
                }
                return o1.studentId.compareTo(o2.studentId);
            }
            return o1.examId.compareTo(o2.examId);
        });
        for (Score score : scoreList) {
            sb.append(score.examId).append(",").append(score.studentId).append(",").append(score.qId).append(",").append(score.complexity).append("\n");
        }
        System.err.println(sb);
        FileUtil.writeUtf8String(sb.toString(), path);
    }
}
