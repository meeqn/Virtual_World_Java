import VirtualWorld.*;
import VirtualWorld.Animals.*;
import VirtualWorld.Plants.Sosnowski;
import VirtualWorld.Window;
import VirtualWorld.Worlds.HexWorld;
import VirtualWorld.Worlds.RectWorld;

import java.awt.*;
import java.util.Vector;

public class Main {
    private static Vector<Organism> createOrganisms(){
        Vector<Organism> organisms = new Vector<>();
        organisms.add(new Sheep(new Point(0,0)));
        organisms.add(new Sheep(new Point(1,0)));
        organisms.add(new Wolf(new Point(2,0)));
        organisms.add(new Turtle(new Point(3,0)));
        organisms.add(new Fox(new Point(6,1)));
        organisms.add(new Fox(new Point(6,0)));
        organisms.add(new Sosnowski(new Point(7,0)));
        organisms.add(new Human(new Point(9,9)));
        return organisms;
    }
    public static void main(String[] args) {
        Vector<Organism> organisms = createOrganisms();
        World world = new RectWorld(15, 15, organisms);
        Window mainWin = new Window(world, 800, 800);
    }
}