package com.ammarreal.realestates.commen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Commen {

    public final static String DATA_REF = "estates";
    public static boolean isNetworkOnline(Context context) {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }

    public final static String USERKEY="name";
    public final static String USER_PASS="pass";
}
