package com.example.dell.catchtheball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView textView3,textView4;
    /*Button button1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView3=findViewById(R.id.score);
        textView4=findViewById(R.id.HighScore);



        int score=getIntent().getIntExtra("Score",0);
        textView3.setText(score+"");
        SharedPreferences sharedPreferences=getSharedPreferences("Game Data", Context.MODE_PRIVATE);
        int highScore=sharedPreferences.getInt("High Score",0);
        if(score>highScore){
            textView4.setText("High Score :"+score);

            //save
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("High Score :",score);
            editor.commit();
        }else {
        textView4.setText("High Score"+highScore);
    }}

    public  void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),Start.class));
    }

    //Disable return Button

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
