package VirtualWorld.Animals;

import VirtualWorld.Organism;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Human extends Animal implements Serializable {
    final static int HUMAN_INITIATIVE = 5;
    final static int HUMAN_STRENGTH = 5;
    final static Color HUMAN_COLOR = Color.BLACK;
    final static int SKILL_COOLDOWN = 5;
    final static int SKILL_DURATION = 5;
    private int skillTimeout;
    private int skillRemainingTurns;
    private boolean isSkillActive;
    public Human(Point pos) {
        super(HUMAN_INITIATIVE, HUMAN_STRENGTH, pos, HUMAN_COLOR);
        skillTimeout=0;
        skillRemainingTurns=0;
        isSkillActive = false;
    }
    public String useSkill(){
        if(isSkillActive){
            return "Skill is already active! \n";
        }
        else if(!isSkillActive && skillTimeout==0){
            isSkillActive=true;
            skillTimeout = SKILL_COOLDOWN;
            skillRemainingTurns = SKILL_DURATION;
            return "Skill activated! \n";
        }
        else
            return "Remaining skill cooldown! " + skillTimeout + " turns remaining! \n";

    }
    private void skillEffect(){
        ArrayList<Point> surrFields = world.getSurroundingFields(this.pos, false, 1);
        for(int i = 0; i<surrFields.size(); i++){
            if(!world.getBoard().isFieldEmpty(surrFields.get(i))){
                world.moveOrganismToGraveyard(world.getBoard().getBoardField(surrFields.get(i)), this);
            }
        }
    }

    private void skillTurnPass(){
        if(isSkillActive){
            if(skillRemainingTurns>0)
                skillRemainingTurns--;
            else if(skillRemainingTurns==0) {
                isSkillActive=false;
            }
        }
        else if(skillTimeout>0 && skillRemainingTurns==0)
            skillTimeout--;
    }

    @Override
    public void action(){
        skillTurnPass();
        if(this.skillRemainingTurns>0)
            world.getLogTextArea().append("Skill active, remaining turns: " + skillRemainingTurns + "\n");

        nextPos = world.generateNextPosUsingKeyboard(this, moveDist);
        if(nextPos!=null){
            if(world.getBoard().isFieldEmpty(nextPos))
                world.moveAnimalToNextPos(this);
            else{
                world.getBoard().getBoardField(nextPos).collision(this);
            }
        }
        this.setActive(false);
    }
    @Override
    public void collision(Animal invader){
        if(isSkillActive) {
            skillEffect();
            world.moveAnimalToNextPos(this);
        }
        else{
            this.organismGetsAttacked(invader);
        }
    }
    @Override
    protected Organism createChild(Point pos) {
        return new Human(pos);
    }
    @Override
    public String toString() {
        return "Human " + statsToString();
    }
}
