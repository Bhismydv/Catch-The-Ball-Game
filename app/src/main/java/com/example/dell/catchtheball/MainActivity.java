package com.example.dell.catchtheball;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView textView1,textView2;
    ImageView imageView1,imageView2,imageView3,imageView4;
    FrameLayout frameLayout;
//position
    int boxY;
    int frameHeight;

    int boxSize;
    int screeWidth;
    int screenHeight;

    int colorFullX;
    int colorFullY;
    int goldenX;
    int goldenY;
    int blackX;
    int blackY;

    int boxSpeed;
    int goldenSpeed;
    int colorFullSpeed;
    int blackSpeed;
//Score
    int score=0;


//initialize class
    Handler handler=new Handler();
    Timer timer=new Timer();
    SoundPlay soundPlay;

    //status change
    boolean action_flag=false;
    boolean start_flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlay=new SoundPlay(this);

        textView1=findViewById(R.id.score_label);
        textView2=findViewById(R.id.startLabel);
        imageView1=findViewById(R.id.box);
        imageView2=findViewById(R.id.orange);
        imageView3=findViewById(R.id.pink);
        imageView4=findViewById(R.id.black);

        //get screen size
        WindowManager windowManager=getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);

        screenHeight=size.x;
        screeWidth=size.y;

//Now
        //Nexus4 width:786 height:1184
        //Speed box:20 purple:12 white:20 oxDog:16
        boxSpeed=Math.round(screenHeight/60F);//(1184/60=19.733...=>20)
        goldenSpeed=Math.round(screenHeight/60F);//(768/60=12.8...=>13)
        colorFullSpeed=Math.round(screenHeight/36F);//(768/36=21.333...=>21)
        blackSpeed=Math.round(screenHeight/45F);//(768/45=17.06...=>17)
        Log.v("SPEED_BOX",boxSpeed+"");
        Log.v("SPEED_PURPLE",goldenSpeed+"");
        Log.v("SPEED_WHITE",colorFullSpeed+"");
        Log.v("SPEED_OXDOG",blackSpeed+"");


/*Move to out of screen*/
        imageView4.setX(-80);
        imageView4.setY(-80);

        imageView3.setX(-80);
        imageView3.setY(-80);

        imageView2.setX(-80);
        imageView2.setY(-80);

        textView1.setText("Score : 0");


//Temporary

        boxY=500;

    }
    public void changePos(){
        hitCheck();
        //purple
        goldenX -=goldenSpeed;
        if(goldenX<0)
        {
            goldenX=screeWidth+20;
            goldenY=(int)Math.floor(Math.random()*(frameHeight-imageView2.getHeight()));
        }
imageView2.setX(goldenX);
        imageView2.setY(goldenY);

        //white
        colorFullX-=colorFullSpeed;
        if(colorFullX<0){
            colorFullX=screeWidth+5000;
            colorFullY=(int)Math.floor(Math.random()*(frameHeight-imageView3.getHeight()));
        }
        imageView3.setX(colorFullX);
        imageView3.setY(colorFullY);
//oxDog
     blackX-=blackSpeed;
if(blackX<0){
    blackX=screeWidth+10;
    blackY=(int)Math.floor(Math.random()*(frameHeight-imageView4.getHeight()));
}
imageView4.setX(blackX);
imageView4.setY(blackY);

        imageView1.setY(boxY);
        //move box
        if(action_flag==true){
            //touching
            boxY -=boxSpeed;
        }else {
            //releasing
            boxY +=boxSpeed;
        }

        //check box position
        if(boxY<0)boxY=0;

        if(boxY > frameHeight-boxSize)boxY=frameHeight-boxSize;
        imageView1.setY(boxY);
        textView1.setText("Score :"+score);

    }

    public void hitCheck(){
        //if the center of the ball in box, it counts as a hit.

        //purple

        int goldenCenterX =goldenX+imageView2.getWidth()/2;
        int goldenCenterY =goldenY+imageView2.getHeight()/2;


        //0<=purpleCenterX<=boxWidth
        //boxY<=purpleCenterY=boxY+boxHeight

        if(0<=goldenCenterX && goldenCenterX<=boxSize && boxY<=goldenCenterY && goldenCenterY<=boxY+boxSize){
           score+=10;
            goldenX=-10;
            soundPlay.playHitSound();
        }
      //white
        int whiteCenterX=colorFullX+imageView3.getWidth()/2;
        int whiteCenterY=colorFullY+imageView3.getHeight()/2;

        if(0<=whiteCenterX && whiteCenterX<=boxSize && boxY<=whiteCenterY && whiteCenterY<=boxY+boxSize){
            score+=20;
            colorFullX=-10;
            soundPlay.playHitSound();

        }
       //oxDog
        int oxDogCenterX= blackX+imageView4.getWidth()/2;
        int oxDogCenterY= blackY+imageView4.getHeight()/2;
        if(0<=oxDogCenterX && oxDogCenterX<=boxSize && boxY<= oxDogCenterY && oxDogCenterY<=boxY+boxSize){
            //Stop Timer!!
            timer.cancel();
            /*timer=null;*/
            soundPlay.playOverSound();
            //Show Result
            Intent intent=new Intent(getApplicationContext(),Result.class);
            intent.putExtra("Score",score);
            startActivity(intent);
        }
    }


//Touch the screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(start_flag==false){
start_flag=true;
//why get frame height and hungry height here?
            //because the UI has not been set on the screen in OnCreate()!!
frameLayout=findViewById(R.id.frame_layout);
frameHeight=frameLayout.getHeight();
boxY=(int)imageView1.getY();


boxSize=imageView1.getHeight();

textView2.setVisibility(View.GONE);

//change position every 20 millisecond
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
            },0,20);
        }else{
            /*The box moves upward*/
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                action_flag=true;
            }else if(event.getAction()==MotionEvent.ACTION_UP){
                action_flag=false;

            }
        }



        return super.onTouchEvent(event);
    }
    //Disable Return Button

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


}
