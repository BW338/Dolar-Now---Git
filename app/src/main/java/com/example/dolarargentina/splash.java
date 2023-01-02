package com.example.dolarargentina;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class splash extends AppCompatActivity {

    ImageView logo;
    ProgressBar pb;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pb = findViewById(R.id.progressBar);
        logo = findViewById(R.id.logo);

        pb.setProgress(30);

        logo.setAlpha(210);
        getSupportActionBar().hide();

        Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        logo.startAnimation(fadein);


        ////////////////////------CLICK AUTOMATICO----------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash.this,MainActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();



            }
        }, 2000);
        //--------------------------------------------------------------
    }

    }
