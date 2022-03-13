package view;

import controller.ButtonController;
import controller.KeyController;
import model.GameMap;
import model.UsedPaths;

import javax.swing.*;
import java.awt.*;


public class MainView extends JFrame {
    private GameMap gameMap;
    private HpBar hpBar;
    private JButton upButton;
    private JButton leftButton;
    private JButton downButton;
    private JButton rightButton;


    public MainView(GameMap gameMap) {
        this.gameMap = gameMap;
        setLayout(new BorderLayout());
        configCenter();
        configNorth();
        configSouth();
        configKeys();
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
        setTitle("Scuffed TBOI");
        setVisible(true);
    }

    /**
     * Function that configures the center of the window
     */
    private void configCenter() {
       add(new GameMap(this.gameMap.getGameBoard(),this.gameMap.getPlayerPositionX(),this.gameMap.getPlayerPositionY(),this.gameMap.getPlayerHp(),this.gameMap.getXSize(), this.gameMap.getYSize()),BorderLayout.CENTER);
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

    /**
     * Function that creates the victory Pane
     */
    private void createVictoryPane() {
        JOptionPane.showMessageDialog(null,"WINNER WINNER CHICKEN DINNER","Winner",JOptionPane.ERROR_MESSAGE);
        gameMap.setIsaacToStart();
        hpBar.setValue(10);

    }

    /**
     * Function that creates the loss panel
     */
    private void createLosingPane(){
        JOptionPane.showMessageDialog(null,"Unlucky you died L2P","You Lost",JOptionPane.ERROR_MESSAGE);
        gameMap.setIsaacToStart();
        hpBar.setValue(10);
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
       if( x>0 && x<=16 && gameMap.getGameBoard()[x][y]!='#') return true;
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
}
