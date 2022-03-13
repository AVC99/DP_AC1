package controller;

import view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController implements ActionListener {
    private MainView view;

    public ButtonController(MainView view) {
        this.view = view;
    }

    /**
     * Function that controls tha actions performed by the buttons
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "UP"-> this.view.moveIsaac("up");
            case "DOWN"-> this.view.moveIsaac("down");
            case "LEFT"-> this.view.moveIsaac("left");
            case "RIGHT"-> this.view.moveIsaac("right");
        }

    }
}
