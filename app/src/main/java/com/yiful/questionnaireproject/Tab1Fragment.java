package com.yiful.questionnaireproject;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {
    Button btnStart, btnA, btnB, btnC;
    TextView tvQuestion;
    Spinner spinner;

    SQLiteDatabase database;
    QuizDbHelper dbHelper;
    Cursor cursor;
    String userAnswer, trueAnswer;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView()
        try {
            dbHelper = new QuizDbHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        database = dbHelper.myDataBase;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_tab1, container,false);
        btnStart = v.findViewById(R.id.btnStart);
        tvQuestion = v.findViewById(R.id.tvQuestion);
        spinner = v.findViewById(R.id.spn);
        btnA = v.findViewById(R.id.btnA);
        btnB = v.findViewById(R.id.btnB);
        btnC = v.findViewById(R.id.btnC);

       // String trueAnswer;


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.genre, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                v.findViewById(R.id.btnGroup).setVisibility(LinearLayout.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.findViewById(R.id.btnGroup).setVisibility(LinearLayout.VISIBLE);
                String genre = spinner.getSelectedItem().toString();
                cursor = database.rawQuery("select * from " + genre,null);
                cursor.moveToFirst();
                readQuestion();
            //    cursor.close();
            }
        });
        return v;
    }
//check the answer is correct or not.
    public void checkAnswer(){
        if(userAnswer.equals(trueAnswer)){
            Toast.makeText(getActivity(), "Correct!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Wrong! The correct answer is "+trueAnswer, Toast.LENGTH_SHORT).show();
        }
    }
//load the question and go to next
    public void readQuestion(){
        if(!cursor.moveToNext()){
            cursor.close();
            Toast.makeText(getActivity(), "This is last question!", Toast.LENGTH_SHORT).show();
        }else{
            String question = cursor.getString(0);
            String optionA = cursor.getString(1);
            String optionB = cursor.getString(2);
            String optionC = cursor.getString(3);
            trueAnswer = cursor.getString(4);
            //     String userAnswer;
            tvQuestion.setText(question);
            btnA.setText(optionA);
            btnB.setText(optionB);
            btnC.setText(optionC);
            btnA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userAnswer = btnA.getText().toString();
                    checkAnswer();
                    readQuestion();
                }
            });
            btnB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userAnswer = btnB.getText().toString();
                    checkAnswer();
                    readQuestion();
                }
            });
            btnC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userAnswer = btnC.getText().toString();
                    checkAnswer();
                    readQuestion();
                }
            });
        }
    }


}
