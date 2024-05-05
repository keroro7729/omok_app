package com.example.omokproject2.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.omokproject2.R;
import com.example.omokproject2.components.BoardView;
import com.example.omokproject2.components.MyListener;
import com.example.omokproject2.components.PracticeOmokManager;

public class PracticeActivity extends AppCompatActivity {

    private BoardView boardView;
    private PracticeOmokManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        boardView = findViewById(R.id.practice_boardView);
        manager = new PracticeOmokManager(boardView);

        Button PREVButton = findViewById(R.id.practice_PREV_button);
        Button prevButton = findViewById(R.id.practice_prev_button);
        Button nextButton = findViewById(R.id.practice_next_button);
        Button NEXTButton = findViewById(R.id.practice_NEXT_button);
        Button exitButton = findViewById(R.id.practice_exit_button);
        Button showNumberingButton = findViewById(R.id.practice_show_numbering);
        TextView numberTextView = findViewById(R.id.practice_number_textView);

        boardView.setListener(new MyListener() {
            @Override
            public void onTouchEvent(int x, int y) {
                manager.put(x, y);
                numberTextView.setText(String.valueOf(manager.getNumber()));
            }
        });

        PREVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.moveGameLog(-10);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.moveGameLog(-1);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.moveGameLog(1);
            }
        });

        NEXTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.moveGameLog(10);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        showNumberingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardView.turnNumbering();
                boardView.invalidate();
            }
        });
    }
}
