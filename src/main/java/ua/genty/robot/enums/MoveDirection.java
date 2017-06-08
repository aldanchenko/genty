package ua.genty.robot.enums;

import ua.genty.robot.INode;

/**
 * Author: Alexander Danchenko.
 */
public enum MoveDirection implements INode {
    GO_NORTH,
    GO_SOUTH,
    GO_EAST,
    GO_WEST,
    DEFAULT;

    public MoveDirection random() {
        int index = ((int) (Math.random() * 10)) % 4;

        return values()[index];
    }
}
