package VirtualWorld.Plants;

import VirtualWorld.Organism;

import java.awt.*;
import java.io.Serializable;

public class Grass extends Plant implements Serializable {
    final static int GRASS_STRENGTH = 0;
    final static Color GRASS_COLOR = Color.green;
    public Grass(Point pos){
        super(GRASS_STRENGTH, pos, GRASS_COLOR);
    }
    @Override
    protected Organism createChild(Point pos) {
        return new Grass(pos);
    }
    @Override
    public String toString() {
        return "Grass " + statsToString();
    }
}
