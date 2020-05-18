package com.example.bakingapp.root;

import android.app.Application;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }

}
