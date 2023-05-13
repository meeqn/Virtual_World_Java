package VirtualWorld;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import VirtualWorld.Animals.*;

public abstract class World {
    public static class Board {
        protected int sizeX, sizeY;
        private Organism[][] fields;
        public Board(int sizeX, int sizeY){
            fields = new Organism[sizeY][sizeX];
            this.sizeY = sizeY;
            this.sizeX = sizeX;
        }
        public boolean isFieldInBoundaries(Point field){
            return field.x >= 0 && field.y >= 0 && field.x < this.sizeX && field.y < this.sizeY;
        }
        public Organism getBoardField(Point field){
            return fields[field.y][field.x];
        }

        public void setBoardField(Point point, Organism organism){
            fields[point.y][point.x] = organism;
        }

        public boolean isFieldEmpty(Point field){
            return getBoardField(field) == null;
        }
    }
    protected int polygonPoints;
    protected Polygon[][] polygons;

    public Polygon[][] getPolygons() {
        return polygons;
    }

    protected Board board;
    protected Vector<Organism> organisms = new Vector<>();

    protected JPanel boardSpace;
    protected JTextArea logTextArea;
    protected int turnNum;

    protected World(int sizeX, int sizeY, Vector<Organism> organisms){
        this.board = new Board(sizeX, sizeY);
        if(!organisms.isEmpty()) {
            Collections.sort(organisms);
            for (Organism organism : organisms) {
                if (this.board.isFieldEmpty(organism.getPos()))
                    this.addOrganismToWorld(organism, true);
            }
        }
        turnNum = 1;
    }
    private void ridOfTheDead(){
        for(int i = 0; i<organisms.size(); i++){
            if(!organisms.elementAt(i).isAlive()){
                organisms.remove(i);
                i--;
            }
        }
    }
    public int getSizeX() {
        return board.sizeX;
    }
    public int getSizeY() {
        return board.sizeY;
    }
    public void addOrganismToWorld(Organism organism, boolean isActive){
       this.organisms.add(organism);
       organism.setWorld(this);
       this.board.setBoardField(organism.getPos(), organism);
       organism.setActive(isActive);
    }

    public void setBoardSpace(JPanel boardSpace){
        this.boardSpace = boardSpace;
    }
    public void setLogTextArea(JTextArea logTextArea){
        this.logTextArea = logTextArea;
        logTextArea.setText("New world connected!\n");
    }

    public JTextArea getLogTextArea(){
        return logTextArea;
    }

    public Point generateRandomNeighboringField(Organism organism, boolean mustBeEmpty, int range) {
        ArrayList<Point> possibleMoves = getSurroundingFields(organism.getPos(), mustBeEmpty, range);
        if(possibleMoves.isEmpty())
            return null;
        else{
            int move;
            move = (int)(Math.random()*(possibleMoves.size()));
            return possibleMoves.get(move);
        }
    }
    public abstract Point generateNextPosUsingKeyboard(Organism org, int range);
    public abstract ArrayList<Point> getSurroundingFields(Point field, boolean mustBeEmpty, int range);
    public void nextTurn(){
        logTextArea.append("--Turn : " + turnNum + "--\n");
        Collections.sort(organisms);
        for(int i=0; i<organisms.size(); i++){
            if(organisms.elementAt(i).isActive() && organisms.elementAt(i).isAlive()) {
                organisms.elementAt(i).action();
                organisms.elementAt(i).ageing();
            }
        }
        for(int i=0; i<organisms.size(); i++){
            if(organisms.elementAt(i).isAlive())
                organisms.elementAt(i).setActive(true);
        }
        turnNum++;
        this.ridOfTheDead();
    }

    public Board getBoard() {
        return board;
    }

    public void moveAnimalToNextPos(Animal animal){
        board.setBoardField(animal.getPos(), null);
        animal.setPos(animal.getNextPos());
        board.setBoardField(animal.getPos(), animal);
    }

    public void moveOrganismToGraveyard(Organism organism){
        this.board.setBoardField(organism.getPos(), null);
        organism.setAlive(false);
    }
}
