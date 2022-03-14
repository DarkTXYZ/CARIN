import java.util.Map;

public interface Unit {
    void move(String direction);


    void shoot(String direction);

    void destruct();

    void takingDamage(Unit attacker);

    void execute() throws DeadException;

    void setProgram(Executable program);

    void attack(Unit a);

    int getAtk();
    int getHp();
    int getMaxHp();
    int getLifeSteal();
    int getCost();
    int getAttackRange();
    int getSkin();
    int getMoveCost();


    String getGene();
    Map<String,Integer> getBindings();



    Pair<Integer,Integer> getPosition();
    void setPos(Pair<Integer,Integer> pos);
    void setHP(int mod);
    void setAttack(int mod);
    public void configMod(int cAtk, int cLs, int cHp, int ccost, int cmoveCost, String gene);
}
