package ua.genty.robot;

import ua.genty.robot.enums.Sensors;
import ua.genty.robot.enums.MoveDirection;
import ua.genty.robot.beans.Position;

import java.util.Map;

/**
 * Author: Alexander Danchenko.
 */
public class Scene {

    private Robot robot;

    private World world;

    public Scene(Robot robot, World world) {
        this.robot = robot;
        this.world = world;
    }

    /*public void runScene(Robot robot, World world) {

        int fitness = 0;

        for (int i = 0; i < World.MAX_FITNESS; i++) {
            Position currentPosition = robot.getPosition();

            Map<Sensors, Boolean> sensors = world.getSensors(currentPosition);

            MoveDirection moveDirection = (MoveDirection) robot.executeAlgorithm(sensors);

            Position newPosition = calculatePosition(currentPosition, moveDirection);

            if (world.isWallHere(newPosition)) {
                //TODO: delete?
            } else if (world.isFoodHere(newPosition)) {
                fitness++;
            }
        }

        robot.setAlgorithFitness(fitness);
    }*/

    public void runScene() {

        int fitness = 0;

        Position currentPosition = robot.getPosition();

        Map<Sensors, Boolean> sensors = world.getSensors(currentPosition);

        MoveDirection moveDirection = (MoveDirection) robot.executeAlgorithm(sensors);

        Position newPosition = calculatePosition(currentPosition, moveDirection);

        if (world.isWallHere(newPosition)) {
            //TODO: delete?
        } else if (world.isFoodHere(newPosition)) {
            fitness++;
        }

        robot.setAlgorithFitness(fitness);
    }

    private Position calculatePosition(Position position, MoveDirection moveDirection) {

        int i = position.getI();
        int j = position.getJ();

        if (MoveDirection.GO_NORTH.equals(moveDirection)) {
            i--;
        } else if (MoveDirection.GO_EAST.equals(moveDirection)) {
            j--;
        } else if (MoveDirection.GO_SOUTH.equals(moveDirection)) {
            i++;
        } else if (MoveDirection.GO_NORTH.equals(moveDirection)) {
            j++;
        }

        return new Position(i, j);
    }
}
