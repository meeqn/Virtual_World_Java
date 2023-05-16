package VirtualWorld.Animals;
import java.awt.Point;
import java.awt.Color;
import java.io.Serializable;

import VirtualWorld.Organism;


public abstract class Animal extends Organism implements Serializable {
    protected Point nextPos;
    protected static final int STANDARD_MOVE_DIST = 1;
    protected int moveDist;

    protected Animal(Integer initiative, Integer strength, Point pos, Color color) {
        super(initiative, strength, pos, color);
        this.moveDist = STANDARD_MOVE_DIST;
    }

    protected void breed(Animal breeder){
        Point childPos;
        childPos = world.generateRandomNeighboringField(this, true, 1);
        if(childPos==null)
            childPos = world.generateRandomNeighboringField(breeder, true, 1);

        if(childPos != null){
            Organism offspring = this.createChild(childPos);
            world.addOrganismToWorld(offspring, false);
            this.setActive(false);
            world.getLogTextArea().append(this.toString() + " and " + breeder.toString() + " had a baby: " + offspring.toString() + " \n");
        }
    }

    @Override
    protected void action() {
        nextPos = world.generateRandomNeighboringField(this, false, moveDist);
        if(nextPos!=null){
            if(world.getBoard().isFieldEmpty(nextPos))
                world.moveAnimalToNextPos(this);
            else{
                world.getBoard().getBoardField(nextPos).collision(this);
            }
        }
        this.setActive(false);
    }

    @Override
    public void collision(Animal invader){
        if(this.getClass().equals(invader.getClass()) && this.isActive()){
            this.breed(invader);
        }
        else if(!this.getClass().equals(invader.getClass())){
            this.organismGetsAttacked(invader);
        }

    }

    protected void setMoveDist(int moveDist){
        this.moveDist = moveDist;
    }
    public void setNextPos(Point nextPos){
        this.nextPos = nextPos;
    }
    public Point getNextPos() {
        return nextPos;
    }

}
