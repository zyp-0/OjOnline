package org.example.builder.exam;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONArray;
import org.example.builder.question.QuestionFactory;
import org.example.builder.question.QuestionFactoryProducer;
import org.example.model.question.Question;

import org.example.model.question.QuestionBasicInfo;
import org.example.model.question.QuestionParam;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


public class JsonExamBuilder extends ExamBuilder{
    JSONObject jsonObject = null;


    @Override
    public void checkPath() throws FileNotFoundException {
        super.checkPath();
        jsonObject = JSONUtil.parseObj(FileUtil.readUtf8String(path));
    }

    @Override
    public void buildBasicInfo() {
        //读取json文件，构建Exam对象，返回

        // 解析字段
        String id = jsonObject.getStr("id");
        String title = jsonObject.getStr("title");
        long startTime = jsonObject.getLong("startTime");
        long endTime = jsonObject.getLong("endTime");

        exam.setId(id);
        exam.setTitle(title);
        exam.setStartTime(startTime);
        exam.setEndTime(endTime);

    }

    /**
     * 从json文件读取题目信息，构建Question对象。
     */
    @Override
    public void buildQuestion() {
        JSONArray questions = jsonObject.getJSONArray("questions");
        // 遍历全部题目
        for (int i = 0; i < questions.size(); i++) {
            // 创建题目对象
            Question q = null;

            // 获取题目信息
            JSONObject questionJson = questions.getJSONObject(i);
            // 提取题目基本信息
            String id = questionJson.getStr("id");
            int type = questionJson.getInt("type");
            String questionContent = questionJson.getStr("question");
            int points = questionJson.getInt("points");


            // 构建题目基本信息
            QuestionBasicInfo questionBasicInfo = new QuestionBasicInfo(id, type, questionContent, points);

            // 创建题目传递模型
            QuestionParam questionParam = new QuestionParam();
            questionParam.setQuestionBasicInfo(questionBasicInfo);

            // 解析不同类型的题目
            if (type == 1) { // 单选题
                //提取answer
                String answer = questionJson.getStr("answer");
                questionParam.addExtra("answer", answer);
                // 提取options
                String[] options = questionJson.getJSONArray("options").toArray(new String[0]);
                questionParam.addExtra("options", options);
                // 创建单选题工厂 利用工厂模式创建单选题实体
                QuestionFactory questionFactory = QuestionFactoryProducer.getQuestionFactory(type, questionParam);
                q = questionFactory.createQuestion();

            } else if (type == 2) { // 多选题
                //提取answer
                String[] answers = questionJson.getJSONArray("answer").toArray(new String[0]);
                questionParam.addExtra("answers", answers);
                // 提取options
                String[] options = questionJson.getJSONArray("options").toArray(new String[0]);
                questionParam.addExtra("options", options);
                //提取scoreMode和fixScore和partialScore,没有就默认为null
                String scoreMode = questionJson.getStr("scoreMode");
                questionParam.addExtra("scoreMode", scoreMode);

                Integer fixScore = questionJson.get("fixScore", Integer.class);
                questionParam.addExtra("fixScore", fixScore);

                if (questionJson.getJSONArray("partialScore") != null) {
                    Integer[] partialScore = questionJson.getJSONArray("partialScore").toArray(new Integer[0]);
                    questionParam.addExtra("partialScore", partialScore);
                } else {
                    questionParam.addExtra("partialScore", null);
                }

                // 创建多选题工厂
                QuestionFactory questionFactory = QuestionFactoryProducer.getQuestionFactory(type, questionParam);
                q = questionFactory.createQuestion();
            }else if (type == 3) { // 编程题
                Long timeLimit = questionJson.getLong("timeLimit");
                questionParam.addExtra("timeLimit", timeLimit);

                // 提取samples
                JSONArray samples = questionJson.getJSONArray("samples");
                // 将samples中每个sample的input和output提取出来，放入map中
                Map<String, String> sampleMap = new HashMap<>();
                for (int j = 0; j < samples.size(); j++) {
                    JSONObject sample = samples.getJSONObject(j);
                    String input = sample.getStr("input");
                    String output = sample.getStr("output");
                    sampleMap.put(input,output);
                }
                questionParam.addExtra("samples", sampleMap);


                // 创建编程题工厂
                QuestionFactory questionFactory = QuestionFactoryProducer.getQuestionFactory(type, questionParam);
                q = questionFactory.createQuestion();
            }
            exam.addQuestion(q);
        }
    }

}
