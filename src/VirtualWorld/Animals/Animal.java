package VirtualWorld.Animals;
import java.awt.Point;
import java.awt.Color;
import VirtualWorld.Organism;


public abstract class Animal extends Organism{
    protected Point nextPos;
    protected static final int STANDARD_MOVE_DIST = 1;
    protected int moveDist;

    protected Animal(Integer initiative, Integer strength, Point pos, Color color) {
        super(initiative, strength, pos, color);
        this.moveDist = STANDARD_MOVE_DIST;
    }

    protected void breed(Animal secondAnimal){
        Point childPos;
        childPos = world.generateRandomNeighboringField(this, true, 1);
        if(childPos==null)
            childPos = world.generateRandomNeighboringField(secondAnimal, true, 1);

        if(childPos != null){
            Organism offspring = this.createChild(childPos);
            world.addOrganismToWorld(offspring, false);
            secondAnimal.setActive(false);
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
        if(this.getClass().equals(invader.getClass())){
            this.breed(invader);
        }
        else{
            this.organismGetsAttacked(invader);
        }

    }

    protected void setMoveDist(int moveDist){
        this.moveDist = moveDist;
    }

    public Point getNextPos() {
        return nextPos;
    }

}
