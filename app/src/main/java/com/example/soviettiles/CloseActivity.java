package com.example.soviettiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.Math.toIntExact;

public class CloseActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tx;
    private int score;
    private ConstraintLayout layout;
    private String endcond;
    private Button redo;
    private TextView st;
    private TextView tv3;

    private String clickbackg  = "You clicked on the background!";
    private String clickcapital = "You clicked on a Capitalist!";
    private String missedcomrade = "You missed a Comrade!";
    private String messg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File save = new File(this.getFilesDir(), "save.txt");
        int saved_highscore=0;
        try {
            if(!save.exists()) {
                save.createNewFile();
                FileOutputStream write = this.openFileOutput("save.txt", this.MODE_PRIVATE);
                String zero = "0";
                write.write(zero.getBytes());
                write.flush();
                write.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            FileInputStream read = this.openFileInput("save.txt");
            long len = save.length();
            byte[] b = new byte[toIntExact(len)];
            read.read(b);
            saved_highscore = Integer.parseInt(new String(b, "UTF-8"));

        }catch (IOException e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_close);
        layout = findViewById(R.id.layoutc);

        layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        redo = findViewById(R.id.res);
        redo.setOnClickListener(this);

        tx = findViewById(R.id.scoredisp);
        st = findViewById(R.id.scoreview);
        tv3= findViewById(R.id.textView3);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        endcond = intent.getStringExtra("losing condition");
        if(score>saved_highscore){
            try {
                FileOutputStream write = new FileOutputStream(save, false);
                write.write(Integer.toString(score).getBytes());
                write.close();
                write.flush();
                saved_highscore = score;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        tv3.setText("Highscore: "+Integer.toString(saved_highscore));
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
