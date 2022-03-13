package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameMap extends JPanel {
    private char[][] gameBoard;
    private int playerPositionX;
    private int playerPositionY;
    private int playerHp;
    private int xSize;
    private int ySize;
    private BufferedImage image;
    private BufferedImage playerimage;

    public GameMap(char[][] gameBoard, int playerPositionX, int playerPositionY, int playerHp, int xSize, int ySize) {
        this.gameBoard = gameBoard;
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
        this.playerHp = playerHp;
        this.xSize = xSize;
        this.ySize = ySize;
        setPlayerImage();
    }


    private void setPlayerImage() {
        try{
           playerimage= ImageIO.read(new File(UsedPaths.PLAYER_PATH));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not find the Player image");
        }
    }
    private void changeCellPath(char cell){
       try{
           switch (cell){
               case'#'-> image=ImageIO.read(new File(UsedPaths.WALL_PATH));
               case ' '-> image=ImageIO.read(new File(UsedPaths.EMPTY_PATH));
               case 'X'-> image=ImageIO.read(new File(UsedPaths.SPIKES_PATH));
               case 'S'-> image=ImageIO.read(new File(UsedPaths.START_PATH));
               case 'W'-> image=ImageIO.read(new File(UsedPaths.VICTORY_PATH));
           }
       }catch (IOException e){
           e.printStackTrace();
           System.out.println("Could not find the cell image");
       }

    }
    public void paintMapConsole(){
        for (int i=0;i<xSize;i++){
            for (int j=0; j<ySize;j++){
                System.out.print(gameBoard[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellHeight= this.getHeight()/ySize;
        int cellWidth= this.getWidth()/xSize;
        for (int i=0;i<xSize;i++){
            for (int j=0; j<ySize;j++){
                changeCellPath(gameBoard[j][i]);
                g.drawImage(image,i*cellWidth,j*cellHeight,cellWidth,cellHeight,null);
                if(j==playerPositionX && i==playerPositionY){
                    g.drawImage(playerimage,i*cellWidth,j*cellHeight,cellWidth,cellHeight,null);
                }
            }
        }
    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public int getPlayerPositionY() {
        return playerPositionY;
    }

    public int getPlayerHp() {
        return playerHp;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setPlayerPosition(int x, int y) {
        this.playerPositionX=x;
        this.playerPositionY=y;
        this.repaint();
    }
    private int getStartX(){
        for (int i=0;i<xSize;i++){
            for (int j=0; j<ySize;j++){
                if (this.gameBoard[i][j]=='S')return i;
            }
        }
        return 0;
    }
    private int getStartY(){
        for (int i=0;i<xSize;i++){
            for (int j=0; j<ySize;j++){
                if (this.gameBoard[i][j]=='S')return j;
            }
        }
        return 0;
    }

    public void takeSpikeDmg() {
        this.playerHp-=1;
    }

    public void setIsaacToStart(){
        this.playerPositionX=getStartX();
        this.playerPositionY=getStartY();
        this.playerHp=10;
        repaint();
    }
}
