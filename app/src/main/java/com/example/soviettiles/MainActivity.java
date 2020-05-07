package com.example.soviettiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ConstraintLayout lay;
    private int loopcount = 1;
    private float width;
    private float height;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private TextView scbox;

    private ArrayList<Integer> soviets = new ArrayList<>();
    private ArrayList<Integer> capitalists = new ArrayList<>();

    private MediaPlayer mp;
    private Context This;
    private int score;
    private boolean running = true;

    private MainActivity self = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(MainActivity.this, R.raw.anthemstalin);
        mp.start();
        mp.setLooping(true);
        lay = findViewById(R.id.layout);
        lay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        This = this;
        scbox = findViewById(R.id.scorebox);

        soviets.add(R.drawable.s1);
        soviets.add(R.drawable.s2);
        soviets.add(R.drawable.s3);
        soviets.add(R.drawable.s4);
        soviets.add(R.drawable.s5);
        soviets.add(R.drawable.s6);
        soviets.add(R.drawable.s7);
        soviets.add(R.drawable.s8);
        soviets.add(R.drawable.s9);
        soviets.add(R.drawable.s10);
        soviets.add(R.drawable.s11);
        soviets.add(R.drawable.s12);
        soviets.add(R.drawable.s13);
        soviets.add(R.drawable.s14);
        soviets.add(R.drawable.s15);
        soviets.add(R.drawable.s16);
        soviets.add(R.drawable.s17);
        soviets.add(R.drawable.s18);
        soviets.add(R.drawable.s19);

        capitalists.add(R.drawable.c1);
        capitalists.add(R.drawable.c2);
        capitalists.add(R.drawable.c3);
        capitalists.add(R.drawable.c4);
        capitalists.add(R.drawable.c5);
        capitalists.add(R.drawable.c6);
        capitalists.add(R.drawable.c7);
        capitalists.add(R.drawable.c8);
        capitalists.add(R.drawable.c9);
        capitalists.add(R.drawable.c10);
        capitalists.add(R.drawable.c11);
        capitalists.add(R.drawable.c12);
        capitalists.add(R.drawable.c13);
        capitalists.add(R.drawable.c14);
        capitalists.add(R.drawable.c15);
        capitalists.add(R.drawable.c16);
        capitalists.add(R.drawable.c17);
        capitalists.add(R.drawable.c18);
        capitalists.add(R.drawable.c19);
        tiles.add(new Tile(This, self, lay, width,height));

        score=0;
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end("background");

                return;
            }
        });




    }
    public void makebutton(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                tiles.add(new Tile(This, self, lay, width,height));

            }
        });

    }
    public void delbutton(){
        runOnUiThread(new Runnable(){
            @Override
            public  void run(){
                tiles.remove(0);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {


                if(loopcount%5==0) {
                    lay.setBackgroundColor(Color.parseColor(randcolorhex()));
                    loopcount = 1;
                }


                loopcount++;
                if(!tiles.isEmpty()) {
                    for(Tile p:tiles){
                        p.updatepos();
                        if(p.inc()){
                            score++;
                            scbox.setText(tomsg(score));
                        }else if(p.wrongclick()){
                            end("clickedwrong");
                        }
                    }
                    if (tiles.get(tiles.size()-1).readyfornext()) {
                        makebutton();
                    }
                    if (tiles.get(0).offscreen()) {
                        if(tiles.get(0).missedsoviet()){
                            end("missedclick");
                        }
                        delbutton();
                    }
                }
                if(running) {
                    handler.postDelayed(this, 10);
                }else{
                    return;
                }
            }
        }, 0);


    }




    @Override
    public void onClick(View v) {

    }

    public Integer randimage(boolean soviet){
        Random rand = new Random();
        if(soviet){
            int lol = rand.nextInt(soviets.size());
            return soviets.get(lol);
        }else{
            int lol = rand.nextInt(capitalists.size());
            return capitalists.get(lol);
        }

    }



    private String tomsg(int pres){

        return (Integer.toString(pres));
    }

    private String randcolorhex(){
        Random rand = new Random();
        int randint = rand.nextInt(0xffffff+1);
        return String.format("#%06x", randint);
    }

    private void end(String endcondition){
        Intent i = new Intent (MainActivity.this, CloseActivity.class);
        i.putExtra("losing condition", endcondition);
        i.putExtra("score",  score);
        running = false;
        mp.stop();

        startActivity(i);

        finish();
        return;
    }
}

class Tile extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private boolean clicked;
    private float ypos;
    private float xpos;
    private float width;
    private float height;
    private boolean issoviet;
    private boolean counted;





    Tile(Context context, MainActivity act, ConstraintLayout layout, float w, float h){
        clicked = false;
        counted = false;

        button = new Button(context);
        button.setLayoutParams(new ConstraintLayout.LayoutParams(400, 400));
        width = w;
        height = h;
        xpos = randfloat(0, width-400);
        ypos = -400;
        button.setX(xpos);
        button.setY(ypos);
        button.setOnClickListener(Tile.this);
        layout.addView(button);

        Random r = new Random();
        issoviet = r.nextBoolean();


        button.setBackground(context.getResources().getDrawable(act.randimage(issoviet), context.getApplicationContext().getTheme()));
        button.setText("");


    }

    public void updatepos(){
        ypos+=20;
        button.setY(ypos);
    }

    @Override
    public void onClick(View v) {
        clicked = true;
        if(issoviet){
            button.setText("☭");
            button.setTextSize(100);
            button.setTextColor(Color.parseColor("#FFD900"));
        }



    }

    public boolean isClicked(){
        return clicked;
    }

    private float randfloat(float min, float max){
        Random rand = new Random();

        return rand.nextFloat() * (max - min) + min;
    }
    public boolean readyfornext(){
        return ypos>10;
    }
    public boolean offscreen(){
        return ypos>height+410;
    }
    public boolean inc(){
        if( clicked&&issoviet&&!counted){
            counted = true;
            return true;
        }else{
            return false;
        }
    }
    public boolean wrongclick(){
        return clicked&&!issoviet;
    }
    public boolean missedsoviet(){
        return !clicked && issoviet;
    }
}


