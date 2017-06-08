package ua.genty.robot;

import ua.genty.robot.enums.Functions;
import ua.genty.robot.enums.Sensors;
import ua.genty.robot.beans.Position;

import java.util.Map;

/**
 * Author: Alexander Danchenko.
 */
public class Robot {

    private Position position;

    private Node algorithmTree;

    int algorithmFitness;

    public Robot() {
    }

    public Robot(Position position, Node tree) {
        this.position = position;
        this.algorithmTree = tree;
    }

    public void setAlgorithFitness(int newFitness) {
        this.algorithmFitness = newFitness;
    }

    public int getAlgorithmFitness() {
        return this.algorithmFitness;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Node getAlgorithmTree() {
        return algorithmTree;
    }

    public void setAlgorithmTree(Node algorithmTree) {
        this.algorithmTree = algorithmTree;
    }

    public INode executeAlgorithm(Map<Sensors, Boolean> sensors) {
        return executeAlgorithm(algorithmTree, sensors);
    }

    private INode executeAlgorithm(Node tree, Map<Sensors, Boolean> sensors) {
        if (calculateCondition(tree, sensors)) {
            if (tree.getThenClause() instanceof Node) {
                Node thenNode = (Node) tree.getThenClause();

                return executeAlgorithm(thenNode, sensors);
            } else {
                return tree.getThenClause();
            }
        } else {
            if (tree.getElseClause() instanceof Node) {
                Node elseNode = (Node) tree.getElseClause();

                return executeAlgorithm(elseNode, sensors);
            } else {
                return tree.getElseClause();
            }
        }
    }

    private boolean calculateCondition(Node tree, Map<Sensors, Boolean> sensors) {
        Functions condition = tree.getCondition();

        if (Functions.AND.equals(condition)) {

            return andFunctionClause(tree, sensors);

        } else if (Functions.OR.equals(condition)) {

            return orFunctionClause(tree, sensors);

        } else if (Functions.NOT.equals(condition)) {

            return notFunctionClause(tree, sensors);

        }

        return false;
    }

    private boolean notFunctionClause(Node tree, Map<Sensors, Boolean> sensors) {
        Boolean firstValue = sensors.get(tree.getFirstValue());

        return !firstValue;
    }

    private boolean orFunctionClause(Node tree, Map<Sensors, Boolean> sensors) {
        Boolean firstValue = sensors.get(tree.getFirstValue());
        Boolean secondValue = sensors.get(tree.getSecondValue());

        return firstValue || secondValue;
    }

    private boolean andFunctionClause(Node tree, Map<Sensors, Boolean> sensors) {
        Boolean firstValue = sensors.get(tree.getFirstValue());
        Boolean secondValue = sensors.get(tree.getSecondValue());

        return firstValue && secondValue;
    }
}
