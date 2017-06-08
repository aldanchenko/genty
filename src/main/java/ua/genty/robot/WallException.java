package ua.genty.robot;

import ua.genty.robot.enums.MoveDirection;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: May 7, 2008
 * Time: 12:31:18 AM
 */
public class WallException extends Exception {
    private MoveDirection moveDirection;

    public WallException(String msg, MoveDirection direction) {
        super(msg);

        this.moveDirection = direction;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }
}
