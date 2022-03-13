package view;

import controller.ButtonController;
import controller.KeyController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;


public class MainView extends JFrame {
    private GameMap gameMap;
    private HpBar hpBar;
    private LinkedList<Thread> threadList;


    public MainView(GameMap gameMap) {
        this.gameMap = gameMap;
        setLayout(new BorderLayout());
        configCenter();
        configNorth();
        configSouth();
        configKeys();
        startEnemies();
        configWindow();
    }

    /**
     * Function that configures all the keys
     */
    private void configKeys() {
        this.addKeyListener(new KeyController(this));
        setFocusable(true);
    }

    /**
     * Function that configures the entire window
     */
    private void configWindow() {
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Scuffed TBOI 1.0");
        setVisible(true);
    }

    /**
     * Function that configures the center of the window
     */
    private void configCenter() {
       add(new GameMap(this.gameMap.getGameBoard(),this.gameMap.getPlayerPositionX(),this.gameMap.getPlayerPositionY(),this.gameMap.getPlayerHp(),this.gameMap.getXSize(), this.gameMap.getYSize(),this.gameMap.getEnemyList()),BorderLayout.CENTER);
        add(gameMap);
    }

    /**
     * Function that configures the south of the window
     */
    private void configSouth() {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc= new GridBagConstraints();

        configureButtons(southPanel, gbc);
        getContentPane().add(southPanel,BorderLayout.SOUTH);
    }

    /**
     * Funtion that configures the buttons on the south panel
     * @param southPanel south panel
     * @param gbc GridBagConstraints
     */
    private void configureButtons(JPanel southPanel, GridBagConstraints gbc) {
        gbc.gridx=1; gbc.gridy=0;
        JButton upButton = new JButton(new ImageIcon(UsedPaths.UP_ARROW_PATH));
        upButton.setActionCommand("UP");
        upButton.addActionListener(new ButtonController(this));
        upButton.setFocusable(false);
        southPanel.add(upButton, gbc);

        gbc.gridx=0;gbc.gridy=1;
        JButton leftButton = new JButton(new ImageIcon(UsedPaths.LEFT_ARROW_PATH));
        leftButton.setActionCommand("LEFT");
        leftButton.addActionListener(new ButtonController(this));
        leftButton.setFocusable(false);
        southPanel.add(leftButton,gbc);

        gbc.gridx=1;gbc.gridy=1;
        JButton downButton = new JButton(new ImageIcon(UsedPaths.DOWN_ARROW_PATH));
        downButton.setActionCommand("DOWN");
        downButton.addActionListener(new ButtonController(this));
        downButton.setFocusable(false);
        southPanel.add(downButton,gbc);

        gbc.gridx=2;gbc.gridy=1;
        JButton rightButton = new JButton(new ImageIcon(UsedPaths.RIGHT_ARROW_PATH));
        rightButton.setActionCommand("RIGHT");
        rightButton.addActionListener(new ButtonController(this));
        rightButton.setFocusable(false);
        southPanel.add(rightButton,gbc);
    }

    /**
     * Function that configures the north portion of the window
     */
    private void configNorth() {
        hpBar= new HpBar();
        add(hpBar, BorderLayout.NORTH);
    }

    /**
     * Function that receives the button/key information to move
     * @param direction direction desired to move Isaac
     */
    public void moveIsaac(String direction){
        int gotoX;
        int gotoY;
        switch (direction){
            case "up"->{
                gotoX=gameMap.getPlayerPositionX()-1;
                gotoY=gameMap.getPlayerPositionY();
                if(canIsaacMove(gotoX, gotoY)){
                    actuallyMove(gotoX, gotoY);
                }
            }
            case "down"->{
                gotoX=gameMap.getPlayerPositionX()+1;
                gotoY=gameMap.getPlayerPositionY();
                if(canIsaacMove(gotoX,gotoY)){
                   actuallyMove(gotoX,gotoY);
                }
            }
            case"right"->{
                gotoX=gameMap.getPlayerPositionX();
                gotoY=gameMap.getPlayerPositionY()+1;
                if(canIsaacMove(gotoX,gotoY)){
                    actuallyMove(gotoX,gotoY);
                }
            }
            case "left"->{
                gotoX=gameMap.getPlayerPositionX();
                gotoY=gameMap.getPlayerPositionY()-1;
                if(canIsaacMove(gotoX,gotoY)){
                    actuallyMove(gotoX,gotoY);
                }
            }
        }
    }

    /**
     * Function that moves Isaac to the desired position
     * @param gotoX x Position to move
     * @param gotoY y Position to move
     */
    private void actuallyMove(int gotoX, int gotoY) {
        gameMap.setPlayerPosition(gotoX, gotoY);
        if(hasIsaacWon(gotoX, gotoY)){
            createVictoryPane();
        }else{
            if(isSpikeCell(gotoX, gotoY)){
                isaacTakesDmg();
            }
            moveIntoEnemyCell(gotoX, gotoY);
            if(hasIsaacLost()){
                createLosingPane();
            }
        }
    }

    /**
     * Function that tells if the player has lost all his hp
     * @return boolean
     */
    private boolean hasIsaacLost(){
        return gameMap.getPlayerHp() <= 0;
    }

    /**
     * Function that checks if isaac has moved into an enemy cell and if it has makes the player take dmg
     * @param gotoX go-to x
     * @param gotoY go-to y
     */
    private void moveIntoEnemyCell(int gotoX, int gotoY) {
        for(Enemy e: gameMap.getEnemyList()){
            if(gotoX==e.getXPosition()&&gotoY==e.getYPosition()){
                takeEnemyDamage(e);
            }
        }
    }

    /**
     * Funtion that makes the player take damage form an enemy
     * @param e enemy
     */
    private void takeEnemyDamage(Enemy e) {
        gameMap.takeEnemyDmg(e);
        hpBar.setValue(gameMap.getPlayerHp());
    }

    /**
     * Function that creates the victory Pane
     */
    private void createVictoryPane() {
        stopEnemies();
        JOptionPane.showMessageDialog(null,"WINNER WINNER CHICKEN DINNER","Winner",JOptionPane.ERROR_MESSAGE);
        gameMap.setIsaacToStart();
        hpBar.setValue(10);
        startEnemies();
    }

    /**
     * Function that creates the loss panel
     */
    private void createLosingPane(){
        stopEnemies();
        JOptionPane.showMessageDialog(null,"Unlucky you died L2P","You Lost",JOptionPane.ERROR_MESSAGE);
        gameMap.setIsaacToStart();
        hpBar.setValue(10);
        startEnemies();
    }

    /**
     * Function that reduces the player hp when Isaac takes dmg
     */
    private void isaacTakesDmg() {
        gameMap.takeSpikeDmg();
        hpBar.setValue(gameMap.getPlayerHp());
    }

    /**
     * Function that tells if the go-to position has spikes in it
     * @param x x go-to position
     * @param y y go-to position
     * @return true/false
     */
    private boolean isSpikeCell(int x, int y) {
        return gameMap.getGameBoard()[x][y] == 'X';
    }

    /**
     * Function that tells if the go-to is not a wall or off the map
     * @param x x go-to position
     * @param y y go-to position
     * @return true/false
     */
    private boolean canIsaacMove(int x, int y){
       if( x>0 && x<=16 && y>0 && y<=16 && gameMap.getGameBoard()[x][y]!='#') return true;
       else return  false;
    }

    /**
     * Function that returns if the enemy can move to the desired position
     * @param x x
     * @param y y
     * @return true/false
     */
    public boolean canEnemyMove(int x, int y){
        if( x>0 && x<16 && y>0 && y<16 && this.gameMap.getGameBoard()[x][y]!='#') return true;
        else return  false;
    }

    /**
     * Function that returns if Isaac has won the Game
     * @param x x go-to position
     * @param y y go-to position
     * @return true/false
     */
    private boolean hasIsaacWon(int x,int y){
        return gameMap.getGameBoard()[x][y] == 'W';
    }

    /**
     * Funtion that moves the selected enemy and repaints the view
     * @param enemy selected enemy
     */
    public void moveEnemy(Enemy enemy) {
        if(isIsaacThere(enemy.getNextX(), enemy.getNextY())){
            takeEnemyDamage(enemy);
            hpBar.repaint();
        }
        enemy.move();

        this.gameMap.repaint();
        if(hasIsaacLost())createLosingPane();
    }

    private boolean isIsaacThere(int x, int y) {
        if(y==gameMap.getPlayerPositionY() && x==gameMap.getPlayerPositionX()){
          return true;
        }else return false;
    }

    /**
     * Function that starts a new thread for all enemies and starts the thread
     */
    private void startEnemies(){
        this.threadList= new LinkedList<>();
            for (Enemy e : this.gameMap.getEnemyList()) {
                Thread t = new Thread(new EnemyThread(this, e));
                t.start();
               this.threadList.add(t);
            }

    }

    /**
     * Function that stops enemies from moving
     */
    private void stopEnemies(){
        for (Thread t : this.threadList) {
            t.stop();
        }
    }

}
