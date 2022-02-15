import java.util.Map;

public class ATBD implements Unit{
    Map<String,Integer> bindings;
    Pair<Integer,Integer> position;

    int Hp;
    int Atk;
    int cost;
    String geneticCode;
    //program


    public void move(Pair<Integer,Integer> position){

    }

    public void shoot(String direction){

    }

    public void destruct(){

    }

    public void setHp(int mod){
        Hp+=mod;
    }
    public void setAttack(int mod){
        Atk+=mod;
    }

    public int getHp(){
        return Hp;
    }

    public int getAtk(){
        return Atk;
    }


    public void takingDamage(Unit attacker){
        Hp-=attacker.getAtk();
    }
    public int getCost(){
        return cost;
    }
}
