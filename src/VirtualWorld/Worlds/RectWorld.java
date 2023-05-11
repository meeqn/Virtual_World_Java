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
        this.polygonPoints = 4;
        this.directions.put(0,"UP");
        this.directions.put(1,"DOWN");
        this.directions.put(2,"LEFT");
        this.directions.put(3,"RIGHT");
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
    public Point generateRandomNeighboringField(Organism organism, boolean mustBeEmpty, int range) {
        int move;
        byte control = 0b0000;
        Point newPos = new Point();
        do{
            newPos.y = organism.getPos().y;
            newPos.x = organism.getPos().x;

            move = (int)(Math.random()*(directions.size())); //range from 0 to 3
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
