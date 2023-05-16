package VirtualWorld.Animals;

import VirtualWorld.Organism;

import java.awt.*;
import java.io.Serializable;

public class Antelope extends Animal implements Serializable {
    final static int ANTELOPE_INITIATIVE = 4;
    final static int ANTELOPE_STRENGTH = 4;
    final static int ANTELOPE_MOVE_DIST = 2;
    final static Color ANTELOPE_COLOR = new Color(193, 190, 90);
    public Antelope(Point pos) {
        super(ANTELOPE_INITIATIVE, ANTELOPE_STRENGTH, pos, ANTELOPE_COLOR);
        this.setMoveDist(ANTELOPE_MOVE_DIST);
    }
    @Override
    public void collision(Animal invader){
        if(this.getClass().equals(invader.getClass())){
            this.breed(invader);
        }
        else {
            int prob = (int)(Math.random()*(101));
            if(prob>50) {
                Point escapePos = world.generateRandomNeighboringField(this, true, moveDist);
                if(escapePos!=null){
                    this.nextPos = escapePos;
                    world.moveAnimalToNextPos(this);
                    world.moveAnimalToNextPos(invader);
                    return;
                }
            }
            this.organismGetsAttacked(invader);
        }
    }

    @Override
    public String toString() {
        return "Antelope " + statsToString();
    }

    @Override
    protected Organism createChild(Point pos) {
        return new Antelope(pos);
    }
}
