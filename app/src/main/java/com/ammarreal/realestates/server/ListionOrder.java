package com.ammarreal.realestates.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.ammarreal.realestates.R;
import com.ammarreal.realestates.commen.Commen;
import com.ammarreal.realestates.home.Home;
import com.ammarreal.realestates.pojo.HomeModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ListionOrder extends Service implements ChildEventListener {
int id;
    FirebaseDatabase db;
    DatabaseReference request;
    public ListionOrder() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db=FirebaseDatabase.getInstance();
        request=db.getReference(Commen.DATA_REF);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        request.addChildEventListener(this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
       return  null;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        HomeModel request=dataSnapshot.getValue(HomeModel.class);

        shownotifacion(dataSnapshot.getKey(),request);

    }

    private void shownotifacion(String key, HomeModel request) {

        Intent intent=new Intent(getBaseContext(), Home.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,
                intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Real Estates")
                .setContentInfo("New Estates")
                .setContentText("The Real :"+request.getName())
                .setContentIntent(pendingIntent)

                .setSmallIcon(R.drawable.sing);


        NotificationManager Manager=(NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        int reandom=new Random().nextInt(9999-1)+1;
       Manager.notify(reandom,builder.build());
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
