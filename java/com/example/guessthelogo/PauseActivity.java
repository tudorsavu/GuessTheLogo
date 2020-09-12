package com.example.guessthelogo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PauseActivity extends AppCompatActivity {

    Button resumeBtn;
    Button quitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        initView();

        initFunctionality();
    }

    private void initView() {
        Typeface font = Typeface.createFromAsset(getAssets(),  "FredokaOne.ttf");

        resumeBtn = findViewById(R.id.resumeGame);
        resumeBtn.setTypeface(font);

        quitBtn = findViewById(R.id.quitGame);
        quitBtn.setTypeface(font);

        TextView title = findViewById(R.id.paused);
        title.setTypeface(font);
    }

    private void initFunctionality() {
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(500,null);
                finish();
            }
        });
    }


}
