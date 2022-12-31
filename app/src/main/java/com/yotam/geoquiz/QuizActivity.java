package com.yotam.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizActivity extends AppCompatActivity
{
    private static final String TAG = QuizActivity.class.getSimpleName();
    private static final String KEY_INDEX="index";
    private static final String KEY_CHEAT="cheat";
    private static final int REQUEST_CODE_FOR_CHEAT = 1;

    private Button m_trueButton;
    private Button m_falseButton;
    private Button m_nextButton;
    private Button m_prevButton;
    private Button m_cheatButton;
    private TextView m_questionText;

    private Question[] m_questions;
    private int m_currQuestionIndex;
    private Set<Integer> m_cheatedQuestion = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate Called");
        setState(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        m_questions = new Question[3];
        m_questions[0] = new Question(R.string.question1, true);
        m_questions[1] = new Question(R.string.question2, false);
        m_questions[2] = new Question(R.string.question3, true);

        m_questionText = (TextView)findViewById(R.id.question_text_view);
        changeCurrentQuestion(m_currQuestionIndex);
        initButtons();
    }

    private void setState(Bundle savedInstanceState)
    {
        if(null == savedInstanceState)
        {
            return;
        }
        m_currQuestionIndex = savedInstanceState.getInt(KEY_INDEX);
        m_cheatedQuestion = new HashSet<Integer>(savedInstanceState.getIntegerArrayList(KEY_CHEAT));
    }

    private void initButtons()
    {
        m_trueButton = (Button) findViewById(R.id.true_button);
        m_trueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onAnswerButtonClicked(true);
            }
        });

        m_falseButton = (Button) findViewById(R.id.false_button);
        m_falseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onAnswerButtonClicked(false);
            }
        });

        m_nextButton = findViewById(R.id.next_button);
        m_nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                changeCurrentQuestion(++m_currQuestionIndex % m_questions.length);
            }
            });

        m_prevButton = findViewById(R.id.prev_button);
        m_prevButton.setOnClickListener(view ->
                {
                    m_currQuestionIndex = m_currQuestionIndex - 1 < 0 ? m_questions.length: m_currQuestionIndex;
                    --m_currQuestionIndex;
                    changeCurrentQuestion(m_currQuestionIndex);

                }
        );

        m_cheatButton = (Button)findViewById(R.id.cheat_button);
        m_cheatButton.setOnClickListener(view ->
        {
            Intent cheatActivityIntent = CheatActivity.NewIntent(this,m_questions[m_currQuestionIndex].getAnswer());
            startActivityForResult(cheatActivityIntent, REQUEST_CODE_FOR_CHEAT);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, m_currQuestionIndex);
        outState.putIntegerArrayList(KEY_CHEAT, (ArrayList<Integer>) m_cheatedQuestion.stream().collect(Collectors.toList()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
        {
            return;
        }

        if(requestCode == REQUEST_CODE_FOR_CHEAT && data != null)
        {
           boolean isCheated = CheatActivity.IsAnswerShown(data);
           if(isCheated)
           {
               m_cheatedQuestion.add(m_currQuestionIndex);
           }
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void changeCurrentQuestion(int questionIndex)
    {
        m_currQuestionIndex = questionIndex;
        m_questionText.setText(m_questions[questionIndex].getQuestionId());
    }

    private void onAnswerButtonClicked(boolean answer)
    {

        int resStringId;
        if(m_cheatedQuestion.contains(m_currQuestionIndex))
        {
            resStringId = R.string.judgement_toast;
        }
        else if(answer == m_questions[m_currQuestionIndex].getAnswer())
        {
            resStringId = R.string.correct_toast;
        }
        else
        {
            resStringId = R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this, resStringId, Toast.LENGTH_SHORT).show();
    }
}