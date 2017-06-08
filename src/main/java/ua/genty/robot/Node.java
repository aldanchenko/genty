package ua.genty.robot;

import ua.genty.robot.helpers.Random;
import ua.genty.robot.helpers.NodeStringFactory;
import ua.genty.robot.enums.Functions;
import ua.genty.robot.enums.Sensors;
import ua.genty.robot.enums.MoveDirection;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 */
public class Node implements INode, Cloneable {

    public static final int DEFAULT_LEVEL_COUNT = 5;
    
    private Sensors firstValue;
    private Sensors secondValue;

    private Functions condition;

    private INode thenClause;
    private INode elseClause;

    private int fitness;

    public Sensors getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(Sensors firstValue) {
        this.firstValue = firstValue;
    }

    public Sensors getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Sensors secondValue) {
        this.secondValue = secondValue;
    }

    public Functions getCondition() {
        return condition;
    }

    public void setCondition(Functions condition) {
        this.condition = condition;
    }

    public INode getThenClause() {
        return thenClause;
    }

    public void setThenClause(INode thenClause) {
        this.thenClause = thenClause;
    }

    public INode getElseClause() {
        return elseClause;
    }

    public void setElseClause(INode elseClause) {
        this.elseClause = elseClause;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }        

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("if (").append(getFirstValue()).append(" ")
                .append(getCondition()).append(" ").append(getSecondValue()).append(" )");

        stringBuilder.append(" [Fitness = ").append(getFitness()).append(" ]");

        return stringBuilder.toString();
    }

    public String treeToString() {
        return NodeStringFactory.generateString(this);        
    }

    public void mutate() {
        int mutateRange = Random.random(3);

        switch (mutateRange) {
            case 0: {
                setFirstValue(Sensors.DEFAULT.random());
            
                setSecondValue(Sensors.DEFAULT.random());

                setCondition(Functions.DEFAULT.random());

                break;
            }

            case 1: {
                if (getThenClause() instanceof Node) {
                    ((Node) getThenClause()).mutate();
                } else {
                    setThenClause(MoveDirection.DEFAULT.random());
                }
                break;
            }

            case 2: {
                if (getElseClause() instanceof Node) {
                    ((Node) getElseClause()).mutate();
                } else {
                    setElseClause(MoveDirection.DEFAULT.random());
                }
                break;
            }
        }
    }

    public void mutateOne() {
        int mutateRange = Random.random(3);

        switch (mutateRange) {
            case 0: {
                int conditionRange = Random.random(3);

                switch (conditionRange) {
                    case 0: {
                        setFirstValue(Sensors.DEFAULT.random());
                        break;
                    }

                    case 1: {
                        setSecondValue(Sensors.DEFAULT.random());
                        break;
                    }

                    case 2: {
                        setCondition(Functions.DEFAULT.random());
                        break;
                    }
                }

                break;
            }

            case 1: {
                if (getThenClause() instanceof Node) {
                    ((Node) getThenClause()).mutate();
                } else {
                    setThenClause(MoveDirection.DEFAULT.random());
                }
                break;
            }

            case 2: {
                if (getElseClause() instanceof Node) {
                    ((Node) getElseClause()).mutate();
                } else {
                    setElseClause(MoveDirection.DEFAULT.random());
                }
                break;
            }
        }
    }

    public void crossover(Node node) {
        crossover(this, node);
    }

    public void crossover(Node node1, Node node2) {
        switch (Random.random(3)){

            case 0: {
                Sensors value1 = node2.getFirstValue();
                Sensors value2 = node2.getSecondValue();

                Functions function = node2.getCondition();

                INode thenClause = node2.getThenClause();
                INode elseClause = node2.getElseClause();

                node2.setFirstValue(node1.getFirstValue());
                node2.setSecondValue(node1.getSecondValue());
                node2.setCondition(node1.getCondition());

                node2.setThenClause(node1.getThenClause());
                node2.setElseClause(node1.getElseClause());

                node1.setFirstValue(value1);
                node1.setSecondValue(value2);

                node1.setCondition(function);

                node1.setThenClause(thenClause);
                node1.setElseClause(elseClause);

                break;
            }

            case 1: {
                if (node1.getThenClause() instanceof Node) {
                    if (Random.random(2) > 0) {
                        crossover((Node) node2.getThenClause(), (Node) node1.getThenClause());
                    } else {
                        INode tempThenClause = node2.getThenClause();

                        node2.setThenClause(node1.getThenClause());

                        node1.setThenClause(tempThenClause);
                    }

                } else {
                    INode tempThenClause = node2.getThenClause();

                    node2.setThenClause(node1.getThenClause());

                    node1.setThenClause(tempThenClause);
                }
                
                break;
            }

            case 2: {
                if (node1.getElseClause() instanceof Node && node2.getElseClause() instanceof Node) {
                    if (Random.random(2) > 0) {
                        crossover((Node) node2.getElseClause(), (Node) node1.getElseClause());
                    } else {
                        INode tempElseClause = node2.getElseClause();

                        node2.setElseClause(node1.getElseClause());

                        node1.setElseClause(tempElseClause);
                    }

                } else {
                    INode tempElseClause = node2.getElseClause();

                    node2.setElseClause(node1.getThenClause());

                    node1.setElseClause(tempElseClause);
                }

                break;
            }
        }
    }           

    protected Object clone() throws CloneNotSupportedException {
        super.clone();

        Node node = new Node();

        node.setFirstValue(getFirstValue());
        node.setSecondValue(getSecondValue());

        node.setCondition(getCondition());

        node.setFitness(getFitness());

        if (getThenClause() instanceof Node) {
            node.setThenClause((INode) ((Node) getThenClause()).clone());
        } else {
            node.setThenClause(getThenClause());
        }

        if (getElseClause() instanceof Node) {
            node.setElseClause((INode) ((Node) getElseClause()).clone());
        } else {
            node.setElseClause(getElseClause());
        }

        return node;
    }
}
