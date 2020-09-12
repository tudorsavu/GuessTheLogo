package com.example.guessthelogo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.annotation.Target;
import java.security.KeyPair;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "scores.db";
    private static final String TABLE_NAME = "score_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "Score";


    DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORE INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private boolean isFull(){
        return getScores().length > 7;
    }

    boolean insertScore(int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, score);


        if(!isFull()){
            return db.insert(TABLE_NAME, null, contentValues) != -1;
        } else{
            Integer[] scores = getScores();
            for (Integer integer : scores) {
                if (integer < score) {
                    int rowId = getRowToUpdate(score);
                    db.update(TABLE_NAME,contentValues,COL_1 + " = " + rowId, null);
                    return true;
                }
            }
        }

        return false;

    }

    int getHighScore(){
        int max = 0;
        Integer[] scores = getScores();
        if(scores != null){
            max = scores[0];
        }

        return max;
    }

    private int getRowToUpdate(int score){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int id = 0;
        int min = 99999;
        while(result.moveToNext()){
            if(result.getInt(1)< min){
                min = result.getInt(1);
                id = result.getInt(0);
            }
        }
        return id;
    }

    Integer[] getScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_2 + " DESC ", null);
        if(result.getCount() == 0){
            return null;
        }

        Integer[] scores_arr= new Integer[result.getCount()];
        int i = 0;
        while(result.moveToNext()){
            scores_arr[i] = result.getInt(1);
            i++;
        }

        result.close();

        return scores_arr;

    }
}
