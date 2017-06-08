package ua.genty.robot.helpers;

import ua.genty.robot.enums.Sensors;
import ua.genty.robot.enums.Functions;
import ua.genty.robot.enums.MoveDirection;
import ua.genty.robot.Node;

/**
 * Author: Aleksandr Danchenko.
 */
public class NodeFactory {

    private static final int DEFAULT_LEVEL_COUNT = 2;

    private static int elseLevel;
    private static int thenLevel;

    public static Node createTree() {

        thenLevel = 0;
        elseLevel = 0;
        
        Node tree = createTree(DEFAULT_LEVEL_COUNT);
        tree.setFitness(0);

        return tree;
    }

    private static Node createTree(int levelCount) {
        Node root = new Node();

        root.setFirstValue(Sensors.DEFAULT.random());
        root.setSecondValue(Sensors.DEFAULT.random());

        root.setCondition(Functions.DEFAULT.random());

        initThenClause(root, levelCount);

        initElseClause(root, levelCount);

        return root;
    }

    private static int initElseClause(Node root, int levelCount) {
        if (Random.random(2) > 1) {
            root.setElseClause(MoveDirection.DEFAULT.random());
        } else {
            if (elseLevel < levelCount) {
                elseLevel++;
                root.setElseClause(createTree(levelCount));
            } else {
                root.setElseClause(MoveDirection.DEFAULT.random());
            }
        }

        return elseLevel;
    }

    private static int initThenClause(Node root, int levelCount) {
        if (Random.random(2) > 1) {
            root.setThenClause(MoveDirection.DEFAULT.random());
        } else {
            if (thenLevel < levelCount) {
                thenLevel++;
                root.setThenClause(createTree(levelCount));
            } else {
                root.setThenClause(MoveDirection.DEFAULT.random());
            }
        }

        return thenLevel;
    }
}
