package ua.genty.robot.helpers;

import ua.genty.robot.enums.WorldElements;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: May 11, 2008
 * Time: 11:47:52 PM
 */
public class UserDefinedMapFactory extends AbstractFactory {
    WorldElements[][] templateMap;


    public UserDefinedMapFactory(WorldElements[][] template) {
//        templateMap = template.clone();
        templateMap = clone(template);
    }

    public WorldElements[][] createMap(int width, int height) {
        return clone(templateMap); 
    }

    private static WorldElements[][] clone(WorldElements[][] map) {
        WorldElements[][] destMap = new WorldElements[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (WorldElements.WALL.equals(map[i][j])) {
                    destMap[i][j] = WorldElements.WALL;
                } else if (WorldElements.FOOD.equals(map[i][j])) {
                    destMap[i][j] = WorldElements.FOOD;
                } else if (WorldElements.GRASS.equals(map[i][j]) || map[i][j] == null) {
                    destMap[i][j] = WorldElements.GRASS;
                } else if (WorldElements.MINE.equals(map[i][j])) {
                    destMap[i][j] = WorldElements.MINE;
                }
            }
        }

        return destMap;
    }
}
