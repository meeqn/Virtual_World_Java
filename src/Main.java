import VirtualWorld.*;
import VirtualWorld.Animals.Sheep;
import VirtualWorld.Window;
import VirtualWorld.Worlds.RectWorld;

import java.awt.*;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Sheep dolly = new Sheep(new Point(4,5));
        Vector<Organism> organisms = new Vector<Organism>();
        organisms.add(dolly);
        World world = new RectWorld(10, 10, organisms);
        Window mainWin = new Window(world, 800, 800);
    }
}