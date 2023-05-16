package VirtualWorld;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import javax.swing.text.DefaultCaret;
public class Window extends JFrame implements Serializable {
    private World world;
    private JPanel boardSpace;
    private JPanel logSpace;
    private JPanel buttonSpace;
    private JTextArea logTextArea;
    private boolean isTurnActive;
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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
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

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonSpace.add(loadButton);

        JButton switchButton = new JButton("Switch Shape");
        buttonSpace.add(switchButton);

        return buttonSpace;
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

        buttonSpace = createButtonSpace();
        add(buttonSpace);
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                latchKeyPressed.countDown();
                pressedKey = e.getKeyCode();
                latchKeyPressed = new CountDownLatch(1);
            }
        });

        setLayout(null);
        setVisible(true);
        requestFocus();
        play();
    }
}
