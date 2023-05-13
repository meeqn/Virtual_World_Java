package VirtualWorld.Plants;

import VirtualWorld.Animals.Animal;
import VirtualWorld.Organism;

import java.awt.*;
import java.util.ArrayList;

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
       ArrayList<Point> surrFields = world.getSurroundingFields(this.pos, false, 1);
       for(int i = 0; i<surrFields.size(); i++){
           if(!world.getBoard().isFieldEmpty(surrFields.get(i))){
                world.moveOrganismToGraveyard(world.getBoard().getBoardField(surrFields.get(i)));
           }
       }
    }
    @Override
    protected Organism createChild(Point pos){
        return new Sosnowski(pos);
    }
    @Override
    public String toString() {
        return "Sosnowski's Hogweed " + statsToString();
    }
}
