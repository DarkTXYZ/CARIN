import java.util.Map;

public class Virus extends UnitImpl{

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
    }
    public Virus(Unit template){
        Hp = template.getHp();
        maxHp = template.getMaxHp();
        Atk = template.getAtk();
        geneticCode = template.getGene();
        lifeSteal = template.getLifeSteal();
    }

    @Override
    public void destruct() {
        previousAttacker.setHP(previousAttacker.getKillgain());
        Game.destroyVirus(this);
    }


}
