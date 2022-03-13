package model;

public abstract class Enemy {
    protected int damage;
    protected int xPosition;
    protected int yPosition;
    protected int timer;
    protected String direction;
    public static final int FLY_DMG=3;
    public static final int FLY_TIMER=300;
    public static final int SPIDER_DMG=5;
    public static final int SPIDER_TIMER=200;
    public static final String[] SPIDER_DIRECTIONS= {"left","right"};
    public static final String[] FLY_DIRECTIONS= {"up","down"};


    public Enemy( int damage,int xPosition, int yPosition) {
        this.damage=damage;
        this.xPosition = xPosition;
        this.yPosition = yPosition;

    }
    public int getNextX(){
        return this.xPosition;
    }
    public int getNextY(){
        return this.yPosition;
    }
    public int getDamage() {
        return damage;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void changeDirection(){

    }

    public void move(){
    }
}
