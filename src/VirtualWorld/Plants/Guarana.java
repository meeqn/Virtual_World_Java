package VirtualWorld.Plants;

import VirtualWorld.Animals.Animal;
import VirtualWorld.Organism;

import java.awt.*;
import java.io.Serializable;

public class Guarana extends Plant implements Serializable {
    final static int GUARANA_STRENGTH = 0;
    final static Color GUARANA_COLOR = Color.red;
    public Guarana(Point pos){
        super(GUARANA_STRENGTH, pos, GUARANA_COLOR);
    }
    @Override
    public void collision(Animal invader){
        invader.setStrength(invader.getStrength()+3);
        world.moveOrganismToGraveyard(this, invader);
        world.moveAnimalToNextPos(invader);
    }
    @Override
    protected Organism createChild(Point pos){
        return new Guarana(pos);
    }
    @Override
    public String toString() {
        return "Guarana " + statsToString();
    }
}
