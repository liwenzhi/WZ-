package com.liwenzhi.aib.bean;

import java.io.Serializable;

/**
 * 数据的基类
 */
public class DataBean implements Serializable {
    private String flag;//标识，0面试常识，1java知识，2android知识
    private String title;//标题，选择题，填空题，描述，编程题
    private String problem;//问题题目
    private String answer;  //答案
    private boolean showAnswer;  //是否显示答案

    public DataBean() {

    }

    public DataBean(String flag, String title, String problem, String answer) {
        this.flag = flag;
        this.title = title;
        this.problem = problem;
        this.answer = answer;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "flag=" + flag +
                ", title='" + title + '\'' +
                ", problem='" + problem + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
