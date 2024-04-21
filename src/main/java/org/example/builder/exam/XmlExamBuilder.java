package org.example.builder.exam;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.example.builder.question.QuestionFactory;
import org.example.builder.question.QuestionFactoryProducer;
import org.example.model.exam.Exam;
import org.example.model.question.QuestionParam;
import org.example.model.question.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlExamBuilder extends ExamBuilder {
    Document document = null;

    @Override
    public void checkPath() throws FileNotFoundException {
        super.checkPath();
        document = XmlUtil.readXML(FileUtil.file(path));
    }

    @Override
    public void buildBasicInfo() {
        exam.setId(XmlUtil.elementText(document.getDocumentElement(), "id"));
        exam.setTitle(XmlUtil.elementText(document.getDocumentElement(), "title"));
        exam.setStartTime(Long.parseLong(XmlUtil.elementText(document.getDocumentElement(), "startTime")));
        exam.setEndTime(Long.parseLong(XmlUtil.elementText(document.getDocumentElement(), "endTime")));
    }

    @Override
    public void buildQuestion() {
        // 解析题目
        Element questions = XmlUtil.getElement(document.getDocumentElement(), "questions");
        NodeList questionList = questions.getElementsByTagName("question");
        int length = questionList.getLength();

        for (int i = 0; i < length / 2; i++) {
            QuestionParam questionParam = new QuestionParam();
            // 每个问题的基本信息
            org.w3c.dom.Element question = (org.w3c.dom.Element) questionList.item(i * 2);
            String id = XmlUtil.elementText(question, "id");
            int type = Integer.parseInt(XmlUtil.elementText(question, "type"));
            String questionContent = XmlUtil.elementText(question, "question");
            int points = Integer.parseInt(XmlUtil.elementText(question, "points"));
            QuestionBasicInfo questionBasicInfo = new QuestionBasicInfo(id, type, questionContent, points);
            questionParam.setQuestionBasicInfo(questionBasicInfo);
            Question q = null;
            // 问题的其他信息
            if (type == 1) {
                String answer = XmlUtil.elementText(question, "answer");
                questionParam.addExtra("answer", answer);
                org.w3c.dom.Element options = XmlUtil.getElement(question, "options");
                NodeList optionList = options.getElementsByTagName("option");
                String[] optionArray = new String[optionList.getLength()];
                for (int j = 0; j < optionList.getLength(); j++) {
                    optionArray[j] = optionList.item(j).getTextContent();
                }
                questionParam.addExtra("options", optionArray);
                QuestionFactory questionFactory = QuestionFactoryProducer.getQuestionFactory(type, questionParam);
                q = questionFactory.createQuestion();
            } else if (type == 2) {
                // 获取答案
                org.w3c.dom.Element answers = XmlUtil.getElement(question, "answers");
                NodeList answerList = answers.getElementsByTagName("answer");
                String[] answerArray = new String[answerList.getLength()];
                for (int j = 0; j < answerList.getLength(); j++) {
                    answerArray[j] = answerList.item(j).getTextContent();
                }
                questionParam.addExtra("answers", answerArray);
                // 获取选项
                org.w3c.dom.Element options = XmlUtil.getElement(question, "options");
                NodeList optionList = options.getElementsByTagName("option");
                String[] optionArray = new String[optionList.getLength()];
                for (int j = 0; j < optionList.getLength(); j++) {
                    optionArray[j] = optionList.item(j).getTextContent();
                }
                questionParam.addExtra("options", optionArray);
                String scoreMode = XmlUtil.elementText(question, "scoreMode");
                questionParam.addExtra("scoreMode", scoreMode);
                String fixScore = XmlUtil.elementText(question, "fixScore");
                if (fixScore != null) {
                    questionParam.addExtra("fixScore", Integer.parseInt(fixScore));
                } else {
                    questionParam.addExtra("fixScore", null);
                }
                Element partialScores = XmlUtil.getElement(question, "partialScores");
                if (partialScores != null) {
                    NodeList partialScoreList = partialScores.getElementsByTagName("partialScore");
                    List<Integer> partialScoreArray = new ArrayList<>();
                    for (int j = 0; j < partialScoreList.getLength(); j++) {
                        partialScoreArray.add(Integer.parseInt(partialScoreList.item(j).getTextContent()));
                    }
                    questionParam.addExtra("partialScore", partialScoreArray);
                } else {
                    questionParam.addExtra("partialScore", null);
                }
                QuestionFactory questionFactory = QuestionFactoryProducer.getQuestionFactory(type, questionParam);
                q = questionFactory.createQuestion();
            } else if (type == 3) {

                Long timeLimit = Long.parseLong(XmlUtil.elementText(question, "timeLimit"));
                questionParam.addExtra("timeLimit", timeLimit);

                // 提取samples
                Element samples = XmlUtil.getElement(question,"samples");
                if(samples != null){
                    NodeList sampleList = samples.getElementsByTagName("sample");
                    // 将samples中每个sample的input和output提取出来，放入map中
                    Map<String, String> sampleMap = new HashMap<>();
                    for (int j = 0; j < sampleList.getLength(); j++) {
                        Element sample = (Element) sampleList.item(j);
                        String input = XmlUtil.elementText(sample, "input");
                        String output = XmlUtil.elementText(sample, "output");
                        sampleMap.put(input, output);
                    }
                    questionParam.addExtra("samples", sampleMap);
                }
                QuestionFactory questionFactory = QuestionFactoryProducer.getQuestionFactory(type, questionParam);
                q = questionFactory.createQuestion();
            }
            exam.addQuestion(q);
        }
    }

    @Override
    public Exam getExam() {
        return exam;
    }
}
