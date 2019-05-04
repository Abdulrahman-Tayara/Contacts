package com.tayara.abdulrahman.telephoneguide;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataBase.setContext(this);
    }
}
