package com.example.betteractualgame;

public class Bullet {

    static float bulletRaduis=20f;

    float x;
    float y;
    float velocityX;
    float velocityY;

    public Bullet(float x, float y, float velocityX, float velocityY){
        this.x=x;
        this.y=y;
        this.velocityX=velocityX;
        this.velocityY=velocityY;
    }

    public void increment(){
        x=velocityX+x;
        y=velocityY+y;
    }

    public float getBulletRaduis(){
        return bulletRaduis;
    }

    public boolean collidesWith(float rectX, float rectY, float rectSize){
        if(circleColide(x,y,rectX,rectY,rectSize))return true;
        if(circleColide(x+velocityX,y+velocityY,rectX,rectY,rectSize))return true;
        return false;
    }

    private boolean circleColide(float cx,float cy,float rectX, float rectY, float rectSize){
        float testX=cx;
        float testY=cy;
        if (cx < rectX) testX = rectX;
        else if (cx > rectX+rectSize) testX = rectX+rectSize;
        if (cy < rectY) testY = rectY;
        else if (cy > rectY+rectSize) testY = rectY+rectSize;
        return(Math.sqrt(Math.pow(cx-testX,2)+Math.pow(cy-testY,2))<=bulletRaduis);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Bullet clone(){
        return new Bullet(x,y,velocityX,velocityY);
    }
}
