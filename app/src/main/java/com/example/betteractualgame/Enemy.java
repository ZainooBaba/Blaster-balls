package com.example.betteractualgame;

public class Enemy {
    float enemySizes;

    float x;
    float y;
    float velocityY;


    public Enemy(float x, float y, float velocityY,float enemySizes){

        this.enemySizes=enemySizes;
        this.x=x;
        this.y=y;
        this.velocityY=velocityY;
    }

    public void increment(){
        y+=velocityY;
    }

    public float getEnemySize() {
        return enemySizes;
    }

    public boolean touchesGoal(float cx,float cyBig,float size) {
        float cy= cyBig-10f;
        float testX=cx;
        float testY=cy;
        if (cx < x) testX = x;
        else if (cx > y+enemySizes) testX = x+enemySizes;
        if (cy < y) testY = y;
        else if (cy > y+enemySizes) testY = y+enemySizes;
        return(Math.sqrt(Math.pow(cx-testX,2)+Math.pow(cy-testY,2))<=size);
    }


}
