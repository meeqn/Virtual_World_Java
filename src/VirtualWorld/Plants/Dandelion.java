package VirtualWorld.Plants;
import VirtualWorld.Organism;

import java.awt.*;
import java.io.Serializable;

public class Dandelion extends Plant implements Serializable {
    final static int DANDELION_STRENGTH = 0;
    final static Color DANDELION_COLOR = new Color(255, 239, 0);
    public Dandelion(Point pos){
        super(DANDELION_STRENGTH, pos, DANDELION_COLOR);
    }
    @Override
    public void action(){
        for(int i=0; i<3; i++){
            super.action();
        }
    }
    @Override
    protected Organism createChild(Point pos){
        return new Dandelion(pos);
    }
    @Override
    public String toString() {
        return "Dandelion " + statsToString();
    }
}
