package model;

import java.util.Objects;

public class Fly extends Enemy{

    public Fly(int damage, int xPosition, int yPosition) {
        super(damage, xPosition, yPosition);
        this.direction =Enemy.FLY_DIRECTIONS[0];
        this.timer=Enemy.FLY_TIMER;
    }

    /**
     * function that returns the fly's next move
     * @return fly's next move
     */
    @Override
    public int getNextX() {
        if(Objects.equals(this.direction, Enemy.FLY_DIRECTIONS[0])){
            return this.getXPosition()-1;
        }else return this.getXPosition()+1;
    }

    /**
     * function that returns the fly's next move
     * @return fly's next move
     */
    @Override
    public int getNextY() {
        return this.yPosition;
    }

    /**
     * Function that moves the fly
     */
    @Override
    public void move() {
        if(Objects.equals(this.direction, Enemy.FLY_DIRECTIONS[0])){
            this.xPosition--;
        }else this.xPosition++;
    }

    /**
     * Function that changes the fly's direction of movement
     */
    @Override
    public void changeDirection(){
        if(Objects.equals(this.direction, Enemy.FLY_DIRECTIONS[0]))this.direction =Enemy.FLY_DIRECTIONS[1];
        else this.direction =Enemy.FLY_DIRECTIONS[0];

    }
}
