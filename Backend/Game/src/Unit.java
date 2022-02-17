import java.util.Map;

public interface Unit {

    /** Move dat ass ~  shake dat booty~~ */
    void move(Pair<Integer,Integer> position);
    /** run shoot steal*/
    void shoot(String direction);
    /** rekt*/
    void destruct();
    /** dat hurt        ouch*/
    void takingDamage(Unit attacker);

    /** test method */
    void attack(Unit a);


    int getAtk();
    int getHp();
    int getMaxHp();
    int getLifeSteal();
    int getKillgain();
    String getGene();



    Pair<Integer,Integer> getPosition();
    void setPos(Pair<Integer,Integer> pos);
    void setHP(int mod);
}
