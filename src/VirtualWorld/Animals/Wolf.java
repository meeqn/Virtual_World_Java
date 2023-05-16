package VirtualWorld.Animals;

import VirtualWorld.Organism;

import java.awt.*;
import java.io.Serializable;

public class Wolf extends Animal implements Serializable {
    final static int WOLF_INITIATIVE = 5;
    final static int WOLF_STRENGTH = 9;
    final static Color WOLF_COLOR = Color.DARK_GRAY;
    public Wolf(Point pos) {
        super(WOLF_INITIATIVE, WOLF_STRENGTH, pos, WOLF_COLOR);
    }

    @Override
    protected Organism createChild(Point pos) {
        return new Wolf(pos);
    }
    @Override
    public String toString() {
        return "Wolf " + statsToString();
    }
}
