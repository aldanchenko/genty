package ua.genty.robot.helpers;

import ua.genty.robot.enums.WorldElements;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: May 11, 2008
 * Time: 11:48:13 PM
 */
public class DefaultMapFactory extends AbstractFactory {

    public WorldElements[][] createMap(int width, int height) {
        
        WorldElements[][] map = new WorldElements[width][height];

        for (int i = 0; i < map.length; i++) {

            map[0][i] = WorldElements.FOOD;
            map[map.length - 1][i] = WorldElements.FOOD;

            map[i][0] = WorldElements.FOOD;
            map[i][map.length - 1] = WorldElements.FOOD;
        }

        map[0][0] = WorldElements.GRASS;


        return map;
    }
}
