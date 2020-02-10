package com.example.dell.catchtheball;

import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Move extends AppCompatActivity {
    int screenWidth;
    int screenHeight;

    ImageView upI, downI, leftI, rightI;
    Button button;

    float upX;
    float upY;
    float downX;
    float downY;
    float leftX;
    float leftY;
    float rightX;
    float rightY;

    //status check
    boolean pause_flg = false;

    //Initialize class
    Handler handler = new Handler();
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        button = findViewById(R.id.button);
        upI = findViewById(R.id.up);
        downI = findViewById(R.id.down);
        rightI = findViewById(R.id.right);
        leftI = findViewById(R.id.left);

//get screen size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //move to out of screen
        upI.setX(-80.0f);
        downI.setX(-80.0f);

        //start the timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }


                });
            }
        }, 0, 20);

        leftI.setX(-80.0f);
        rightI.setX(screenWidth + 80.0f);
        upI.setY(-80.0f);
        downI.setY(screenHeight + 80.0f);
        leftI.setY(-80.0f);
        rightI.setY(-80.0f);


    }

    private void changePos() {
        //up
        upY -= 10;
        if (upI.getY() + upI.getHeight() < 0) {
            upX = (float) Math.floor(Math.random() * (screenWidth - upI.getWidth()));
            upY = screenHeight + 100.0f;
        }
        upI.setX(upX);
        upI.setY(upY);

        //down
        downY += 10;
        if (downI.getY() > screenHeight) {
            downX = (float) Math.floor(Math.random() * (screenWidth - downI.getWidth()));
            downY = -100.0f;
        }
        downI.setX(downX);
        downI.setY(downY);

        //right
        rightY += 10;
        if (rightI.getX() > screenWidth) {
            rightX = -100.0f;
            rightY = (float) Math.floor(Math.random() * (screenHeight - rightI.getHeight()));
        }
        rightI.setX(rightX);
        rightI.setY(rightY);

        //left
        leftX -= 10;
        if (leftI.getX() + leftI.getWidth() < 0) {
            leftX = screenWidth + 100.0f;
            leftY = (float) Math.floor(Math.random() * (screenHeight - leftI.getHeight()));

        }
        leftI.setX(leftX);
        leftI.setY(leftY);


    }
//pause

    public void Pause(View view) {
        if (pause_flg = false) {
            pause_flg = true;

            //stop timer
            timer.cancel();
            timer = null;
            //change button text
            button.setText("Start");

        }
        else {
            pause_flg = false;
            //change button text
            button.setText("Pause");
            //create and start the timer
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }


                    });
                }
            }, 0, 20);


        }

    }
}
