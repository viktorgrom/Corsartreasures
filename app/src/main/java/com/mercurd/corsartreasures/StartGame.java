package com.mercurd.corsartreasures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);
    }

    public void startGame(View view) {

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}