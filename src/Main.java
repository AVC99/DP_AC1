import model.Enemy;
import model.EnemyThread;
import model.MapDAO;
import view.MainView;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            MapDAO mapDAO = new MapDAO();
            MainView view= new MainView(mapDAO.startGameMap());

        });
    }
}
