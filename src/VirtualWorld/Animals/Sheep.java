package VirtualWorld.Animals;
import VirtualWorld.Organism;

import java.awt.*;
import java.io.Serializable;

public class Sheep extends Animal implements Serializable {
    final static int SHEEP_INITIATIVE = 4;
    final static int SHEEP_STRENGTH = 4;
    final static Color SHEEP_COLOR = Color.WHITE;
    public Sheep(Point pos) {
        super(SHEEP_INITIATIVE, SHEEP_STRENGTH, pos, SHEEP_COLOR);
    }

    @Override
    protected Organism createChild(Point pos) {
        return new Sheep(pos);
    }
    @Override
    public String toString() {
        return "Sheep " + statsToString();
    }
}
