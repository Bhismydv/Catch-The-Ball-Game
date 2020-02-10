package com.example.dell.catchtheball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }


    public  void startGame(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    //Disable return Button


}
