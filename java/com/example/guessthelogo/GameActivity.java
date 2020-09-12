package com.example.guessthelogo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GameActivity extends AppCompatActivity {

    Integer[] imageList;
    int score = 0;
    int tries = 3;
    int logoIdx = 0;
    String answer;
    boolean loaded = false;
    DbHelper dbHelper;

    ImageView logoImage;
    EditText answerInput;
    TextView label;
    TextView scoreLabel;
    Button submitBtn;
    Button skipBtn;

    int REQUEST_EXIT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        initView();

        initFunctionality();

        loadLogosAndShuffle();

        startGame();
    }

    private void initFunctionality() {

        dbHelper = new DbHelper(this);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerInput.getText().toString().equals(answer)){
                    // Toast.makeText(getApplicationContext(),"good", Toast.LENGTH_LONG).show();
                    updateScore(2);
                    loadNextLogo();
                } else if(tries == 1){
                    Toast.makeText(getApplicationContext(),"Hint: answer starts with " + answer.charAt(0) , Toast.LENGTH_LONG).show();
                    updateScore(-1);

                }
                else {
                    tries -= 1;
                    Toast.makeText(getApplicationContext(),"Tries left: " + tries, Toast.LENGTH_SHORT).show();
                }
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextLogo();
            }
        });


    }

    private void startGame() {
        loadNextLogo();
    }

    private void loadNextLogo(){
        tries = 3;
        if(logoIdx == imageList.length){
            Toast.makeText(getApplicationContext(),"Game finished with score of " + score, Toast.LENGTH_LONG).show();
            loadScoreToDb(score);
            finish();
            return;
        }
        logoImage.setImageResource(imageList[logoIdx]);
        answer = getResources().getResourceEntryName(imageList[logoIdx]);
        answerInput.setText("");
        answerInput.requestFocus();
        logoIdx++;
    }

    private void loadScoreToDb(int score) {
        loaded = true;
        if(score > 0){
            dbHelper.insertScore(score);
            Toast.makeText(getApplicationContext(),"Game finished with score of " + score, Toast.LENGTH_LONG).show();
        }
    }

    private void initView() {
        logoImage = findViewById(R.id.imgLogo);

        Typeface font = Typeface.createFromAsset(getAssets(),  "FredokaOne.ttf");

        answerInput = findViewById(R.id.answerInput);
        answerInput.requestFocus();
        answerInput.setTypeface(font);

        label = findViewById(R.id.answerLabel);
        label.setTypeface(font);

        scoreLabel = findViewById(R.id.scoreLabel);
        scoreLabel.setTypeface(font);

        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(font);

        skipBtn = findViewById(R.id.skipBtn);
        skipBtn.setTypeface(font);

    }

    private void loadLogosAndShuffle(){
        imageList = new Integer[]{
                R.drawable.blogger,
                R.drawable.deviantart,
                //R.drawable.digg,
                R.drawable.dropbox,
                //R.drawable.evernote,
                R.drawable.facebook,
                R.drawable.flickr,
                R.drawable.google,
                R.drawable.googleplus,
                //R.drawable.hyves,
                R.drawable.instagram,
                R.drawable.linkedin,
                R.drawable.myspace,
                R.drawable.picasa,
                R.drawable.pinterest,
                R.drawable.reddit,
                R.drawable.rss,
                R.drawable.skype,
                R.drawable.soundcloud,
                //R.drawable.stumbleupon,
                R.drawable.twitter,
                R.drawable.vimeo,
                R.drawable.wordpress,
                R.drawable.yahoo,
                R.drawable.youtube
        };
        // list shuffling
        List<Integer> intList = Arrays.asList(imageList);
        Collections.shuffle(intList);
        intList.toArray(imageList);

    }

    private void updateScore(int points){
        score += points;
        String labelTxt = "Score: " + score;
        scoreLabel.setText(labelTxt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!loaded){
            loadScoreToDb(score);
        }

    }

    @Override
    public void onBackPressed() {

        startActivityForResult(new Intent(this,PauseActivity.class), REQUEST_EXIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == REQUEST_EXIT){
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
