package com.example.betteractualgame;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameView extends AppCompatActivity implements Runnable {

    CustomSurfaceView customSurfaceView = null;
    private LinearLayout canvasLayout = null;


    private final boolean isPlaying = true;

    int score = 0;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bullets.add(new Bullet(100f, 100f, 0f, 0f));
        customSurfaceView = new CustomSurfaceView(getApplicationContext());
        canvasLayout.addView(customSurfaceView);
        if (canvasLayout == null) {
            canvasLayout = findViewById(R.id.customViewLayout);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (isPlaying) {
                update();
                draw();
                sleep();
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void draw() {
//        if(customSurfaceView == null){
//            if(getApplicationContext()==null){
//                return;
//            }
//            customSurfaceView = new CustomSurfaceView(getApplicationContext());
//            return;
//        }
//        customSurfaceView.drawBackground();
//        for (Bullet b:bullets) {
//            customSurfaceView.drawBullet(b.x,b.y,b.getBulletRaduis());
//        }
//        for (Enemy e:enemies) {
//            customSurfaceView.drawEnemy(e.x,e.y,e.getEnemySize());
//        }
    }

    private void update() {
        //Create enemy
        for (Enemy e : enemies) {
            boolean hitBullet = false;
            for (Bullet b : bullets) {
                if (b.collidesWith(e.x, e.y, e.getEnemySize())) {
                    hitBullet = true;
                    bullets.remove(b);
                }
                if (hitBullet) enemies.remove(e);
            }
            e.touchesGoal(customSurfaceView.goalX, customSurfaceView.goalY, customSurfaceView.goalSize);
        }
        for (Enemy e : enemies) {
            e.increment();
        }
        for (Bullet b : bullets) {
            b.increment();
        }
        //Shoot Things
    }


}
