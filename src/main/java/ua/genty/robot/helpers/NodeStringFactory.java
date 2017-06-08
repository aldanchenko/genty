package ua.genty.robot.helpers;

import ua.genty.robot.Node;

/**
 * Author: Alexander Danchenko.
 */
public class NodeStringFactory {

    private static int tabCount = 0;

    private static NodeStringFactory instance;

    private NodeStringFactory() {
    }

    public static NodeStringFactory getInstance() {
        if (instance == null) {
            instance = new NodeStringFactory();
        }

        tabCount = 0;

        return instance;
    }

    public static String generateString(Node tree) {

        StringBuffer sb = new StringBuffer();

        if (tree.getFirstValue() != null) {
            String tab = tab(tabCount);
//            sb.append(tab);
            sb.append("If (").append(tree.getFirstValue()).append(" ")
                    .append(tree.getCondition()).append(" ")
                    .append(tree.getSecondValue()).append(")").append("\n");

            tabCount++;

            if (tree.getThenClause() instanceof Node) {
                sb.append(" thene ").append(((Node) tree.getThenClause()).treeToString());

            } else {

                sb.append(" thene ").append(tree.getThenClause());
            }

            if (tree.getElseClause() instanceof Node) {
                sb.append(" else ").append(((Node) tree.getElseClause()).treeToString());

            } else {

                sb.append(" else ").append(tree.getElseClause());
            }
        }

        return sb.toString();
    }

    protected static String tab(int count) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < count; i++) {
            sb.append("     ");
        }

        return sb.toString();
    }
}
