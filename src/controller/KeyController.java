package controller;

import view.MainView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {
    private MainView view;
    private final static int W = 87;
    private final static int A = 65;
    private final static int S = 83;
    private final static int D = 68;
    private final static int UP = 38;
    private final static int DOWN = 40;
    private final static int LEFT = 37;
    private final static int RIGHT = 39;

    public KeyController(MainView view) {
        this.view = view;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case W, UP ->  {
                this.view.moveIsaac("up");
            }
            case A, LEFT -> this.view.moveIsaac("left");
            case S, DOWN -> this.view.moveIsaac("down");
            case D, RIGHT -> this.view.moveIsaac("right");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
