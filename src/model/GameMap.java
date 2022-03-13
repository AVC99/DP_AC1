package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameMap extends JPanel {
    private char[][] gameBoard;
    private int playerPositionX;
    private int playerPositionY;
    private int playerHp;
    private final int xSize;
    private final int ySize;
    private BufferedImage image;
    private BufferedImage playerImage;
    private BufferedImage spiderImage;
    private BufferedImage flyImage;
    private ArrayList<Enemy> enemyList;
    public static final int SPIKE_DAMAGE=1;

    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    public GameMap(char[][] gameBoard, int playerPositionX, int playerPositionY, int playerHp, int xSize, int ySize, ArrayList<Enemy>enemyList) {
        this.gameBoard = gameBoard;
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
        this.playerHp = playerHp;
        this.xSize = xSize;
        this.ySize = ySize;
        setPlayerImage();
        setFlyImage();
        setSpiderImage();
        this.enemyList=enemyList;
    }

    /**
     * Function that sets the buffered image to the player image
     */
    private void setPlayerImage() {
        try{
           playerImage= ImageIO.read(new File(UsedPaths.PLAYER_PATH));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not find the Player image");
        }
    }
    private void setSpiderImage() {
        try{
            spiderImage= ImageIO.read(new File(UsedPaths.SPIDER_PATH));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not find the Player image");
        }
    }
    private void setFlyImage() {
        try{
           flyImage= ImageIO.read(new File(UsedPaths.FLY_PATH));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not find the Player image");
        }
    }

    /**
     * Function that changes the path of the cell depending of their type
     * @param cell selected cell of the map
     */
    private void changeCellPath(char cell){
       try{
           switch (cell){
               case'#'-> image=ImageIO.read(new File(UsedPaths.WALL_PATH));
               case ' ', '-', '|' -> image=ImageIO.read(new File(UsedPaths.EMPTY_PATH));
               case 'X'-> image=ImageIO.read(new File(UsedPaths.SPIKES_PATH));
               case 'S'-> image=ImageIO.read(new File(UsedPaths.START_PATH));
               case 'W'-> image=ImageIO.read(new File(UsedPaths.VICTORY_PATH));

           }
       }catch (IOException e){
           e.printStackTrace();
           System.out.println("Could not find the cell image");
       }

    }

    /**
     * Function that iterates the map and paints it according to each cell
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellHeight= this.getHeight()/ySize;
        int cellWidth= this.getWidth()/xSize;
        for (int i=0;i<xSize;i++){
            for (int j=0; j<ySize;j++){
                changeCellPath(gameBoard[j][i]);
                g.drawImage(image,i*cellWidth,j*cellHeight,cellWidth,cellHeight,null);
                for(Enemy e: enemyList){
                    if(j==e.getXPosition() && i==e.getYPosition()){
                        if(e instanceof Fly){
                            g.drawImage(flyImage,i*cellWidth,j*cellHeight,cellWidth,cellHeight,null);
                        }else g.drawImage(spiderImage,i*cellWidth,j*cellHeight,cellWidth,cellHeight,null);
                    }
                }
                if(j==playerPositionX && i==playerPositionY){
                    g.drawImage(playerImage,i*cellWidth,j*cellHeight,cellWidth,cellHeight,null);
                }
            }
        }
    }

    /**
     * Getter of the GameBoard
     * @return Game Board
     */
    public char[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Getter of the playerPositionX
     * @return playerPositionX
     */
    public int getPlayerPositionX() {
        return playerPositionX;
    }

    /**
     * Getter of the playerPositionY
     * @return playerPositionY
     */
    public int getPlayerPositionY() {
        return playerPositionY;
    }

    /**
     * Getter of the playerHp
     * @return playerHp
     */
    public int getPlayerHp() {
        return playerHp;
    }

    /**
     * Getter of the Map x size
     * @return Map x size
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * Getter of the Map y size
     * @return Map y size
     */
    public int getYSize() {
        return ySize;
    }

    /**
     * Setter for the player positon
     * @param x x position on the map
     * @param y y position on the map
     */
    public void setPlayerPosition(int x, int y) {
        this.playerPositionX=x;
        this.playerPositionY=y;
        this.repaint();
    }

    /**
     * Getter that gets the x starting position
     * @return x starting position
     */
    private int getStartX(){
        for (int i=0;i<xSize;i++){
            for (int j=0; j<ySize;j++){
                if (this.gameBoard[i][j]=='S')return i;
            }
        }
        return 0;
    }

    /**
     * Getter that gets the y starting position
     * @return y starting position
     */
    private int getStartY(){
        for (int i=0;i<xSize;i++){
            for (int j=0; j<ySize;j++){
                if (this.gameBoard[i][j]=='S')return j;
            }
        }
        return 0;
    }

    /**
     * Function that if the player steps on a spike they loose 1 hp
     */
    public void takeSpikeDmg() {
        this.playerHp-=1;
    }

    /**
     * Function that makes the player loose hp if they collide with an enemy
     * @param enemy enemy the player colides
     */
    public void takeEnemyDmg(Enemy enemy){
        this.playerHp-=enemy.getDamage();
    }

    /**
     * Function that resets Isaac to the start
     */
    public void setIsaacToStart(){
        this.playerPositionX=getStartX();
        this.playerPositionY=getStartY();
        this.playerHp=10;
        repaint();
    }


}
