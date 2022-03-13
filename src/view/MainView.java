package view;

import controller.ButtonController;
import controller.KeyController;
import model.GameMap;
import model.MapDAO;
import model.UsedPaths;

import javax.swing.*;
import java.awt.*;


public class MainView extends JFrame {
    private GameMap gameMap;
    private HpBar hpBar;
    JButton upButton;
    JButton leftButton;
    JButton downButton;
    JButton rightButton;


    public MainView(GameMap gameMap) {
        this.gameMap = gameMap;
        setLayout(new BorderLayout());
        configCenter();
        configNorth();
        configSouth();
        configKeys();
        configWindow();
    }

    private void configKeys() {
        this.addKeyListener(new KeyController(this));
        setFocusable(true);
    }

    private void configWindow() {
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Scuffed TBOI");
        setVisible(true);
    }

    private void configCenter() {
       add(new GameMap(this.gameMap.getGameBoard(),this.gameMap.getPlayerPositionX(),this.gameMap.getPlayerPositionY(),this.gameMap.getPlayerHp(),this.gameMap.getxSize(), this.gameMap.getySize()),BorderLayout.CENTER);
        add(gameMap);
    }

    private void configSouth() {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc= new GridBagConstraints();

        configureButtons(southPanel, gbc);
        getContentPane().add(southPanel,BorderLayout.SOUTH);
    }

    private void configureButtons(JPanel southPanel, GridBagConstraints gbc) {
        gbc.gridx=1; gbc.gridy=0;
        upButton= new JButton(new ImageIcon(UsedPaths.UP_ARROW_PATH));
        upButton.setActionCommand("UP");
        upButton.addActionListener(new ButtonController(this));
        upButton.setFocusable(false);
        southPanel.add(upButton, gbc);

        gbc.gridx=0;gbc.gridy=1;
        leftButton=new JButton(new ImageIcon(UsedPaths.LEFT_ARROW_PATH));
        leftButton.setActionCommand("LEFT");
        leftButton.addActionListener(new ButtonController(this));
        leftButton.setFocusable(false);
        southPanel.add(leftButton,gbc);

        gbc.gridx=1;gbc.gridy=1;
        downButton=new JButton(new ImageIcon(UsedPaths.DOWN_ARROW_PATH));
        downButton.setActionCommand("DOWN");
        downButton.addActionListener(new ButtonController(this));
        downButton.setFocusable(false);
        southPanel.add(downButton,gbc);

        gbc.gridx=2;gbc.gridy=1;
        rightButton=new JButton(new ImageIcon(UsedPaths.RIGHT_ARROW_PATH));
        rightButton.setActionCommand("RIGHT");
        rightButton.addActionListener(new ButtonController(this));
        rightButton.setFocusable(false);
        southPanel.add(rightButton,gbc);
    }

    private void configNorth() {
        hpBar= new HpBar();
        add(hpBar, BorderLayout.NORTH);
    }

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

    private void actuallyMove(int gotoX, int gotoY) {
        gameMap.setPlayerPosition(gotoX, gotoY);
        if(hasIsaacWon(gotoX, gotoY)){
            System.out.println("isaac won");
            createVictoryPane();
        }else{

            if(isSpikeCell(gotoX, gotoY)){
                isaacTakesDmg();
            }
            if(gameMap.getPlayerHp()<=0){
                createLosingPane();
            }
        }
    }

    private void createVictoryPane() {
        JOptionPane.showMessageDialog(null,"WINNER WINNER CHICKEN DINNER","Winner",JOptionPane.ERROR_MESSAGE);
        gameMap.setIsaacToStart();
        hpBar.setValue(10);

    }

    private void createLosingPane(){
        JOptionPane.showMessageDialog(null,"Unlucky you died L2P","You Lost",JOptionPane.ERROR_MESSAGE);
        gameMap.setIsaacToStart();
        hpBar.setValue(10);
    }

    private void isaacTakesDmg() {
        gameMap.takeSpikeDmg();
        hpBar.setValue(gameMap.getPlayerHp());

    }

    private boolean isSpikeCell(int x, int y) {
        return gameMap.getGameBoard()[x][y] == 'X';
    }

    private boolean canIsaacMove(int x, int y){
       if( x>0 && x<=16 && gameMap.getGameBoard()[x][y]!='#') return true;
       else return  false;
    }

    private boolean hasIsaacWon(int x,int y){
        return gameMap.getGameBoard()[x][y] == 'W';
    }
}
