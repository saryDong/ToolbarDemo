package com.example.abu.toolbardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setExitTransition(new Explode().setDuration(2000));
        getWindow().setEnterTransition(new Explode().setDuration(2000));
        setContentView(R.layout.activity_main);
    }
}
