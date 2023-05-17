package VirtualWorld.Worlds;

import VirtualWorld.Animals.Human;
import VirtualWorld.Organism;
import VirtualWorld.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class HexWorld extends World implements Serializable {
    private double sideLen;
    public HexWorld(int sizeX, int sizeY, Vector<Organism> organisms){
        super(sizeX, sizeY, organisms);
        this.polygonPoints = 6;
    }
    @Override
    public void setBoardSpace(JPanel boardSpace){
        super.setBoardSpace(boardSpace);
        double reqWidth = 0;
        for(int i =0; i<this.getSizeX(); i++){
            if(i%2==0){
                reqWidth+=1;
            }
            else{
                reqWidth+=2;
            }
        }
        if(this.getSizeX()%2==0)
            reqWidth+=1;

        this.sideLen = Math.min((boardSpace.getWidth()-7)/reqWidth, boardSpace.getHeight()/(this.getSizeY()*Math.sqrt(3) + Math.sqrt(3)/2));
        this.polygons = new Polygon[this.getSizeY()][this.getSizeX()];
        for(int x = 0; x<this.getSizeX(); x++) {
            double centerX =(sideLen+x*(1.5*sideLen));
            for (int y = 0; y < this.getSizeY(); y++) {
                double centerY;
                if (x % 2 == 0)
                    centerY = sideLen*Math.sqrt(3)/2 + y * (sideLen * Math.sqrt(3));
                else
                    centerY = sideLen * Math.sqrt(3) + y * (sideLen * Math.sqrt(3));

                double theta = 0;
                int[] hexX = new int[6];
                int[] hexY = new int[6];
                for (int i = 0; i < 6; i++) {
                    hexX[i] = (int) (centerX + sideLen*Math.cos(theta));
                    hexY[i] = (int) (centerY + sideLen*Math.sin(theta));
                    theta += Math.PI / 3;
                }

                this.polygons[y][x] = new Polygon(hexX, hexY, polygonPoints);
            }
        }
    }
    @Override
    public ArrayList<Point> getSurroundingFields(Point field, boolean mustBeEmpty, int range) {
        ArrayList<Point> surrPoints = new ArrayList<Point>();
        int[] xParams;
        int[] yParams;
        if(field.x%2==1) {
            xParams = new int[]{0, 0, 1, -1, 1, -1};
            yParams = new int[]{-1, 1, 0, 0, 1, 1};
        }
        else{
            xParams = new int[]{0, 0, 1, -1, 1, -1};
            yParams = new int[]{-1, 1, 0, 0, -1, -1};
        }
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
    public Point generateNextPosUsingKeyboard(Human human, int range) {
        Point nextPos = new Point(human.getPos().x, human.getPos().y);
        logTextArea.append("Move using 7 8 9 1 2 3 respectively to numpad's layout, press s to use power \n");
        boardSpace.repaint();
        while (true) {
            try {
                window.latchKeyPressed.await();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (window.pressedKey == KeyEvent.VK_S) {
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
        switch (window.pressedKey) {
            case KeyEvent.VK_NUMPAD8:
                nextPos.y -= 1;
                break;
            case KeyEvent.VK_NUMPAD2:
                nextPos.y += 1;
                break;
            case KeyEvent.VK_NUMPAD7:
                if(nextPos.x%2==0)
                    nextPos.y -= 1;
                nextPos.x -= 1;
                break;
            case KeyEvent.VK_NUMPAD9:
                if(nextPos.x%2==0)
                    nextPos.y -= 1;
                nextPos.x += 1;
                break;
            case KeyEvent.VK_NUMPAD1:
                if(nextPos.x%2==1)
                    nextPos.y += 1;
                nextPos.x -= 1;
                break;
            case KeyEvent.VK_NUMPAD3:
                if(nextPos.x%2==1)
                    nextPos.y += 1;
                nextPos.x += 1;

                break;
        }
        if ((nextPos.x == human.getPos().x && nextPos.y == human.getPos().y) || !board.isFieldInBoundaries(nextPos))
            nextPos = null;
        return nextPos;
    }
} //TODO EDIT
