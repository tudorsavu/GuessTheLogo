package com.example.guessthelogo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighScoresActivity extends AppCompatActivity {

    ListView listView;
    Button backBtn;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        listView = findViewById(R.id.listview);
        final Typeface font = Typeface.createFromAsset(getAssets(),  "FredokaOne.ttf");
        viewSetUp(font);
        DbInit();
        listSetUp(font);

    }

    private void viewSetUp(Typeface font){
        TextView title = findViewById(R.id.highScoresTitle);
        title.setTypeface(font);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setTypeface(font);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void DbInit() {
        dbHelper = new DbHelper(this);
    }

    private Integer[] getScores(){
       return dbHelper.getScores();
    }

    private void listSetUp(final Typeface font){
        ArrayList<String> scoreList = new ArrayList<>();
        Integer[] scores = getScores();
        if(scores == null){
            scoreList.add("No games played yet!");
        } else {
            for(int i=0;i<scores.length ;i++){
                scoreList.add((i+1) + ".                                 " + scores[i]);

            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.my_list_itm, scoreList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(R.id.text1);
                tv.setTypeface(font);

                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
    }
}
