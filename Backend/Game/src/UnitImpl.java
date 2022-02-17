import java.util.Map;

public abstract class UnitImpl implements Unit{
    Map<String,Integer> bindings;
    Pair<Integer,Integer> position;

    int Hp;
    int Atk;
    int cost;
    String geneticCode;
    Unit previousAttacker;
    //program
     protected UnitImpl(){System.out.println("unit created");}
    protected UnitImpl(String geneticCode){
        this.geneticCode = geneticCode;
    }
    public void attack(Unit a){
        a.takingDamage(this);
    }

    public abstract void destruct();

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
    public  String getGene(){return geneticCode;}


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
    public void setPos(Pair<Integer,Integer> pos){
        position = pos;
    }
}
