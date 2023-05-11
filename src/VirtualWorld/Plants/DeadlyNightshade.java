package VirtualWorld.Plants;

import VirtualWorld.Organism;
import VirtualWorld.Animals.Animal;

import java.awt.*;

public class DeadlyNightshade extends Plant{
    final static int DEADLY_NIGHTSHADE_STRENGTH = 99;
    final static Color DEADLY_NIGHTSHADE_COLOR = Color.BLACK;
    public DeadlyNightshade(Point pos){
        super(DEADLY_NIGHTSHADE_STRENGTH, pos, DEADLY_NIGHTSHADE_COLOR);
    }
    @Override
    public void collision(Animal invader){
        world.moveOrganismToGraveyard(invader);
        world.moveOrganismToGraveyard(this);
    }
    @Override
    protected Organism createChild(Point pos){
        return new DeadlyNightshade(pos);
    }
}
