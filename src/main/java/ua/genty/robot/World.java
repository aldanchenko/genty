package ua.genty.robot;

import ua.genty.robot.helpers.AbstractFactory;
import ua.genty.robot.enums.Sensors;
import ua.genty.robot.enums.WorldElements;
import ua.genty.robot.beans.Position;

import java.util.HashMap;

/**
 * Author: Alexander Danchenko.
 */
public class World {
    public static final int X_POS = 0;
    public static final int Y_POS = 0;

    public static final int MAP_WIDTH = 8;
    public static final int MAP_HEIGHT = 8;

    public static int MAX_FITNESS = MAP_WIDTH * 4 - 5;
    
    private WorldElements[][] map;

    public World() {
        map = AbstractFactory.getInstance().createMap(MAP_WIDTH, MAP_HEIGHT);
//        map = MapFactory.createMap(MAP_WIDTH, MAP_HEIGHT);
    }

    public WorldElements[][] getMap() {
        return map;
    }
    
    public java.util.Map getSensors(Position position) {
        return  getSensors(position.getI(), position.getJ());
    }

    public java.util.Map getSensors(int ipos, int jpos) {
        java.util.Map<Sensors, Boolean> sensors = new HashMap<Sensors, Boolean>();

        sensors.put(Sensors.WALL_NORTH_WEST, isWallHere(ipos - 1, jpos - 1));
        sensors.put(Sensors.WALL_NORTH, isWallHere(ipos - 1, jpos));
        sensors.put(Sensors.WALL_NORTH_EAST, isWallHere(ipos - 1, jpos + 1));

        sensors.put(Sensors.WALL_WEST, isWallHere(ipos, jpos - 1));
        sensors.put(Sensors.WALL_EAST, isWallHere(ipos, jpos + 1));

        sensors.put(Sensors.WALL_SOUTH_WEST, isWallHere(ipos + 1, jpos - 1));
        sensors.put(Sensors.WALL_SOUTH, isWallHere(ipos + 1, jpos));
        sensors.put(Sensors.WALL_SOUTH_EAST, isWallHere(ipos + 1, jpos + 1));

        return sensors;
    }

    public Boolean isWallHere(Position position) {
        return isWallHere(position.getI(), position.getJ());
    }

    public Boolean isWallHere(int i, int j) {

        Boolean isWall;

        try {
            isWall = WorldElements.WALL.equals(map[i][j]);
        } catch (ArrayIndexOutOfBoundsException exp) {
            isWall = Boolean.TRUE;
        }

        return isWall;
    }

    public Boolean isFoodHere(Position position) {
        return isFoodHere(position.getI(), position.getJ());
    }

    public Boolean isFoodHere(int i, int j) {

        Boolean isFood;

        try {
            isFood = WorldElements.FOOD.equals(map[i][j]);
        } catch (ArrayIndexOutOfBoundsException exp) {
            isFood = Boolean.FALSE;
        }

        return isFood;
    }

    public Boolean isMineHere(Position position) {
        return isMineHere(position.getI(), position.getJ());
    }

    public Boolean isMineHere(int i, int j) {

        Boolean isMine;

        try {
            isMine = WorldElements.MINE.equals(map[i][j]);
        } catch (ArrayIndexOutOfBoundsException exp) {
            isMine = Boolean.FALSE;
        }

        return isMine;
    }

    public void setGrassHere(int i, int j) {

        if (isFoodHere(i, j)) {
            map[i][j] = WorldElements.GRASS;    
        } else {
            //TODO: throw new Exception();
        }
    }

    public void setWallHere(int i, int j) {
        map[i][j] = WorldElements.WALL;
    }

    public void setWorldElement(WorldElements element, int i, int j) {
        map[i][j] = element;        
    }
}
