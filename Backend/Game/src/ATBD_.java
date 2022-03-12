import java.util.HashMap;

public class ATBD_ extends UnitImpl {


    public ATBD_(int atk, int lifeSteal, int hp, String gene, int cost,int attackRange, int skin){
        this.Atk = atk;
        this.maxHp = hp;
        this.Hp = hp;
        this.lifeSteal = lifeSteal;
        this.cost = cost;
        this.moveCost = 2;
        this.attackRange = attackRange;
        geneticCode = gene;
        this.skin = skin;
        bindings = new HashMap<>();
        try {
            setProgram(GeneticEvaluator.getInstance().evaluate( this));
        }catch (Exception e) {System.out.println("genethingy");}
    }

    public ATBD_(Unit template){
        Hp = template.getHp();
        maxHp = template.getMaxHp();
        Atk = template.getAtk();
        geneticCode = template.getGene();
        lifeSteal = template.getLifeSteal();
        this.attackRange = template.getAttackRange();
        bindings = new HashMap<>();
        skin = template.getSkin();
        cost = template.getCost();
        try {
            setProgram(GeneticEvaluator.getInstance().evaluate( this));
        }catch (Exception e) {System.out.println("genethingy");}
    }

    @Override
    public void shoot(String direction) {
        int target = Game.getInstance().senseNearby(this,direction);
        if(target == 0) return;
        if(target%10 == 2){return;} //11 /11 21 31 41
        int attckdistance = (attackRange+1)*10;
        if(target<attckdistance){
            Pair<Integer,Integer> targetPos = new Pair<>(-1,-1);

            int coord = (target-1)/10;
            int x = position.snd(); int y = position.fst();
            if(direction.equals("right")) targetPos = new Pair<>(y,x+coord);
            if(direction.equals("left")) targetPos = new Pair<>(y,x-coord);
            if(direction.equals("up")) targetPos = new Pair<>(y-coord,x);
            if(direction.equals("down")) targetPos = new Pair<>(y+coord,x);

            if(direction.equals("upright")) targetPos = new Pair<>(y-coord,x+coord);
            if(direction.equals("downright")) targetPos = new Pair<>(y+coord,x+coord);
            if(direction.equals("upleft")) targetPos = new Pair<>(y-coord,x-coord);
            if(direction.equals("downleft")) targetPos = new Pair<>(y+coord,x-coord);
            Game.getInstance().gShoot(targetPos,this);
        }
        return;
    }

    @Override
    public void destruct() {
        Game.getInstance().destroyATBD(this,previousAttacker);
    }
}
