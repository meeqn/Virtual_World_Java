package VirtualWorld.Animals;

import VirtualWorld.Organism;

import java.awt.*;

public class Human extends Animal{
    final static int HUMAN_INITIATIVE = 5;
    final static int HUMAN_STRENGTH = 5;
    //TODO CHECK STATS (COLOR!) AND ADD STATS FOR SUPERPOWER
    final static Color HUMAN_COLOR = new Color(77, 135, 100);
    public Human(Point pos) {
        super(HUMAN_INITIATIVE, HUMAN_STRENGTH, pos, HUMAN_COLOR);
    }
    @Override
    public void action(){
        nextPos = world.generateNextPosUsingKeyboard(this, moveDist);
    }
    @Override
    public void collision(Animal invader){
        //TODO
    }
    @Override
    protected Organism createChild(Point pos) {
        return new Human(pos);
    }
    @Override
    public String toString() {
        return "Human " + statsToString();
    }
}
