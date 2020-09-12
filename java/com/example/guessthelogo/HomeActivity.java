package com.example.guessthelogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity {

    Button newGameBtn;
    Button highScoresBtn;
    Button socialShareBtn;

    CallbackManager callbackManager;
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Init View
        viewSetUp();

        //Init FB share
        FBSetup();

        //Init other Btns
        HighscoreBtn();
        NewGameBtn();

    }


    private void viewSetUp() {

        Typeface font = Typeface.createFromAsset(getAssets(),  "FredokaOne.ttf");

        newGameBtn = findViewById(R.id.newGameBtn);
        newGameBtn.setTypeface(font);

        highScoresBtn = findViewById(R.id.highScoresBtn);
        highScoresBtn.setTypeface(font);

        socialShareBtn = findViewById(R.id.socialShareBtn);
        socialShareBtn.setTypeface(font);

    }

    private void FBSetup(){
        final DbHelper dbHelper = new DbHelper(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        socialShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("I am playing GuessTheLogo whith a highscore of "+ dbHelper.getHighScore() +"!")
                        .setContentUrl(Uri.parse("https://ibb.co/Z1zJVtf"))
                        .build();
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(linkContent);
                }
            }
        });

    }

    private void HighscoreBtn() {
        highScoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, HighScoresActivity.class));
            }
        });
    }

    private void NewGameBtn() {
       newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, GameActivity.class));
            }
        });
    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.guessthelogo",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
