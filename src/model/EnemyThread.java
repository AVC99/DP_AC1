package model;

import view.MainView;

public class EnemyThread implements Runnable{
    private MainView view;
    private Enemy enemy;
    private boolean stop;

    public EnemyThread(MainView view, Enemy enemy) {
        this.view = view;
        this.enemy=enemy;
        this.stop=false;
    }

    /**
     *Function that makes an enemy run and switch directions
     */
    @Override
    public void run() {
        while(!stop){
            if(this.view.canEnemyMove(this.enemy.getNextX(),this.enemy.getNextY())){
                this.view.moveEnemy(this.enemy);
            }else{
                this.enemy.changeDirection();
            }
            try{
                Thread.sleep(this.enemy.timer);
            }catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Thread interrupted unexpectedly");
            }
        }
    }

}
