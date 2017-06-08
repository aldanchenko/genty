package ua.genty.robot;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;

import ua.genty.robot.helpers.NodeFactory;
import ua.genty.robot.helpers.Random;
import ua.genty.robot.helpers.AbstractFactory;
import ua.genty.robot.beans.Position;
import ua.genty.robot.enums.Sensors;
import ua.genty.robot.enums.MoveDirection;
import ua.genty.robot.enums.WorldElements;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: May 6, 2008
 * Time: 1:42:56 AM
 */
public class EvolutionAlgorithm {

    private static final int POPULATION_SIZE = 250;

    private static int ITERATION_COUNT = 1000;

    private static final int MUTATE_RATE = 20;
    
    private static final int ELIT_RATE = 20;

    private JLabel robotActionLabel;

    private JTextArea algorithmText;

    private DefaultListModel algorithmsListModel;

    private Node[] population;
    
    private Node[] nextPopulation;

    private int populationIndex;

    private int fitnessIndex;

    private Robot robot;

    private World world;

    private static EvolutionAlgorithm instance;

    /**
     * Singleton realization.
     *
     * @return EvolutionAlgorithm
     */
    public static EvolutionAlgorithm getInstance() {
        if (instance == null) {
            instance = new EvolutionAlgorithm();
        }

        return instance;
    }

    public static EvolutionAlgorithm newInstance() {
        instance = new EvolutionAlgorithm();

        return instance;
    }

    private EvolutionAlgorithm() {
        initializePopulation(); // TODO: may be delete this from constructor?

        robot = new Robot(new Position(0, 0), population[populationIndex]);

        world = new World();
    }

    public void initializePopulation() {
        population = new Node[POPULATION_SIZE];
        nextPopulation = new Node[POPULATION_SIZE];

        for (int i = 0; i < population.length; i++) {
            population[i] = NodeFactory.createTree();
        }
    }

    public Position getRobotPosition() {
        return robot.getPosition();
    }

    public WorldElements[][] getWorldMap() {
        return world.getMap();
    }

    public void createDefaultWorld() {

        AbstractFactory.useDefaultFactoryType();
        
        world = new World();
    }

    public World getWorld() {
        return world;
    }

    public void doOneStep() throws WallException {
        int fitness = robot.getAlgorithmFitness();

        Position currentPosition = robot.getPosition();

        Map<Sensors, Boolean> sensors = world.getSensors(currentPosition);

        MoveDirection moveDirection = (MoveDirection) robot.executeAlgorithm(sensors);

        //TODO: delete this
        robotActionLabel.setText(moveDirection.toString());

        Position newPosition = calculatePosition(currentPosition, moveDirection);

        if (world.isWallHere(newPosition)) {    //World.MAX_FITNESS
//        if (world.isWallHere(newPosition) || fitnessIndex > World.MAX_FITNESS) {    //World.MAX_FITNESS
            //TODO: delete?
            throw new WallException("Go to wall!", moveDirection);
        } else if (world.isMineHere(newPosition)) {

            robot.setAlgorithFitness(0);
            throw new WallException("Go to mine!", moveDirection);
            
        } else if (world.isFoodHere(newPosition)) {

            world.setGrassHere(newPosition.getI(), newPosition.getJ());

            fitness++;
        }

        fitnessIndex++;

//        robot.setAlgorithFitness(fitness);

        robot.setPosition(newPosition);
    }

    private void sortByFitness() {
        Arrays.sort(population, new Comparator<Node>() {

            public int compare(Node o1, Node o2) {
                return o2.getFitness() - o1.getFitness();
            }
        });
    }

    private Position calculatePosition(Position position, MoveDirection moveDirection) {
        int i = position.getI();
        int j = position.getJ();

        if (MoveDirection.GO_NORTH.equals(moveDirection)) {
            i--;
        } else if (MoveDirection.GO_EAST.equals(moveDirection)) {
            j++;
        } else if (MoveDirection.GO_SOUTH.equals(moveDirection)) {
            i++;
        } else if (MoveDirection.GO_WEST.equals(moveDirection)) {
            j--;
        }

        return new Position(i, j);
    }

    public void walkStandalone() {
        try {
            doOneStep();
        } catch (WallException exp) {
            robotActionLabel.setText(exp.getMoveDirection() + " - Algorithm broken!");

            fitnessIndex = 0;

            world = new World();
        }


        algorithmText.setText(robot.getAlgorithmTree().treeToString());
    }

    public void stepByStep() {
        try {
            doOneStep();
        } catch (WallException exp) {
            robotActionLabel.setText(exp.getMoveDirection() + " - Algorithm broken!");

            fitnessIndex = 0;

            populationIndex++;

            robot = new Robot(new Position(0, 0), population[populationIndex]);

            world = new World();
        }

        algorithmText.setText(robot.getAlgorithmTree().treeToString());
    }

    private void calculateFitnessForPopulation() {
        for (Node aPopulation : population) {

            int fitness = 0;

            Robot robot = new Robot(new Position(0, 0), aPopulation);

            World world = new World();

            for (int j = 0; j < World.MAX_FITNESS; j++) {

                Position currentPosition = robot.getPosition();

                Map<Sensors, Boolean> sensors = world.getSensors(currentPosition);

                MoveDirection moveDirection = (MoveDirection) robot.executeAlgorithm(sensors);

                Position newPosition = calculatePosition(currentPosition, moveDirection);

                if (world.isWallHere(newPosition) || fitnessIndex > World.MAX_FITNESS) {    //World.MAX_FITNESS
                    //TODO: delete?
                    fitness = fitness == 0 ? -1 : fitness;

                    break;

                } else if (world.isFoodHere(newPosition)) {

                    world.setGrassHere(newPosition.getI(), newPosition.getJ());

                    fitness++;
                } else if (world.isMineHere(newPosition)) {
                    fitness = 0;
                    break;
                }

                robot.setPosition(newPosition);
            }

            aPopulation.setFitness(fitness);
        }
    }

    public void stepByPopulations() throws CloneNotSupportedException {
        calculateFitnessForPopulation();

        sortByFitness();

        algorithmsListModel.removeAllElements();

        for (int i = 0; i < population.length; i++) {
            algorithmsListModel.add(i, population[i]);
        }

        if (population[0].getFitness() == World.MAX_FITNESS) {
            return;
        }

        mate();
        swapPopulations();
    }

    public void calculateAlgorithm() throws CloneNotSupportedException {
//        MapPanel.instance.repaint();//todo : delete this

//        MapFactory.setTemplateMap(world.getMap());

        int iterationCounter = 0;

        for (int i = 0; i < ITERATION_COUNT; i++) {

            calculateFitnessForPopulation();

            sortByFitness();

            if (population[0].getFitness() == World.MAX_FITNESS) {
                iterationCounter = i;
                break;
            }

            mate();
            swapPopulations();
        }

        robotActionLabel.setText("iteration count: " + iterationCounter);

        algorithmsListModel.removeAllElements();
        
        for (int i = 0; i < population.length; i++) {
            algorithmsListModel.add(i, population[i]);
        }
    }

    private void swapPopulations() {
        Node[] temp = population;
        population = nextPopulation;
        nextPopulation = temp;
    }

    private void elitism(int size) throws CloneNotSupportedException {

        Node[] nodes = clone(population);

        for (int i = 0; i < size; i++) {
            nextPopulation[i] = nodes[i];
        }
    }

    private Node[] clone(Node[] population) throws CloneNotSupportedException {
        Node[] newPopulation = new Node[POPULATION_SIZE];

        for (int i = 0; i < newPopulation.length; i++) {
            newPopulation[i] = (Node) population[i].clone();
        }

        return newPopulation;
    }

    private void mate() throws CloneNotSupportedException {

        elitism(ELIT_RATE);

        Node[] nodes = clone(population);

        for (int i = ELIT_RATE; i < POPULATION_SIZE; i++) {

            if (nodes[i].getFitness() < 0) {
//                nodes[i] = NodeFactory.createTree();
                nodes[i].mutate();
            } else if (Random.random(100) < MUTATE_RATE) {
                nodes[i].mutate();
            }

            if (Random.random(100) < 70) {
                Node tree = nodes[Random.random(nodes.length)];
                nodes[i].crossover(nodes[i], tree);
            }

            nextPopulation[i] = nodes[i];
        }
    }

    public void setRobot(int index) {
        Node tree = (Node) algorithmsListModel.getElementAt(index);

        algorithmText.setText(tree.treeToString());

        robot = new Robot(new Position(0, 0), tree);

        world = new World();
    }

    public void setAlgorithmTextArea(JTextArea textArea) {
        this.algorithmText = textArea;
    }

    public void setRobotActionLabel(JLabel label) {
        this.robotActionLabel = label;
    }

    public void setAlgorithmsListModel(DefaultListModel model) {
        this.algorithmsListModel = model;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        EvolutionAlgorithm evolutionAlgorithm = EvolutionAlgorithm.getInstance();

        evolutionAlgorithm.calculateAlgorithm();
    }
}
