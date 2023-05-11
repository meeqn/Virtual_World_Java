package VirtualWorld.Animals;

import java.awt.*;

public class Turtle extends Animal{
    final static int TURTLE_INITIATIVE = 5;
    final static int TURTLE_STRENGTH = 9;
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
    protected Animal createChild(Point pos) {
        return new Sheep(pos);
    }
}
