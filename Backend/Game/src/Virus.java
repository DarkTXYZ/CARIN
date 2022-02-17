import java.util.Map;

public class Virus extends UnitImpl{

    public Virus(){
        System.out.println("Virus created");
        geneticCode = "dfaaa";
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
        Atk = template.getAtk();
        geneticCode = template.getGene();
        maxHp = template.g
    }

    @Override
    public void destruct() {
        Game.destroyVirus(this);
    }


}
