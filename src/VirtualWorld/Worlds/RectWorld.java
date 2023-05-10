package VirtualWorld.Worlds;

import VirtualWorld.Organism;
import VirtualWorld.World;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
public class RectWorld extends World {
    private int tileScale;
    public RectWorld(int sizeX, int sizeY, Vector<Organism> organisms){
        super(sizeX, sizeY, organisms);
        this.directions.put(0,"UP");
        this.directions.put(1,"DOWN");
        this.directions.put(2,"LEFT");
        this.directions.put(3,"RIGHT");
    }

    @Override
    public void setBoardSpace(JPanel boardSpace){
        super.setBoardSpace(boardSpace);
        this.tileScale = Math.min(boardSpace.getWidth()/this.getSizeX(), boardSpace.getHeight()/this.getSizeY());
    }
    @Override
    public Point generateRandomNeighboringField(Organism organism, boolean mustBeEmpty, int range) {
        int move;
        byte control = 0b0000;
        Point newPos = organism.getPos();
        do{
            move = (int)(Math.random()*(directions.size() + 1));
            switch(directions.get(move)){
                case "UP":
                    newPos.y-=range;
                    control = (byte) (control | 0b1000);
                    break;
                case "DOWN":
                    newPos.y+=range;
                    control = (byte) (control | 0b0100);
                    break;
                case "LEFT":
                    newPos.x-=range;
                    control = (byte) (control | 0b0010);
                    break;
                case "RIGHT":
                    newPos.x+=range;
                    control = (byte) (control | 0b0001);
                    break;
            }
            if(control==0b1111){
                return null;
            }

        }while(!this.board.isFieldInBoundaries(newPos) || (mustBeEmpty && this.board.getBoardField(newPos)!=null));
        return newPos;
    }
}
