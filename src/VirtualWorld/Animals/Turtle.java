package VirtualWorld.Animals;

import VirtualWorld.Organism;

import java.awt.*;
import java.io.Serializable;

public class Turtle extends Animal implements Serializable {
    final static int TURTLE_INITIATIVE = 1;
    final static int TURTLE_STRENGTH = 2;
    final static Color TURTLE_COLOR = new Color(77, 135, 100);
    public Turtle(Point pos) {
        super(TURTLE_INITIATIVE, TURTLE_STRENGTH, pos, TURTLE_COLOR);
    }
    @Override
    public void action(){
        int prob = (int)(Math.random()*(101));
        if(prob>75)
            super.action();
    }
    @Override
    public void collision(Animal invader){
        if(this.getClass().equals(invader.getClass())){
            this.breed(invader);
        }
        else if(invader.getStrength()>=5){
            this.organismGetsAttacked(invader);
        }
    }
    @Override
    protected Organism createChild(Point pos) {
        return new Turtle(pos);
    }
    @Override
    public String toString() {
        return "Turtle " + statsToString();
    }
}
