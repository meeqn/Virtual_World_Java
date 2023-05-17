package VirtualWorld;


import VirtualWorld.Animals.*;
import VirtualWorld.Plants.*;
import VirtualWorld.Worlds.HexWorld;
import VirtualWorld.Worlds.RectWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import javax.swing.text.DefaultCaret;
public class Window extends JFrame implements Serializable {
    private World world;
    private JPanel boardSpace;
    private JPanel logSpace;
    private JPanel buttonSpace;
    private JTextArea logTextArea;
    private JPopupMenu popupMenu;

    private DictWindow dictionary;
    private boolean isTurnActive;
    private final int xOffset;
    private final int yOffset;
    private Point selectedPos;
    private mouseListener ml;
    public static final String title = "Virtual World Java - Mikołaj Brakowski";
    public static CountDownLatch latchKeyPressed = new CountDownLatch(1);
    public static CountDownLatch latchNextTurn = new CountDownLatch(1);
    public static int pressedKey = 0;
    private void play(){
        while (true) {
            try {
                latchNextTurn.await();
                isTurnActive = true;
                world.nextTurn();
                boardSpace.repaint();
                isTurnActive = false;
                latchNextTurn = new CountDownLatch(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private JPopupMenu createPopupMenu(){
        popupMenu = new JPopupMenu();
        JMenuItem sheepItem = new JMenuItem("Sheep");
        JMenuItem wolfItem = new JMenuItem("Wolf");
        JMenuItem antelopeItem = new JMenuItem("Antelope");
        JMenuItem turtleItem = new JMenuItem("Turtle");
        JMenuItem foxItem = new JMenuItem("Fox");

        JMenuItem grassItem = new JMenuItem("Grass");
        JMenuItem dandelionItem = new JMenuItem("Dandelion");
        JMenuItem deadlyNightshadeItem = new JMenuItem("DeadlyNightshade");
        JMenuItem sosnowskiItem = new JMenuItem("Sosnowski's Hogweed");
        JMenuItem guaranaItem = new JMenuItem("Guarana");

        popupMenu.add(sheepItem);
        popupMenu.add(wolfItem);
        popupMenu.add(antelopeItem);
        popupMenu.add(turtleItem);
        popupMenu.add(foxItem);
        popupMenu.add(grassItem);
        popupMenu.add(dandelionItem);
        popupMenu.add(deadlyNightshadeItem);
        popupMenu.add(sosnowskiItem);
        popupMenu.add(guaranaItem);
        sheepItem.addActionListener(e -> {
            world.addOrganismToWorld(new Sheep(selectedPos), true);
            boardSpace.repaint();
        });
        wolfItem.addActionListener(e -> {
            world.addOrganismToWorld(new Wolf(selectedPos), true);
            boardSpace.repaint();
        });
        turtleItem.addActionListener(e -> {
            world.addOrganismToWorld(new Turtle(selectedPos), true);
            boardSpace.repaint();
        });
        antelopeItem.addActionListener(e -> {
            world.addOrganismToWorld(new Antelope(selectedPos), true);
            boardSpace.repaint();
        });
        foxItem.addActionListener(e -> {
            world.addOrganismToWorld(new Fox(selectedPos), true);
            boardSpace.repaint();
        });
        grassItem.addActionListener(e -> {
            world.addOrganismToWorld(new Grass(selectedPos), true);
            boardSpace.repaint();
        });
        sosnowskiItem.addActionListener(e -> {
            world.addOrganismToWorld(new Sosnowski(selectedPos), true);
            boardSpace.repaint();
        });
        guaranaItem.addActionListener(e -> {
            world.addOrganismToWorld(new Guarana(selectedPos), true);
            boardSpace.repaint();
        });
        deadlyNightshadeItem.addActionListener(e -> {
            world.addOrganismToWorld(new DeadlyNightshade(selectedPos), true);
            boardSpace.repaint();
        });
        dandelionItem.addActionListener(e -> {
            world.addOrganismToWorld(new Dandelion(selectedPos), true);
            boardSpace.repaint();
        });

        return popupMenu;
    }
    private JPanel createBoardSpace(){
        JPanel boardSpace = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i<world.getSizeY(); i++) {
                    for(int j = 0; j < world.getSizeX(); j++) {
                        Point p = new Point(j,i);
                        if(world.board.isFieldEmpty(p))
                            g.setColor(Color.LIGHT_GRAY);
                        else
                            g.setColor(world.board.getBoardField(p).getColor());

                        g.drawPolygon(world.getPolygons()[i][j]);
                        g.fillPolygon(world.getPolygons()[i][j]);
                    }
                }
            }
        };
        boardSpace.setBounds(0,0, this.getWidth(),this.getHeight()/2 + this.getHeight()/4 -1);
        boardSpace.setLayout(null);
        world.setBoardSpace(boardSpace);
        return boardSpace;
    }

    private JPanel createLogSpace(){
        JPanel logSpace = new JPanel();
        logSpace.setBounds(0,boardSpace.getHeight()+10, this.getWidth()-20,this.getHeight()/8 -1);
        logSpace.setLayout(new BorderLayout());
        logTextArea = new JTextArea();
        logTextArea.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        DefaultCaret caret = (DefaultCaret)logTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        logSpace.add(scrollPane);
        world.setLogTextArea(logTextArea);
        return logSpace;
    }

    private JPanel createButtonSpace(){
        JPanel buttonSpace = new JPanel();
        buttonSpace.setBounds(0,boardSpace.getHeight()+logSpace.getHeight()+10, this.getWidth(),this.getHeight()/8);
        buttonSpace.setLayout(new FlowLayout());

        JButton nextTurnButton = new JButton("Next Turn");
        nextTurnButton.addActionListener(e -> {
            this.requestFocusInWindow();
            latchNextTurn.countDown();
        });
        buttonSpace.add(nextTurnButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            this.requestFocusInWindow();
            if(!isTurnActive) {
                try {
                    world.saveToFile();
                    logTextArea.append("World saved successfully \n");
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                logTextArea.append("Please finish the turn! \n");
            }
        });
        buttonSpace.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            this.requestFocusInWindow();
            try {
                this.world = world.loadFromFile();
                world.setWindow(this);
                world.setLogTextArea(logTextArea);
                world.setBoardSpace(boardSpace);
                logTextArea.append("World loaded! \n");
                boardSpace.repaint();

            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonSpace.add(loadButton);

        JButton switchButton = new JButton("Switch Shape");
        switchButton.addActionListener(e -> {
            if(this.world instanceof RectWorld){
                this.world = new HexWorld(this.world.getSizeX(), this.world.getSizeY(), this.world.organisms);
            }
            else if(this.world instanceof HexWorld){
                this.world = new RectWorld(this.world.getSizeX(), this.world.getSizeY(), this.world.organisms);
            }
            this.world.setWindow(this);
            this.world.setBoardSpace(boardSpace);
            this.world.setLogTextArea(logTextArea);
            this.removeMouseListener(ml);
            ml = new mouseListener(this.world);
            addMouseListener(ml);
            boardSpace.repaint();
        });
        buttonSpace.add(switchButton);

        JButton dictionaryButton = new JButton("Dictionary");
        dictionaryButton.addActionListener(e -> {
            dictionary.setVisible(true);
            this.requestFocusInWindow();
        });
        buttonSpace.add(dictionaryButton);

        return buttonSpace;
    }
    private class mouseListener extends MouseAdapter implements Serializable{
        World world;
        public mouseListener(World world){
            this.world = world;
        }
        @Override
        public void mousePressed(MouseEvent e) {
            boolean control = true;
            for(int i = 0; i<world.getSizeY(); i++) {
                if (control) {
                    for (int j = 0; j < world.getSizeX(); j++) {
                        if (world.getPolygons()[i][j].contains(new Point(e.getX()-xOffset, e.getY()-yOffset))) {
                            selectedPos = new Point(j,i);
                            if(world.board.isFieldEmpty(selectedPos)) {
                                popupMenu.show(boardSpace, e.getX()-xOffset, e.getY()-yOffset);
                            }
                            control = false;
                            break;
                        }
                    }
                }
            }
        }
    }

    private class DictWindow extends JFrame implements Serializable{
        HashMap<Color, String> orgDict;
        public DictWindow(){
            Dimension dim = new Dimension(400, 500);
            setSize(dim);
            setTitle(title);
            setResizable(false);
            setLayout(null);
            orgDict = new HashMap<>();
            orgDict.put(new Human(null).getColor(), "Human");
            orgDict.put(new Sheep(null).getColor(), "Sheep");
            orgDict.put(new Wolf(null).getColor(), "Wolf");
            orgDict.put(new Turtle(null).getColor(), "Turtle");
            orgDict.put(new Antelope(null).getColor(), "Antelope");
            orgDict.put(new Fox(null).getColor(), "Fox");
            orgDict.put(new Dandelion(null).getColor(), "Dandelion");
            orgDict.put(new DeadlyNightshade(null).getColor(), "DeadlyNightshade");
            orgDict.put(new Grass(null).getColor(), "Grass");
            orgDict.put(new Guarana(null).getColor(), "Guarana");
            orgDict.put(new Sosnowski(null).getColor(), "Sosnowski's Hogweed");
            int y = 10;
            int x = 10;
            for(Map.Entry<Color, String> entry : orgDict.entrySet()) {
                Label newLabel = new Label(entry.getValue());
                newLabel.setBounds(x + 30, y, 150, 20);
                newLabel.setBackground(entry.getKey());
                this.add(newLabel);
                y += 20;
            }
        }
    }
    public Window(World world, int sizeX, int sizeY){
        this.world = world;
        world.setWindow(this);

        Dimension dim = new Dimension(sizeX, sizeY);
        setSize(dim);
        setTitle(title);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardSpace = createBoardSpace();
        add(boardSpace);

        logSpace = createLogSpace();
        add(logSpace);

        popupMenu = createPopupMenu();
        add(popupMenu);

        buttonSpace = createButtonSpace();
        add(buttonSpace);

        dictionary = new DictWindow();

        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                latchKeyPressed.countDown();
                pressedKey = e.getKeyCode();
                latchKeyPressed = new CountDownLatch(1);
            }
        });
        ml = new mouseListener(this.world);
        addMouseListener(ml);
        setLayout(null);
        setVisible(true);
        requestFocus();
        xOffset = this.getInsets().left;
        yOffset = this.getInsets().top;
        play();
    }
}
