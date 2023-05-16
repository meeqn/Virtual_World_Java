package VirtualWorld.Worlds;

import VirtualWorld.Organism;
import VirtualWorld.Animals.Human;
import VirtualWorld.World;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.KeyEvent;
public class RectWorld extends World implements Serializable {
    private int tileScale;
    public RectWorld(int sizeX, int sizeY, Vector<Organism> organisms){
        super(sizeX, sizeY, organisms);
        this.polygonPoints = 4;
    }
    @Override
    public void setBoardSpace(JPanel boardSpace){
        super.setBoardSpace(boardSpace);
        this.tileScale = Math.min(boardSpace.getWidth()/this.getSizeX(), boardSpace.getHeight()/this.getSizeY());
        this.polygons = new Polygon[this.getSizeY()][this.getSizeX()];
        for(int i = 0; i<this.getSizeY(); i++){
            for(int j = 0; j<this.getSizeX(); j++){
                int[] x = {j*tileScale, j*tileScale+tileScale, j*tileScale+tileScale, j*tileScale};
                int[] y = {i*tileScale, i*tileScale, i*tileScale+tileScale, i*tileScale+tileScale};
                this.polygons[i][j] = new Polygon(x,y,polygonPoints);
            }
        }
    }
    @Override
    public ArrayList<Point> getSurroundingFields(Point field, boolean mustBeEmpty, int range) {
        ArrayList<Point> surrPoints = new ArrayList<Point>();
        int[] xParams = {0, 1, 0, -1};
        int[] yParams = {-1, 0, 1, 0};
        for (int i = 0; i < xParams.length; i++) {
            Point pos = new Point(field.x, field.y);
            pos.x += xParams[i] * range;
            pos.y += yParams[i] * range;
            if (this.board.isFieldInBoundaries(pos) && (!mustBeEmpty || this.board.getBoardField(pos) == null)) {
                surrPoints.add(pos);
            }
        }
        return surrPoints;
    }
    @Override
    public void setLogTextArea(JTextArea logTextArea){
        super.setLogTextArea(logTextArea);
    }
    @Override
    public Point generateNextPosUsingKeyboard(Human human, int range){
        Point nextPos = new Point(human.getPos().x, human.getPos().y);
        logTextArea.append("Move using ↑ ↓ → ←, press s to use power \n");
        boardSpace.repaint();
        while (true) {
            try {
                window.latchKeyPressed.await();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(window.pressedKey == KeyEvent.VK_S){
            logTextArea.append(human.useSkill());
            while (true) {
                try {
                    window.latchKeyPressed.await();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        switch(window.pressedKey){
            case KeyEvent.VK_UP:
                nextPos.y-=1;
                break;
            case KeyEvent.VK_DOWN:
                nextPos.y+=1;
                break;
            case KeyEvent.VK_LEFT:
                nextPos.x-=1;
                break;
            case KeyEvent.VK_RIGHT:
                nextPos.x+=1;
                break;
        }
        if((nextPos.x == human.getPos().x && nextPos.y == human.getPos().y) || !board.isFieldInBoundaries(nextPos))
            nextPos=null;
        return nextPos;
    }
}
