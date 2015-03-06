package com.example.juan.chatnube;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Juan on 06/03/2015.
 */
public class Aplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);






    }

}
