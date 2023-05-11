import VirtualWorld.*;
import VirtualWorld.Animals.*;
import VirtualWorld.Window;
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
        organisms.add(new Fox(new Point(6,0)));
        organisms.add(new Fox(new Point(6,1)));
        return organisms;
    }
    public static void main(String[] args) {
        Vector<Organism> organisms = createOrganisms();
        World world = new RectWorld(10, 10, organisms);
        Window mainWin = new Window(world, 800, 800);
    }
}