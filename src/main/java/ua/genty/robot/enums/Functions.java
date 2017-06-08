package ua.genty.robot.enums;

/**
 * Author: Alexander Danchenko.
 */
public enum Functions {
    AND,
    OR,
    NOT,
    DEFAULT;

    public Functions random() {
        int index = ((int) (Math.random() * 10)) % 3;

        return values()[index];
    }
}
