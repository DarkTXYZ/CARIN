import java.util.Map;

public class ATBD extends UnitImpl{
    public ATBD(int atk, int lifeSteal, int hp, String gene){
        this.Atk = atk;
        this.maxHp = hp;
        this.Hp = hp;
        this.lifeSteal = lifeSteal;
        geneticCode = gene;
    }
    @Override
    public void destruct() {
        Game.destroyATBD(this,previousAttacker);
    }
}
