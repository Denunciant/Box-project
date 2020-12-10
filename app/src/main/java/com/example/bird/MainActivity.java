package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private Handler handler = new Handler();
    private final static long TIME = 35;

    MediaPlayer backgroundSong;
    private SoundPool soundpool;
    private int hitSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game(this);
        setContentView(game);
        backgroundSong = MediaPlayer.create(this, R.raw.bgsound);
        backgroundSong.start();

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       game.invalidate();
                    }
                });
            }
        },0 , TIME);


    }
}