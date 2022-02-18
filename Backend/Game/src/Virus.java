import java.util.HashMap;

public class Virus extends UnitImpl {

    public Virus(){
        System.out.println("Virus created");
        geneticCode = "dfalt";
    }
    public Virus(int atk, int lifeSteal, int hp, String gene){
        this.Atk = atk;
        this.maxHp = hp;
        this.Hp = hp;
        this.lifeSteal = lifeSteal;
        geneticCode = gene;
        bindings = new HashMap<>();
        try {
            setProgram(GeneticEvaluator.getInstance().evaluate( this));
        }catch (Exception e) {System.out.println("genethingy");}
    }
    public Virus(Unit template){
        Hp = template.getHp();
        maxHp = template.getMaxHp();
        Atk = template.getAtk();
        geneticCode = template.getGene();
        lifeSteal = template.getLifeSteal();
        bindings = new HashMap<>();
        try {
            setProgram(GeneticEvaluator.getInstance().evaluate( this));
        }catch (Exception e) {System.out.println("genethingy");}

    }

    @Override
    public void destruct() {
        previousAttacker.setHP(previousAttacker.getLifeSteal());
        Game.destroyVirus(this);
    }


}
