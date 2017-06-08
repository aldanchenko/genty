package ua.genty.robot.helpers;

import ua.genty.robot.enums.WorldElements;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: May 11, 2008
 * Time: 11:44:05 PM
 */
public abstract class AbstractFactory {

    private static AbstractFactory instance;

    static {
        instance = new DefaultMapFactory();
    }

    public abstract WorldElements[][] createMap(int width, int height);

    public static void useDefaultFactoryType() {
        instance = new DefaultMapFactory();
    }

    public static void useUserDefinedFactoryType(WorldElements[][] template) {
        instance = new UserDefinedMapFactory(template);
    }

    public static AbstractFactory getInstance() {
        return instance;
    }
}
