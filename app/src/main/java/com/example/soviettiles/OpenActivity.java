package com.example.soviettiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class OpenActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        start = findViewById(R.id.startbutton);
        start.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(OpenActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        return;
    }
}
