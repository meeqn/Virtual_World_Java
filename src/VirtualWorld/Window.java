package VirtualWorld;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Window extends JFrame {
    private World world;
    public static final String title = "Virtual World Java - Mikolaj Brakowski";
    public Window(World world, int sizeX, int sizeY){
        this.world = world;
        Dimension dim = new Dimension(sizeX, sizeY);

        setSize(dim);
        setTitle(title);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
        add(boardSpace);

        JPanel logSpace = new JPanel();
        logSpace.setBounds(0,boardSpace.getHeight()+10, this.getWidth()-20,this.getHeight()/8 -1);
        logSpace.setLayout(new BorderLayout());
        JTextArea logTextArea = new JTextArea("...");
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        logSpace.add(scrollPane);
        add(logSpace);

        JPanel buttonSpace = new JPanel();
        buttonSpace.setBounds(0,boardSpace.getHeight()+logSpace.getHeight()+10, this.getWidth(),this.getHeight()/8);
        buttonSpace.setLayout(new FlowLayout());
        JButton nextTurnButton = new JButton("Next Turn");
        nextTurnButton.addActionListener(e -> {
            world.nextTurn();
            boardSpace.repaint();
        });
        buttonSpace.add(nextTurnButton);
        JButton saveButton = new JButton("Save");
        buttonSpace.add(saveButton);
        JButton loadButton = new JButton("Load");
        buttonSpace.add(loadButton);
        JButton switchButton = new JButton("Switch Shape");
        buttonSpace.add(switchButton);
        add(buttonSpace);

        setLayout(null);
        setVisible(true);


    }
}
