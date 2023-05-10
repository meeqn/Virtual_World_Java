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
                g.setColor(Color.BLUE);
                for(Organism organism : world.organisms)
                    g.fillRect(organism.getPos().x, organism.getPos().y, 59, 59);
            }
        };
        boardSpace.setBounds(0,0, this.getWidth(),this.getHeight()/2 + this.getHeight()/4 -1);
        boardSpace.setLayout(null);
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
        nextTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.nextTurn();
            }
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
        //world.setBoardSpace(boardSpace); //TODO add the same for logspace


    }
}
