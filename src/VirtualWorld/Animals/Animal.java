package VirtualWorld.Animals;
import java.awt.Point;
import java.awt.Color;
import VirtualWorld.Organism;

import javax.management.DescriptorKey;

public abstract class Animal extends Organism{
    protected Point nextPos;
    protected static final int STANDARD_MOVE_DIST = 1;
    int moveDist;

    protected Animal(Integer initiative, Integer strength, Point pos, Color color) {
        super(initiative, strength, pos, color);
        this.moveDist = STANDARD_MOVE_DIST;
    }

    protected void breed(Animal secondAnimal){
        //TODO
    }

    @Override
    protected void action() {
        //TODO
    }

    @Override
    public void collision(Animal invader){
        //TODO
    }

    public int getMoveDist() {
        return moveDist;
    }

    public Point getNextPos() {
        return nextPos;
    }

    public void setNextPos(Point nextPos) {
        this.nextPos = nextPos;
    }

    protected void setMoveDist(int moveDist) {
        this.moveDist = moveDist;
    }
}
