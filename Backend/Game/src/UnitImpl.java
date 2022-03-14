
import java.util.Map;
import java.util.Objects;

public abstract class UnitImpl implements Unit {
    Map<String,Integer> bindings;
    Pair<Integer,Integer> position;

    int Hp;
    int maxHp;
    int Atk;
    int lifeSteal;
    int cost;
    int moveCost;
    int attackRange;
    int skin;
    boolean isDead = false;

    String geneticCode;
    Unit previousAttacker;
    Executable program;
    protected UnitImpl(){System.out.println("gameUnit created");}


    @Override
    public void setProgram(Executable program) {
        this.program = program;
    }

    @Override
    public void execute() throws DeadException{
        if(isDead) throw new DeadException("isdead");
        if(Objects.equals(program,null)){
            System.out.println("program null");
        }else
        program.execute();
    }

    public void attack(Unit a){
        a.takingDamage(this);
    }

    public abstract void destruct();

    @Override
    public void move(String direction) {
        Pair<Integer,Integer> gmove = new Pair<>(position.fst(), position.snd());

        if(direction.equals("left")){gmove =new Pair<>(position.fst(),position.snd()-1);}
        else if(direction.equals( "right")){gmove =new Pair<>(position.fst(),position.snd()+1);}
        else if(direction.equals("up")){gmove =new Pair<>(position.fst()-1,position.snd());}
        else if(direction.equals("down")){gmove =new Pair<>(position.fst()+1,position.snd());}
        else if(direction.equals( "upleft")){gmove =new Pair<>(position.fst()-1,position.snd()-1);}
        else if(direction.equals("downleft")){gmove =new Pair<>(position.fst()+1,position.snd()-1);}
        else if(direction.equals( "upright")){gmove =new Pair<>(position.fst()-1,position.snd()+1);}
        else if(direction.equals( "downright")){gmove =new Pair<>(position.fst()+1,position.snd()+1);}
        gmove(gmove);
    }

    public void gmove(Pair<Integer, Integer> destination) {
        try {
            Game.getInstance().move(this, destination);
        }catch (UnexecutableCommandException e){

        }
    }


    public abstract void shoot(String direction);


    public void takingDamage(Unit attacker){
        previousAttacker = attacker;
        Hp-=attacker.getAtk();
        if(Hp<=0) {
            Hp = 0;
            this.destruct();
            isDead = true;
        }
    }



    public void setPos(Pair<Integer,Integer> pos){position = pos;}


    @Override
    public void setHP(int mod) {
        Hp+=mod;
        if(Hp>maxHp) Hp = maxHp;
        if(Hp<=0) Hp = 1;
    }

    public int getHp(){return Hp;}
    public int getAtk(){return Atk;}
    public int getMaxHp() { return maxHp; }
    public int getLifeSteal() { return lifeSteal;}
    public int getCost(){return cost;}
    public int getAttackRange() {return attackRange;}
    public int getSkin(){return skin;}
    public int getMoveCost(){return moveCost;}

    public void setAttack(int mod){Atk+=mod;}
    public Pair<Integer,Integer> getPosition(){return position;}
    public String getGene(){return geneticCode;}

    @Override
    public Map<String, Integer> getBindings() {
        return bindings;
    }

    public void configMod(int cAtk, int cLs, int cHp, int ccost, int cmoveCost,String cgene){
        maxHp+=cHp;
        Hp+=cHp;
        Atk += cAtk;
        lifeSteal +=cLs;
        cost += ccost;
        moveCost+=cmoveCost;
        geneticCode = cgene;
        try {
            setProgram(GeneticEvaluator.getInstance().evaluate( this));
            System.out.println("success fully config gene");
        }catch (Exception e) {System.out.println("can't eval in config");}
    }
    public void setDf(int datk, int dls,int dhp, int dcost,int datkrange, int dmovecost){
        Hp = dhp;
        maxHp = Hp;
        Atk = datk;
        lifeSteal = dls;
        cost = dcost;
        attackRange = datkrange;
        moveCost = dmovecost;
    }

}
