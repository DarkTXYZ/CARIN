import java.util.HashMap;

public class ATBD extends UnitImpl {




    public ATBD(int atk, int lifeSteal, int hp, String gene,int cost){
        this.Atk = atk;
        this.maxHp = hp;
        this.Hp = hp;
        this.lifeSteal = lifeSteal;
        this.cost = cost;
        this.moveCost = 2;
        geneticCode = gene;
        bindings = new HashMap<>();
    }

    public ATBD(Unit template){
        Hp = template.getHp();
        maxHp = template.getMaxHp();
        Atk = template.getAtk();
        geneticCode = template.getGene();
        lifeSteal = template.getLifeSteal();
        bindings = new HashMap<>();
    }

    @Override
    public void destruct() {
        Game.destroyATBD(this,previousAttacker);
    }
}
