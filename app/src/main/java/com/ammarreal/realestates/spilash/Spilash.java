package com.ammarreal.realestates.spilash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ammarreal.realestates.R;
import com.ammarreal.realestates.commen.Commen;
import com.ammarreal.realestates.commen.SessionManagment;
import com.ammarreal.realestates.home.Home;
import com.ammarreal.realestates.sign.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class Spilash extends AppCompatActivity {
    SessionManagment mSessionManagment;

    private static final int WAIT_TIME =3000;
    private Timer waitTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spilash);
        mSessionManagment = new SessionManagment(this);
        waitTimer = new Timer();
        //Check is login
        Paper.init(this);
        final String user= Paper.book().read(Commen.USERKEY);
        final String pass=Paper.book().read(Commen.USER_PASS);

        waitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Spilash.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(user !=null && pass != null){
                            if (!user.isEmpty()&&!pass.isEmpty()){
                                startActivity(new Intent(Spilash.this, Home.class));

                            }
                            }

                        else{
                            startActivity(new Intent(Spilash.this, LoginActivity.class));

                    }}
                });
            }
        }, WAIT_TIME);
    }
}

