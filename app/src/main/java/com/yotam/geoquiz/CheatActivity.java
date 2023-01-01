package com.yotam.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity
{
    private static final String EXTRA_ANSWER_IS_TRUE = "com.yotam.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.yotam.geoquiz.is_answer_shown";
    private static final String KEY_IS_ANSWER_SHOWN = "answer_shown";

    private boolean m_answer;
    private boolean m_isAnswerShown;

    private TextView m_answerTextView;
    private Button m_showAnswerButton;

    public static Intent NewIntent(Context packageContext, boolean answer)
    {
        return new Intent(packageContext, CheatActivity.class).putExtra(EXTRA_ANSWER_IS_TRUE, answer);
    }

    public static boolean IsAnswerShown(Intent result)
    {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        setState(savedInstanceState);

        m_answer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        m_answerTextView = findViewById(R.id.answerTextView);
        if(m_isAnswerShown)
        {
            setAnswerShown(true);
        }

        m_showAnswerButton = findViewById(R.id.showAnswerButton);
        m_showAnswerButton.setOnClickListener(view ->
        {
            setAnswerShown(true);
        });

        TextView apiVersionTextView = findViewById(R.id.apiVersionTextView);
        apiVersionTextView.setText("API Version " + String.valueOf(Build.VERSION.SDK_INT));
    }

    private void setState(Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
        {
            return;
        }
        m_isAnswerShown = savedInstanceState.getBoolean(KEY_IS_ANSWER_SHOWN, false);
    }

    private void setAnswerShown(boolean isAnswerShown)
    {
        Intent resultData = new Intent().putExtra(EXTRA_ANSWER_SHOWN, true);
        setResult(RESULT_OK, resultData);
        m_isAnswerShown = true;
        m_answerTextView.setText(String.valueOf(m_answer));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_ANSWER_SHOWN, m_isAnswerShown);
    }
}