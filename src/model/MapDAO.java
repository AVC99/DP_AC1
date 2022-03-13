package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MapDAO {
    public MapDAO() {

    }
    public GameMap startGameMap(){
        GameMap gameMap= null;
        try{
            String mapText= new String(Files.readAllBytes(Path.of(UsedPaths.MAP_PATH)));
            /*TODO CAMBIAR \r*/
            String[] lines= mapText.split("\r\n");
            int sizeX=Integer.parseInt(lines[0]);
            int sizeY= Integer.parseInt(lines[1]);
            int playerPositionX=0;
            int playerPositionY=0;
            char[][] gameBoard = new char[sizeX][sizeY];
            for(int i=2;i<lines.length;i++){//Filas
                int j=0;
                for (char c: lines[i].toCharArray()){//letra individual
                    gameBoard[i-2][j]=c;
                    if(c=='S'){
                        playerPositionX=i-2;
                        playerPositionY=j;
                    }
                    j++;
                }
            }
            gameMap= new GameMap(gameBoard,playerPositionX,playerPositionY,10,sizeX,sizeY);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not find the game File!");
        }

        return gameMap;
    }
}
