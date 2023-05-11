package VirtualWorld.Plants;

import VirtualWorld.Animals.Animal;
import VirtualWorld.Organism;

import java.awt.*;

public class Sosnowski extends Plant{
    final static int SOSNOWSKI_STRENGTH = 10;
    final static Color SOSNOWSKI_COLOR = new Color(188, 228, 178);
    public Sosnowski(Point pos){
        super(SOSNOWSKI_STRENGTH, pos, SOSNOWSKI_COLOR);
    }
    @Override
    public void collision(Animal invader){
        world.moveOrganismToGraveyard(invader);
        world.moveOrganismToGraveyard(this);
    }
    @Override
    public void action(){
        //TODO for directions of different worlds
    }
    @Override
    protected Organism createChild(Point pos){
        return new Sosnowski(pos);
    }
}
