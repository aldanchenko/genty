package ua.genty.robot.ui;

import ua.genty.robot.EvolutionAlgorithm;
import ua.genty.robot.helpers.MapFactory;
import ua.genty.robot.helpers.AbstractFactory;
import ua.genty.robot.enums.WorldElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: Apr 23, 2008
 * Time: 9:09:54 AM
 */
public class GentyFrame extends JFrame  {

    private static final String DO_STEP_ACTION = "Step";

    private static final String TITLE = "Genty.";

    private JTextArea originalAlgorithm;

    private JLabel robotActionLabel;

    JList algorithmsList;
    
    DefaultListModel algorithmsListModel;

    EvolutionAlgorithm evolutionAlgorithm = EvolutionAlgorithm.getInstance();

    private static GentyFrame instance;

    private String worldElment;

    public static void main(String[] args) {

		GentyFrame drawFrame = new GentyFrame();
		drawFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawFrame.setVisible(true);
	}

	public GentyFrame() {

        instance = this;

        setTitle(TITLE);
		setSize(450, 500);

		initWestPanel();
		initEastPanel();
        initSouthPanel();
        initCenterPanel();
        initNorthPanel();

        evolutionAlgorithm.setAlgorithmTextArea(originalAlgorithm);
        evolutionAlgorithm.setAlgorithmsListModel(algorithmsListModel);
        evolutionAlgorithm.setRobotActionLabel(robotActionLabel);
    }

    public WorldElements getWorldElement() {

        if (WorldElements.WALL.toString().equals(worldElment)) {
            return WorldElements.WALL;

        } else if (WorldElements.GRASS.toString().equals(worldElment)) {
            return WorldElements.GRASS;

        } else if (WorldElements.FOOD.toString().equals(worldElment)) {
            return WorldElements.FOOD;

        } else if (WorldElements.MINE.toString().equals(worldElment)) {
            return WorldElements.MINE;
        }

        return null;
    }

    /**
     * Init west panel.
     */
    private void initNorthPanel() {

        JPanel northPanel = new JPanel(new BorderLayout(5, 5));

        JPanel actionButtonsPanel = new JPanel();

        actionButtonsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        robotActionLabel = new JLabel("");

        actionButtonsPanel.add(new JButton(doWalkStandaloneAction));
        actionButtonsPanel.add(new JButton(calculateAlgorithAction));
        actionButtonsPanel.add(new JButton(setMapPatternAction));
        actionButtonsPanel.add(new JButton(clearAction));

        actionButtonsPanel.add(robotActionLabel, BorderLayout.SOUTH);

        JPanel radioButtonsPanel = new JPanel();

        radioButtonsPanel.setBorder(BorderFactory.createTitledBorder("World Elements"));

        ButtonGroup buttonGroup = new ButtonGroup();

        addRadioButton(WorldElements.WALL.toString(), buttonGroup, radioButtonsPanel);
        addRadioButton(WorldElements.GRASS.toString(), buttonGroup, radioButtonsPanel);
        addRadioButton(WorldElements.FOOD.toString(), buttonGroup, radioButtonsPanel);
        addRadioButton(WorldElements.MINE.toString(), buttonGroup, radioButtonsPanel);

        northPanel.add(actionButtonsPanel, BorderLayout.NORTH);
        northPanel.add(radioButtonsPanel, BorderLayout.SOUTH);

        getContentPane().add(northPanel, BorderLayout.NORTH);
    }

    /**
     * Init east panel.
     */
    private void initEastPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());

        centerPanel.setBorder(BorderFactory.createTitledBorder("Map"));

        MapPanel mapPanel = new MapPanel();

        centerPanel.add(mapPanel, BorderLayout.CENTER);

        getContentPane().add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Init south panel.
     */
    private void initSouthPanel() {
        JPanel southPanel = new JPanel(new BorderLayout(5, 5));

        originalAlgorithm = new JTextArea(10, 5);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(0.05);

        splitPane.setLeftComponent(originalAlgorithm);

        southPanel.add(splitPane, BorderLayout.CENTER);

        getContentPane().add(new JScrollPane(originalAlgorithm), BorderLayout.SOUTH);
    }

    /**
     * Init center panel.
     */
    private void initWestPanel() {
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.setBorder(BorderFactory.createTitledBorder("Algorithms"));

        algorithmsListModel = new DefaultListModel();

        algorithmsList = new JList(algorithmsListModel);

        algorithmsList.addMouseListener(new AlgorithmListMouseListener());

        eastPanel.add(new JScrollPane(algorithmsList), BorderLayout.CENTER);

        getContentPane().add(eastPanel, BorderLayout.WEST);
    }

    private void initCenterPanel() {
    }

    Action doOneStepAction = new AbstractAction(DO_STEP_ACTION) {

        public void actionPerformed(ActionEvent e) {
            evolutionAlgorithm.stepByStep();

            GentyFrame.getInstance().repaint();
        }
    };

    Action doWalkStandaloneAction = new AbstractAction("Do Step") {

        public void actionPerformed(ActionEvent e) {
            evolutionAlgorithm.walkStandalone();

            GentyFrame.getInstance().repaint();
        }
    };

    Action doStepByPopulationAction = new AbstractAction("By population") {

        public void actionPerformed(ActionEvent e) {
            try {
                evolutionAlgorithm.stepByPopulations();
            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();
            }

            GentyFrame.getInstance().repaint();
        }
    };

    Action calculateAlgorithAction = new AbstractAction("Calculate Algorithm") {

        public void actionPerformed(ActionEvent e) {
            try {

                MapFactory.setTemplateMap(EvolutionAlgorithm.getInstance().getWorldMap());

                evolutionAlgorithm = EvolutionAlgorithm.newInstance();

                evolutionAlgorithm.setAlgorithmTextArea(originalAlgorithm);
                evolutionAlgorithm.setAlgorithmsListModel(algorithmsListModel);
                evolutionAlgorithm.setRobotActionLabel(robotActionLabel);
                
                evolutionAlgorithm.calculateAlgorithm();

            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();
            }

            GentyFrame.getInstance().repaint();
        }
    };

    Action setMapPatternAction = new AbstractAction("Set Map") {

        public void actionPerformed(ActionEvent e) {
            AbstractFactory.useUserDefinedFactoryType(EvolutionAlgorithm.getInstance().getWorldMap());
        }
    };

    Action clearAction = new AbstractAction("Clear") {

        public void actionPerformed(ActionEvent e) {
                        
            EvolutionAlgorithm.getInstance().createDefaultWorld();

            EvolutionAlgorithm.getInstance().getRobotPosition().setI(0);
            EvolutionAlgorithm.getInstance().getRobotPosition().setJ(0);
            
            GentyFrame.getInstance().repaint();
        }
    };

    public static GentyFrame getInstance() {
        return instance;
    }

    private void addRadioButton(final String value, ButtonGroup buttonGroup, JPanel panel) {

        JRadioButton button = new JRadioButton(value);

        Action selectWorldElement = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                worldElment = value;
            }
        };

        button.addActionListener(selectWorldElement);

        buttonGroup.add(button);

        panel.add(button);
    }

    public class AlgorithmListMouseListener implements MouseListener, MouseMotionListener {

        public AlgorithmListMouseListener() {
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                evolutionAlgorithm.setRobot(algorithmsList.getSelectedIndex());
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }
    }
}
