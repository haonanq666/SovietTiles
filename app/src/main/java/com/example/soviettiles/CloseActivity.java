package com.example.soviettiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

public class CloseActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tx;
    private int score;
    private ConstraintLayout layout;
    private String endcond;
    private Button redo;
    private TextView st;

    private String clickbackg  = "You clicked on the background!";
    private String clickcapital = "You clicked on a Capitalist!";
    private String missedcomrade = "You missed a Comrade!";
    private String messg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        layout = findViewById(R.id.layoutc);

        layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        redo = findViewById(R.id.res);
        redo.setOnClickListener(this);

        tx = findViewById(R.id.scoredisp);
        st = findViewById(R.id.scoreview);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        endcond = intent.getStringExtra("losing condition");
        if(endcond.equals("background")){
            messg = clickbackg;
        }else if(endcond.equals("clickedwrong")){
            messg = clickcapital;
        }else if(endcond.equals("missedclick")){
            messg = missedcomrade;
        }
        tx.setText(messg);
        st.setText("Score: "+Integer.toString(score));
        tx.setTextColor(Color.parseColor("#FFD900"));
        layout.setBackgroundColor(Color.parseColor("#CD0000"));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(CloseActivity.this, OpenActivity.class);
        startActivity(i);
        finish();
        return;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(CloseActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        return;
    }
}
