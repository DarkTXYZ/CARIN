import java.util.Map;

public class ATBD implements Unit{
    Map<String,Integer> bindings;
    Pair<Integer,Integer> position;

    int Hp =69;
    int Atk = 2;
    int cost;
    String geneticCode;
    Unit previousAttacker;
    //program


    public void move(Pair<Integer,Integer> position){

    }

    public void shoot(String direction){

    }
    public void attack(Unit a){
        a.takingDamage(this);
    }

    public void destruct(){
        Game.destroy(this,previousAttacker);
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
    public String getGene(){return geneticCode;}


    public void takingDamage(Unit attacker){
        previousAttacker = attacker;
        Hp-=attacker.getAtk();
    }
    public int getCost(){
        return cost;
    }

    public Pair<Integer,Integer> getPosition(){
        return position;
    }

}
