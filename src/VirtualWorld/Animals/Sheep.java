package VirtualWorld.Animals;
import java.awt.*;

public class Sheep extends Animal{
    final static int SHEEP_INITIATIVE = 4;
    final static int SHEEP_STRENGTH = 4;
    final static Color SHEEP_COLOR = Color.WHITE;
    public Sheep(Point pos) {
        super(SHEEP_INITIATIVE, SHEEP_STRENGTH, pos, SHEEP_COLOR);
    }

    @Override
    protected Animal createChild(Point pos) {
        return new Sheep(pos);
    }
}
