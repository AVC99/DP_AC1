import model.GameMap;
import model.MapDAO;
import view.MainView;

public class Main {

    public static void main(String[] args) {
        MapDAO mapDAO = new MapDAO();
        MainView view= new MainView(mapDAO.startGameMap());

    }
}
