package VirtualWorld.Animals;
import VirtualWorld.Organism;
import java.awt.*;
import java.io.Serializable;

public class Fox extends Animal implements Serializable {
    final static int FOX_INITIATIVE = 7;
    final static int FOX_STRENGTH = 3;
    final static Color FOX_COLOR = new Color(245, 174, 10);
    public Fox(Point pos) {
        super(FOX_INITIATIVE, FOX_STRENGTH, pos, FOX_COLOR);
    }
    @Override
    public void action(){
        nextPos = world.generateRandomNeighboringField(this, false, moveDist);
        if(nextPos!=null){
            Organism org = world.getBoard().getBoardField(nextPos);
            if(world.getBoard().isFieldEmpty(nextPos))
                world.moveAnimalToNextPos(this);
            else if(org.getClass().equals(this.getClass()) || this.getStrength() >= org.getStrength()){
                world.getBoard().getBoardField(nextPos).collision(this);
            }
        }
        this.setActive(false);
    }
    @Override
    protected Organism createChild(Point pos) {
        return new Fox(pos);
    }
    @Override
    public String toString() {
        return "Fox " + statsToString();
    }
}
