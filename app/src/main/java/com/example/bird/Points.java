package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Points extends AppCompatActivity {

    MediaPlayer lost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        TextView ScoreLabel = findViewById(R.id.ScoreLabel);
        TextView HighScore= findViewById(R.id.HighScoreLabel);
        TextView gameOver= findViewById(R.id.gameOver);
        lost = MediaPlayer.create(this, R.raw.go);
        lost.start();
        MediaPlayer backgroundSong;
        backgroundSong = MediaPlayer.create(this, R.raw.bgsound);
       // backgroundSong.start();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        gameOver.startAnimation(animation);

        int points = getIntent().getIntExtra("Points", 0);
        ScoreLabel.setText(points + " ");
        SharedPreferences settings = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        int highscore = settings.getInt("HIGH_SCORE", 0);

        if(points > highscore){
            HighScore.setText("Best game points:" + points);
            SharedPreferences.Editor e = settings.edit();
            e.putInt("HIGH_SCORE", points);
            e.commit();
        }else{
            HighScore.setText("High Score :"+ highscore);
        }
    }
    public void play(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void toMenu(View view){
        startActivity(new Intent(getApplicationContext(), StartingActivity.class));
    }
}