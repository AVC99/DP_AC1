package model;

import java.util.Objects;

public class Spider extends Enemy{

    public Spider(int damage, int xPosition, int yPosition) {
        super(damage, xPosition, yPosition);
        this.direction=Enemy.SPIDER_DIRECTIONS[0];
        this.timer=Enemy.SPIDER_TIMER;
    }

    /**
     * function that returns the spider's next move
     * @return spider's next move
     */
    @Override
    public int getNextX() {
        return this.xPosition;
    }

    /**
     * function that returns the spider's next move
     * @return spider's next move
     */
    @Override
    public int getNextY() {
        if(Objects.equals(this.direction, Enemy.SPIDER_DIRECTIONS[0])){
            return this.getYPosition()-1;
        }else return this.getYPosition()+1;
    }

    /**
     * Function that moves the spider
     */
    @Override
    public void move(){
        if(Objects.equals(this.direction, Enemy.SPIDER_DIRECTIONS[0])){
            this.yPosition--;
        }else this.yPosition++;
    }

    /**
     * Function that changes the direction of the spider
     */
    @Override
    public void changeDirection(){
        if(Objects.equals(this.direction, Enemy.SPIDER_DIRECTIONS[0])) this.direction =Enemy.SPIDER_DIRECTIONS[1];
        else this.direction =Enemy.SPIDER_DIRECTIONS[0];
    }
}
