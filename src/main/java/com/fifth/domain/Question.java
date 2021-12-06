package com.fifth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Question implements Serializable {
    private Integer id;//

    private Integer no;//题号

    private String title;//题干
    @JsonProperty("A")
    private String A;//A选项
    @JsonProperty("B")
    private String B;//B选项
    @JsonProperty("C")
    private String C;//C选项
    @JsonProperty("D")
    private String D;//D选项

    private Integer type;//题目类型

    private String trueAnswer;//正确答案

    private float score;//分数

    private Integer homeworkId;//作业id

    public Question(Integer id, Integer no, String title, String trueAnswer, float score, Integer type, Integer homeworkId) {
        this.id = id;
        this.no = no;
        this.title = title;
        this.type = type;
        this.trueAnswer = trueAnswer;
        this.score = score;
        this.homeworkId = homeworkId;
    }

    public Question(Integer id, Integer no, String title, String a, String b, String c, String d, String trueAnswer, float score, Integer type, Integer homeworkId) {
        this.id = id;
        this.no = no;
        this.title = title;
        A = a;
        B = b;
        C = c;
        D = d;
        this.type = type;
        this.trueAnswer = trueAnswer;
        this.score = score;
        this.homeworkId = homeworkId;
    }

    public Question() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }
}
