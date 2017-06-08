package ua.genty.robot.enums;

/**
 * Author: Alexander Danchenko.
 */
public enum Sensors {
    WALL_NORTH_WEST,
    WALL_NORTH,
    WALL_NORTH_EAST,
    WALL_EAST,
    WALL_SOUTH_EAST,
    WALL_SOUTH,
    WALL_SOUTH_WEST,
    WALL_WEST,    
    DEFAULT;

    public Sensors random() {
        int index = ((int) (Math.random() * 10)) % 8;

        return values()[index];
    }
}
