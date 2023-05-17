package VirtualWorld;
import java.awt.Point;
import java.awt.Color;
import java.io.Serializable;

import VirtualWorld.Animals.Animal;
public abstract class Organism implements Comparable<Organism>, Serializable {
    static final int STARTING_AGE = 0;
    protected Color color;
    protected Integer age, initiative, strength;
    protected Point pos;
    protected boolean isActive, isAlive;
    protected World world;
    protected Organism(Integer initiative, Integer strength, Point pos, Color color){
        this.initiative = initiative;
        this.strength = strength;
        this.pos = pos;
        this.isAlive = true;
        this.age = STARTING_AGE;
        this.color = color;
    }
    protected abstract void action();
    protected void setPos(Point pos){
        this.pos = pos;
    }
    protected void setActive(boolean state){
        this.isActive = state;
    }
    protected void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    protected void ageing(){
        this.age++;
    }
    protected void setWorld(World world){
        this.world = world;
    }
    public abstract void collision(Animal invader);

    public void organismGetsAttacked(Animal invader){
        if(strength < invader.getStrength()){
            world.moveOrganismToGraveyard(this, invader);
            world.moveAnimalToNextPos(invader);
            world.logTextArea.append(invader + " killed " + this + " \n");
        }
        else{
            world.moveOrganismToGraveyard(invader, this);
        }
    }
    public int getAge() {
        return age;
    }
    public boolean isActive() {
        return isActive;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public int getInitiative() {
        return initiative;
    }
    public int getStrength() {
        return strength;
    }
    public Point getPos() {
        return (Point) pos.clone();
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public Color getColor() {
        return color;
    }

    @Override
    public abstract String toString();

    protected String statsToString(){
        return "s: " + strength + " x: " + pos.x + " y: " + pos.y;
    }

    public int compareTo(Organism o){
        if(this.initiative!=o.getInitiative())
            return (-1)*this.initiative.compareTo(o.getInitiative());
        if(this.age!=o.getAge())
            return (-1)*this.initiative.compareTo(o.getAge());
        return 0;
    }
    protected abstract Organism createChild(Point pos);
}
