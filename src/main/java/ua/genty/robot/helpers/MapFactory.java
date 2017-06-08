package ua.genty.robot.helpers;

import ua.genty.robot.enums.WorldElements;
import ua.genty.robot.World;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: Apr 22, 2008
 * Time: 10:13:16 AM
 */
public class MapFactory {

    private static WorldElements[][] templateMap;

    public static WorldElements[][] createMap(int width, int height) {

        WorldElements[][] map = new WorldElements[width][height];

        if (MapFactory.templateMap == null) {


            for (int i = 0; i < map.length; i++) {

                map[0][i] = WorldElements.FOOD;
                map[map.length - 1][i] = WorldElements.FOOD;

                map[i][0] = WorldElements.FOOD;
                map[i][map.length - 1] = WorldElements.FOOD;
            }

            map[0][0] = WorldElements.GRASS;
            
        } else {

            map = MapFactory.templateMap.clone();

            show();
            System.out.println("------------------------");
        }
        
        return map;
    }

    public static void setTemplateMap(WorldElements[][] map) {
        MapFactory.templateMap = map.clone();

        World.MAX_FITNESS = calculateFitness(map);
    }

    private static int calculateFitness(WorldElements[][] map) {
        int fitness = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (WorldElements.FOOD.equals(map[i][j])) {
                    fitness++;
                }
            }
        }

        return fitness;

    }

    private static WorldElements[][] clone(WorldElements[][] map) {
        WorldElements[][] destMap = new WorldElements[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].equals(WorldElements.WALL)) {
                    destMap[i][j] = WorldElements.WALL;
                } else if (map[i][j].equals(WorldElements.FOOD)) {
                    destMap[i][j] = WorldElements.FOOD;
                } else if (map[i][j].equals(WorldElements.GRASS)) {
                    destMap[i][j] = WorldElements.GRASS;
                } else if (map.equals(WorldElements.MINE)) {
                    destMap[i][j] = WorldElements.MINE;
                }
            }
        }

        return destMap;
    }

    private static void show() {

        for (int i = 0; i < templateMap.length; i++) {

            for (int j = 0; j < templateMap.length; j++) {
                if (templateMap[i][j] == null) {
                    System.out.print("+");
                } else if (templateMap[i][j].equals(WorldElements.WALL)) {
                    System.out.print("#");
                } else if (templateMap[i][j].equals(WorldElements.FOOD)) {
                    System.out.print("F");
                } else if (templateMap[i][j].equals(WorldElements.GRASS)) {
                    System.out.print("G");
                } else if (templateMap[i][j].equals(WorldElements.MINE)) {
                    System.out.print("M");
                }
            }

            System.out.println();
        }
    }    
}
