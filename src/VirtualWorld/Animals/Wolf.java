package VirtualWorld.Animals;

import java.awt.*;

public class Wolf extends Animal{
    final static int WOLF_INITIATIVE = 5;
    final static int WOLF_STRENGTH = 9;
    final static Color WOLF_COLOR = Color.DARK_GRAY;
    public Wolf(Point pos) {
        super(WOLF_INITIATIVE, WOLF_STRENGTH, pos, WOLF_COLOR);
    }

    @Override
    protected Animal createChild(Point pos) {
        return new Sheep(pos);
    }
}
