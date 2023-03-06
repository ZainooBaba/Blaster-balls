package com.example.betteractualgame;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final float ChamberRadius = 120f;
    private static final float enemySpacing = 20;
    private int coins=0;
    Thread thread;
    MediaPlayer music;
    MediaPlayer hit;
    MediaPlayer lose;
    MediaPlayer shoot;
    CustomSurfaceView customSurfaceView = null;
    MyThread t = new MyThread();
    Bullet chamber;
    boolean isHoldingBall = false;
    int score = 0;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    float enemySize = 100f;
    Long level = 0L;
    int delayTillNext = 0;
    private Button playButton = null;
    private Button playAgainButtion=null;
    private Button settingsBack=null;
    private Button menuButton=null;
    private Button goToSettings=null;
    private LinearLayout canvasLayout = null;
    private boolean isInvicible = false;
    ConstraintLayout deadMenu;
    ConstraintLayout mainMenu;
    ConstraintLayout settingMenu;
    Switch invAim;
    Switch aimAssist;
    Switch hardMode;
    SeekBar musicVol;
    SeekBar sxfVol;
    ImageView sfxImage;
    ImageView musicImage;
    boolean isHard;
    boolean isInvAim;
    boolean notPlaying=true;
    TextView scoreBoard=null;
    boolean ownColor[]={false,false,false,true,false,false};
    String colorNames[]={"Blue","Green","Purple","Yellow","Cyan","Red"};
    Button colorButtonsA;
    Button colorButtonsB;
    Button colorButtonsC;
    Button colorButtonsD;
    Button colorButtonsE;
    Button colorButtonsF;
    Button shopButton;
    TextView coinView;
    boolean allwaysColor=false;

    int selectedColor=Color.YELLOW;

    private void renderButtonColorNames(){
        colorButtonsA.setText(colorNames[0]+(ownColor[0]?"":"-100C"));
        colorButtonsB.setText(colorNames[1]+(ownColor[1]?"":"-100C"));
        colorButtonsC.setText(colorNames[2]+(ownColor[2]?"":"-100C"));
        colorButtonsD.setText(colorNames[3]+(ownColor[3]?"":"-100C"));
        colorButtonsE.setText(colorNames[4]+(ownColor[4]?"":"-100C"));
        colorButtonsF.setText(colorNames[5]+(ownColor[5]?"":"-100C"));
        coinView.setText("You currently have "+coins+"C");
        customSurfaceView.setBulletColor(selectedColor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("yoo000");
        hit = MediaPlayer.create(getApplicationContext(), R.raw.hit);
        lose = MediaPlayer.create(getApplicationContext(), R.raw.lose);
        shoot = MediaPlayer.create(getApplicationContext(), R.raw.shoot);

        music = MediaPlayer.create(getApplicationContext(), R.raw.music);
        music.setLooping(true);
        music.start();

        setTitle("dev2qa.com - Android SurfaceView Drawing Example.");

        initControls();

        getSupportActionBar().hide();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        customSurfaceView = new CustomSurfaceView(getApplicationContext());

        customSurfaceView.setOnTouchListener(this);

        canvasLayout.addView(customSurfaceView);


    }

    private void resetVariables(){
        enemySize=isHard?50f:100f;
        customSurfaceView.setBulletColor(selectedColor);
        deadMenu.setVisibility(View.INVISIBLE);
        chamber = new Bullet(customSurfaceView.bulletSpawnX, customSurfaceView.bulletSpawnY, 0, 0);
        isHoldingBall = false;
        score = 0;
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        level = 0L;
        delayTillNext = 0;
        isInvicible=false;
        notPlaying=false;
        allwaysColor=false;

    }

    private Button BackToMenu;

    private void initControls() {



        if (playButton == null) {
            playButton = findViewById(R.id.play);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainMenu.setVisibility(View.INVISIBLE);
                    resetVariables();
                    t.setRunning(true);
                    if(!t.isAlive()) {
                        t.start();
                    }
                }
            });
        }
        if(goToSettings==null){
            goToSettings=findViewById(R.id.settings);
            goToSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    settingMenu.setVisibility(View.VISIBLE);
                    mainMenu.setVisibility(View.INVISIBLE);
                }
            });
        }
        if(playAgainButtion == null){
            playAgainButtion=findViewById(R.id.playAgain);
            playAgainButtion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deadMenu.setVisibility(View.INVISIBLE);
                    resetVariables();
                    t.setRunning(true);
                }
            });
        }
        if(menuButton== null){
            menuButton=findViewById(R.id.Menu);
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainMenu.setVisibility(View.VISIBLE);
                    deadMenu.setVisibility(View.INVISIBLE);
                }
            });
        }
        if(shopButton==null){
            shopButton=findViewById(R.id.shop);
            shopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.shopium).setVisibility(View.VISIBLE);
                    mainMenu.setVisibility(View.INVISIBLE);
                    renderButtonColorNames();
                    allwaysColor=true;
                }
            });
        }
        if(coinView==null){
            coinView=findViewById(R.id.coinDisplay);
        }
        if(BackToMenu==null){
            BackToMenu=findViewById(R.id.BackToMenu);
            BackToMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainMenu.setVisibility(View.VISIBLE);
                    System.out.println("SUP BUDDY");
                    findViewById(R.id.shopium).setVisibility(View.INVISIBLE);

                }
            });
        }
        if(settingsBack==null){
            settingsBack=findViewById(R.id.backSet);
            settingsBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainMenu.setVisibility(View.VISIBLE);
                    System.out.println("SUP BUDDY");
                    settingMenu.setVisibility(View.INVISIBLE);

                }
            });
        }
        if(invAim==null){
            invAim=findViewById(R.id.invAim);
            invAim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isInvAim=isChecked;
                }
            });
        }
        if(hardMode==null){
            hardMode=findViewById(R.id.hardMode);
            hardMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isHard=isChecked;
                }
            });

        }
        if(aimAssist==null){
            aimAssist=findViewById(R.id.aimAssist);
            aimAssist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) coins+=10000;
                }
            });
        }
        if(sxfVol==null){
            sxfVol=findViewById(R.id.SfxseekBar);
            sxfVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int num=((100-seekBar.getProgress())/25);
                    switch (num){
                        case 0:
                            sfxImage.setImageResource(R.drawable.vol3);                            break;
                        case 1:
                            sfxImage.setImageResource(R.drawable.vol2);                            break;
                        case 2:
                            sfxImage.setImageResource(R.drawable.vol1);                            break;
                        case 3:
                            sfxImage.setImageResource(R.drawable.vol0);                            break;
                    }
                    float volLev = (float) (1 - (Math.log(100f - (float)seekBar.getProgress()) / Math.log(100f)));
                    shoot.setVolume(volLev,volLev);
                    hit.setVolume(volLev,volLev);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
                });
            }
        if(sfxImage==null){
            sfxImage=findViewById(R.id.SfxImageView);
        }
        if(musicVol==null){
            musicVol=findViewById(R.id.MusicseekBar);
            musicVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int num=((100-seekBar.getProgress())/25);
                    switch (num){
                        case 0:
                            musicImage.setImageResource(R.drawable.vol3);                            break;
                        case 1:
                            musicImage.setImageResource(R.drawable.vol2);                            break;
                        case 2:
                            musicImage.setImageResource(R.drawable.vol1);                            break;
                        case 3:
                            musicImage.setImageResource(R.drawable.vol0);                            break;
                    }
                    float volLev = (float) (1 - (Math.log(100f - (float)seekBar.getProgress()) / Math.log(100f)));
                    music.setVolume(volLev,volLev);
                    lose.setVolume(volLev,volLev);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }
        if(musicImage==null){
            musicImage=findViewById(R.id.MusicimageView);
        }
        if(settingMenu==null){
            settingMenu=findViewById(R.id.settingsMenu);
        }
        if(mainMenu==null){
            mainMenu=findViewById(R.id.mainMenu);
        }
        if (deadMenu == null) {
            deadMenu= findViewById(R.id.DeadMenu);
            deadMenu.setVisibility(View.INVISIBLE);
        }
        if (canvasLayout == null) {
            canvasLayout = findViewById(R.id.customViewLayout);
        }
        if(scoreBoard==null){
            scoreBoard=findViewById(R.id.scoreBoard);
        }
        ImageView img =findViewById(R.id.imageView);
        img.setImageResource(R.drawable.zainimage);
        colorButtonsA=findViewById(R.id.blue);
        colorButtonsA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=0;
                if(!ownColor[num]){
                    if(coins>=100){
                        coins-=100;
                        ownColor[num]=true;
                        selectedColor=Color.BLUE;
                    }
                }else {
                    selectedColor=Color.BLUE;
                }
                renderButtonColorNames();
            }
        });
        colorButtonsB=findViewById(R.id.green);
        colorButtonsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=1;
                if(!ownColor[num]){
                    if(coins>=100){
                        coins-=100;
                        ownColor[num]=true;
                        selectedColor=Color.GREEN;
                    }
                }else {
                    selectedColor=Color.GREEN;
                }
                renderButtonColorNames();
            }
        });
        colorButtonsC=findViewById(R.id.purple);
        colorButtonsC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=2;
                if(!ownColor[num]){
                    if(coins>=100){
                        coins-=100;
                        ownColor[num]=true;
                        selectedColor=Color.MAGENTA;
                    }
                }else {
                    selectedColor=Color.MAGENTA;
                }
                renderButtonColorNames();
            }
        });
        colorButtonsD=findViewById(R.id.Yellow);
        colorButtonsD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=3;
                if(!ownColor[num]){
                    if(coins>=100){
                        coins-=100;
                        ownColor[num]=true;
                        selectedColor=Color.YELLOW;
                    }
                }else {
                    selectedColor=Color.YELLOW;
                }
                renderButtonColorNames();
            }
        });
        colorButtonsE=findViewById(R.id.Cyan);
        colorButtonsE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=4;
                if(!ownColor[num]){
                    if(coins>=100){
                        coins-=100;
                        ownColor[num]=true;
                        selectedColor=Color.CYAN;
                    }
                }else {
                    selectedColor=Color.CYAN;
                }
                renderButtonColorNames();
            }
        });
        colorButtonsF=findViewById(R.id.Red);
        colorButtonsF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=5;
                if(!ownColor[num]){
                    if(coins>=100){
                        coins-=100;
                        ownColor[num]=true;
                        selectedColor=Color.RED;
                    }
                }else {
                    selectedColor=Color.RED;
                }
                renderButtonColorNames();
            }
        });

    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        if(notPlaying)return true;
        if (e.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            isHoldingBall = distanceFromGun(e.getX(), e.getY()) <= customSurfaceView.bulletSelectSize;
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            if (isHoldingBall) {
                if (distanceFromGun(e.getX(), e.getY()) >= customSurfaceView.bulletShootSize) {
                    shoot.start();
                    isHoldingBall = false;
                    double magnitude = Math.min(distanceFromGun(e.getX(), e.getY()), ChamberRadius);
                    double angle = (Math.atan((customSurfaceView.bulletSpawnX - e.getX()) / (customSurfaceView.bulletSpawnY - e.getY())));
                    Bullet b = chamber.clone();
                    b.velocityX = (isInvAim?(-1f):1f)*(float) (((((e.getY() < customSurfaceView.bulletSpawnY) ? 1 : -1) * magnitude * Math.sin(angle))) * (0.15));
                    b.velocityY = (float) (((((e.getY() < customSurfaceView.bulletSpawnY) ? 1 : -1) * magnitude * Math.cos(angle))) * (0.15));
                    chamber = new Bullet(customSurfaceView.bulletSpawnX, customSurfaceView.bulletSpawnY, 0, 0);
                    bullets.add(b);
                } else {
                    isHoldingBall = false;
                    chamber.x = customSurfaceView.bulletSpawnX;
                    chamber.y = customSurfaceView.bulletSpawnY;
                }
            }

        } else if (isHoldingBall) {
            double magnitude = Math.min(distanceFromGun(e.getX(), e.getY()), ChamberRadius);
            double angle = (Math.atan((customSurfaceView.bulletSpawnX - e.getX()) / (customSurfaceView.bulletSpawnY - e.getY())));
            chamber.x = (float) (customSurfaceView.bulletSpawnX - (isInvAim?(-1f):1f)*((((e.getY() < customSurfaceView.bulletSpawnY) ? 1 : -1) * magnitude * Math.sin(angle))));
            chamber.y = (float) (customSurfaceView.bulletSpawnY - (((e.getY() < customSurfaceView.bulletSpawnY) ? 1 : -1) * magnitude * Math.cos(angle)));
        }
        return true;
    }

    private float distanceFromGun(float x, float y) {
        return (float) Math.sqrt(Math.pow(customSurfaceView.bulletSpawnX - x, 2) + Math.pow(customSurfaceView.bulletSpawnY - y, 2));
    }

    private void draw() {
        if (customSurfaceView == null) {
            if (getApplicationContext() == null) {
                return;
            }
            customSurfaceView = new CustomSurfaceView(getApplicationContext());
            return;
        }
        customSurfaceView.lock();
        customSurfaceView.drawBackground();
        for (Bullet b : bullets) {
            customSurfaceView.drawBullet(b.x, b.y, b.getBulletRaduis(), false);
        }
        for (Enemy e : enemies) {
            customSurfaceView.drawEnemy(e.x, e.y, e.getEnemySize());
        }
        customSurfaceView.drawBullet(chamber.x, chamber.y, chamber.getBulletRaduis(), (!(distanceFromGun(chamber.x, chamber.y) >= customSurfaceView.bulletShootSize))&&(!allwaysColor));
        customSurfaceView.drawScore(score);
        customSurfaceView.pushers();
    }

    private void createEnemy() {
        int lvl = (int) (((level * 17) / 5000));
        Enemy e = new Enemy((float) (Math.random() * (customSurfaceView.getWidth() - enemySize)), -enemySize, (float) ((1.5 + ((lvl * .2)) * ((Math.random() * 1.5) + 1))), enemySize);
        if (lvl >= (Math.random() * 10)) {
            boolean isLeftGreater = (e.x > (customSurfaceView.getWidth() - enemySize) - (e.x + enemySize));
            float boundA = e.x;
            float boundB = isLeftGreater ? (enemySpacing + enemySize) : (customSurfaceView.getWidth() - (enemySpacing + enemySize + enemySize));
            float distance = (float) (Math.random() * (Math.abs(boundB - boundA)));
            Enemy e2 = new Enemy(isLeftGreater ? e.x - distance : e.x + enemySpacing + distance, -enemySize, (float) ((1.5 + ((lvl * .2)) * ((Math.random() * 1.5) + 1))), enemySize);
            enemies.add(e2);
        }
        enemies.add(e);
        delayTillNext = (int) (Math.max(20 - lvl, 0) * ((Math.random() * 5) + 10)) + 70;
    }

    private void update() {
        if (level == 10) {
            chamber.x = customSurfaceView.bulletSpawnX;
            chamber.y = customSurfaceView.bulletSpawnY;
        }
        //Create enemy
        level++;
        if (delayTillNext <= 0) {
            createEnemy();
        }
        delayTillNext--;
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);
            boolean hitBullet = false;
            for (int j = bullets.size() - 1; j >= 0; j--) {
                Bullet b = bullets.get(j);
                if (b.collidesWith(e.x, e.y, e.getEnemySize())) {
                    hitBullet = true;
                    bullets.remove(b);
                }
            }
            if (hitBullet) {
                hit.start();
                enemies.remove(e);
                score++;
            }
            System.out.println(e.y);
        }
        for (Bullet b : bullets) {
            b.increment();
        }
        for (int j = bullets.size() - 1; j >= 0; j--) {
            Bullet b = bullets.get(j);
            if ((b.x < -100) || (b.x > 2000f) || (b.y < -b.getBulletRaduis()) || (b.y > 2000f)) {
                bullets.remove(j);
            }
        }
        for (int j = enemies.size() - 1; j >= 0; j--) {
            Enemy b = enemies.get(j);
            b.increment();
            if ((b.y > 2000f)) {
                enemies.remove(j);
            }
        }
        boolean died = false;
        for (Enemy e : enemies) {
            System.out.println(e.touchesGoal(customSurfaceView.goalX, customSurfaceView.goalY, customSurfaceView.goalSize));
            if (e.touchesGoal(customSurfaceView.goalX, customSurfaceView.goalY, customSurfaceView.goalSize)) {
                died = true;
            }
        }
        if(died&&!isInvicible){
            lose.start();
            isInvicible=true;
            notPlaying=true;
            coins+=score;
            runOnUiThread (new Thread(new Runnable() {
                public void run() {
                    deadMenu.setVisibility(View.VISIBLE);
                    scoreBoard.setText("You scored at total of "+(score)+" points");
                }
            }));
        }
    }

    public class MyThread extends Thread {


        private boolean running = false;


        public void setRunning(boolean run) {
            running = run;
        }

        @Override
        public void run() {
            while (running) {
                update();
                draw();
            }

            try {
                sleep(17);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}






