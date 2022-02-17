import java.util.Map;

public abstract class UnitImpl implements Unit{
    Map<String,Integer> bindings;
    Pair<Integer,Integer> position;

    int Hp;
    int maxHp;
    int Atk;
    int lifeSteal;
    int cost;
    int killgain;
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


    //move direction need impl
    @Override
    public void move(Pair<Integer, Integer> destination) {
        try {
            Game.move(this, destination);
        }catch (UnexecutableCommandException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void shoot(String direction) {

    }

    public void takingDamage(Unit attacker){
        previousAttacker = attacker;
        Hp-=attacker.getAtk();
        if(Hp<=0) {
            Hp = 0;
            this.destruct();
        }
    }



    public void setPos(Pair<Integer,Integer> pos){position = pos;}
    public void setAttack(int mod){Atk+=mod;}

    @Override
    public void setHP(int mod) {
        Hp+=mod;
        if(Hp>maxHp) Hp = maxHp;
        if(Hp<0) Hp = 0;
    }

    public int getHp(){return Hp;}
    public int getAtk(){return Atk;}
    public int getMaxHp() { return maxHp; }
    public int getLifeSteal() { return lifeSteal;}
    public int getCost(){return cost;}
    public int getKillgain() {return killgain;}

    public Pair<Integer,Integer> getPosition(){return position;}
    public String getGene(){return geneticCode;}
}
