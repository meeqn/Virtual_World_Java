package VirtualWorld.Plants;
import VirtualWorld.Organism;
import VirtualWorld.Animals.Animal;

import java.awt.*;

public abstract class Plant extends Organism {
    protected static final int STANDARD_SOWING_DIST = 1;
    static int DEFAULT_SOWING_DIST = 1;
    protected int sowingDist = DEFAULT_SOWING_DIST;
    static int PLANT_INITIATIVE = 0;

    protected Plant(Integer strength, Point pos, Color color) {
        super(PLANT_INITIATIVE, strength, pos, color);
    }

    @Override
    protected void action() {
        int prob = (int)(Math.random()*(101));
        if(prob>80){
            Point offspringPos = world.generateRandomNeighboringField(this, true, sowingDist);
            if(offspringPos!=null){
                Organism sapling = this.createChild(offspringPos);
                world.addOrganismToWorld(sapling,false);
            }
        }
        this.setActive(false);
    }

    @Override
    public void collision(Animal invader){
        this.organismGetsAttacked(invader);
    }
}
