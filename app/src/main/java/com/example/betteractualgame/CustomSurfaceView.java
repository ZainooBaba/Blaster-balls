package com.example.betteractualgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder surfaceHolder = null;

    float goalY;
    float goalX;
    float goalSize=700f;

    float bulletSpawnX;
    float bulletSpawnY;

    float bulletSelectSize;
    float bulletShootSize;

    Paint backgroundColor = new Paint();
    Paint chamberColor = new Paint();
    Paint BulletColor = new Paint();
    Paint enemyColor = new Paint();
    Paint aimAssistColor = new Paint();
    Paint shieldColor = new Paint();
    Paint guidColor = new Paint();

    Canvas canvas;


    public CustomSurfaceView(Context context) {
        super(context);
        bulletSelectSize=20f;
        bulletShootSize=75f;
        bulletSpawnY=(float)(this.getHeight())-100f;
        bulletSpawnX=(((float)(this.getWidth()))/((float) 2));
        goalY=(float)this.getHeight();
        goalX=(((float)(this.getWidth()))/((float) 2));
        System.out.println(goalX);
        System.out.println(goalY);
        backgroundColor.setColor(Color.BLACK);
        chamberColor.setColor(Color.WHITE);
        BulletColor.setColor(Color.YELLOW);
        enemyColor.setColor(Color.RED);
        aimAssistColor.setColor(Color.WHITE);
        shieldColor.setColor(Color.DKGRAY);
        guidColor.setColor(Color.GRAY);




        setFocusable(true);

        if(surfaceHolder == null) {
            surfaceHolder = getHolder();
            // Add this as surfaceHolder callback object.
            surfaceHolder.addCallback(this);
        }

        // Set the parent view background color. This can not set surfaceview background color.
//        this.setBackgroundColor(Color.BLUE);

        // Set current surfaceview at top of the view tree.
        this.bringToFront();
//        this.setZOrderOnTop(true);

        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    public void setBulletColor(int x){
        BulletColor.setColor(x);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /* This method will be invoked to draw a circle in canvas. */


    public void drawBackground(){
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), backgroundColor);
        bulletSpawnY=(float)(this.getHeight())-200f;
        bulletSpawnX=(((float)(this.getWidth()))/((float) 2));
        goalY=(float)this.getHeight()+300f;
        goalX=(((float)(this.getWidth()))/((float) 2));
        canvas.drawCircle(goalX,goalY,goalSize,shieldColor);
    }

    private static void setTextSizeForWidth(Paint paint, float desiredWidth, String text) {

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }

    public void drawScore(int score){
        chamberColor.setTextSize(400f);
        Rect bounds = new Rect();
        chamberColor.getTextBounds(String.valueOf(score), 0, String.valueOf(score).length(), bounds);
        canvas.drawText(String.valueOf(score),bulletSpawnX-(bounds.width()/2),450f, chamberColor);
    }

    public void drawBullet(float x, float y, float rad, boolean isChamber){
        canvas.drawCircle(x, y, rad, isChamber? chamberColor :BulletColor);
    }

    public void drawEnemy(float x, float y, float size){
        canvas.drawRect(x, y, x+size, y+size, enemyColor);
    }


    private void update(){
        //Create enemy
        //Move enemy
        //Move bullets
        //Shoot Things
        //Bullet Hits Enemy
        //Enemy touches circle
    }

    public void lock(){
        canvas = surfaceHolder.lockCanvas();
    }

    public void pushers(){
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}