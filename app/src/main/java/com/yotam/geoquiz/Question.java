package com.yotam.geoquiz;

public class Question {

    private int m_questionId;
    private boolean m_answer;

    public Question(int questionId, boolean answer)
    {
        m_questionId = questionId;
        m_answer = answer;
    }

    public int getQuestionId()
    {
        return m_questionId;
    }

    public void setQuestionId(int questionId)
    {
        m_questionId = questionId;
    }

    public boolean getAnswer()
    {
        return m_answer;
    }

    public void setAnswer(boolean answer)
    {
        m_answer = answer;
    }
}

