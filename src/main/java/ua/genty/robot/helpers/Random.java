package ua.genty.robot.helpers;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: Apr 17, 2008
 * Time: 6:05:44 PM
 */
public class Random {  

    public static int random(int range) {
        return ((int) (Math.random() * 10)) % range;
    }
}
