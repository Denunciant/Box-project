package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Game extends View {
  //  private Bitmap box;
    private Bitmap background;

    private Paint score = new Paint();
    private Paint level = new Paint();

    private int points;

    private Bitmap life[] = new Bitmap[2];
    private int lifes;

    private int canvasW;
    private int canvasH;

    private Bitmap box[] = new Bitmap[2];
    private int boxX = 10;
    private int boxY;
    private int boxS;

    private boolean touchF = false;

    private int pointX;
    private int pointY;
    private int pointS = 14;
    private Paint pointPaint = new Paint();

    private int starX;
    private int starY;
    private int starS = 16;
    private Paint starPaint = new Paint();
    final MediaPlayer mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), R.raw.hit1);

   // private SoundPool soundpool;
    //private int hitSound;

    public Game(Context context) {
        super(context);

        box[0] = BitmapFactory.decodeResource(getResources(),R.drawable.box_right );
        box[1] = BitmapFactory.decodeResource(getResources(),R.drawable.box_right02_ );

        score.setColor(Color.BLUE);
        score.setTextSize(40);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        starPaint.setColor(Color.RED);
        level.setColor(Color.BLACK);
        level.setTextSize(40);
        level.setTypeface(Typeface.DEFAULT_BOLD);
        level.setTextAlign(Paint.Align.CENTER);


        pointPaint.setColor(Color.BLACK);

        boxY = 500;
        lifes = 3;
        points = 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasW = canvas.getWidth();
        canvasH = canvas.getHeight();
        canvas.drawBitmap(background,0,0,null);

        int minBoxy = box[0].getHeight();
        int maxBoxy = canvasH - box[0].getHeight()* 3;
        boxY += boxS;
        if (boxY < minBoxy){
           boxY = minBoxy;
        }
        if (boxY > maxBoxy){
            boxY = maxBoxy;
        }

        boxS += 3;
        if (touchF) {
            canvas.drawBitmap(box[1],boxX,boxY, null );
            touchF = false;
            mediaPlayer.start();
        }else{
            canvas.drawBitmap(box[0], boxX, boxY, null);
        }


       // canvas.drawBitmap(box,0,0,null);

        canvas.drawText("Your score:"+ points ,20,67,score);
        canvas.drawText("Level: 1", canvasW / 2, 67 ,level);
       // canvas.drawBitmap(life[0], 830, 30, null);
       // canvas.drawBitmap(life[0], 890, 30, null);
       // canvas.drawBitmap(life[1], 950, 30, null);
        canvas.drawCircle(pointX,pointY, 10, pointPaint);
        canvas.drawCircle(starX, starY, 15, starPaint);

        for(int i=0; i<3; i++){
            int x = (int)(750 + life[0].getWidth()*1.5 *i );
            int y = 30;
            if (i< lifes){
                canvas.drawBitmap(life[0], x , y , null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

        starX -= starS;
        if(hitCheck(starX, starY)){
            starX = starX - 200;
            lifes--;
            if(lifes == 0){
                menuBack();
                }
        } if (starX < 0){
            starX = canvasW + 200;
            starY = (int) Math.floor(Math.random() *(maxBoxy- minBoxy)) + minBoxy;
            //canvas.drawCircle(starX, starY, 15, starPaint);
        }

        pointX -= pointS;
        if(hitCheck(pointX, pointY))
        {
            points = points + 10;
            pointX = -120;
        }
        if(pointX <0){
            pointX = canvasW + 20;
            pointY = (int) Math.floor(Math.random() *(maxBoxy- minBoxy)) + minBoxy;
        }


    }
    public boolean hitCheck(int x, int y){
        if(boxX<x && x<(boxX + box[0].getWidth() ) && boxY < y && y< (boxY + box[0].getHeight())){
        return true;}
        return false;
    }
    public void menuBack(){
        Intent back = new Intent(getContext(), Points.class);
        back.putExtra("Points", points);
        getContext().startActivity(back);
       }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touchF = true;
            boxS = -28;
        }
        return true;
    }
}
