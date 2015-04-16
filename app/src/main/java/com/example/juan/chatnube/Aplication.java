package com.example.juan.chatnube;


import android.app.Application;

import com.parse.Parse;


/**
 * Created by Juan on 06/03/2015.
 */
public class Aplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this,"0g7C9DWfcs4LDsqiffuL912a6xQdRJrbUvTONmmB", "d05p1dpO4vVEXGi99I5MJ3SHBkznTfRYlAaUEwBX");






    }

}
