package com.example.bakingapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.root.App;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getComponent().inject(this);
    }
}
