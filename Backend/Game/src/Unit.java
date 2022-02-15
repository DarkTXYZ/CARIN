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
    int getAtk();
}
