import java.util.HashMap;

public class ATBD_ extends UnitImpl {




    public ATBD_(int atk, int lifeSteal, int hp, String gene, int cost,int attackRange){
        this.Atk = atk;
        this.maxHp = hp;
        this.Hp = hp;
        this.lifeSteal = lifeSteal;
        this.cost = cost;
        this.moveCost = 2;
        this.attackRange = attackRange;
        geneticCode = gene;
        bindings = new HashMap<>();
    }

    public ATBD_(Unit template){
        Hp = template.getHp();
        maxHp = template.getMaxHp();
        Atk = template.getAtk();
        geneticCode = template.getGene();
        lifeSteal = template.getLifeSteal();
        bindings = new HashMap<>();
    }

    @Override
    public void shoot(String direction) {
        int target = Game.senseNearby(this,direction);
        if(target == 0) return;
        if(target%10 == 2){return;}
        if(target<(attackRange+1)*10){
            Pair<Integer,Integer> targetPos = new Pair<>(-1,-1);

            int coord = (target-2)/10;
            int x = position.snd(); int y = position.fst();
            if(direction.equals("right")) targetPos = new Pair<>(y,x+coord);
            if(direction.equals("left")) targetPos = new Pair<>(y,x-coord);
            if(direction.equals("up")) targetPos = new Pair<>(y-coord,x);
            if(direction.equals("down")) targetPos = new Pair<>(y+coord,x);

            if(direction.equals("upright")) targetPos = new Pair<>(y-coord,x+coord);
            if(direction.equals("downright")) targetPos = new Pair<>(y+coord,x+coord);
            if(direction.equals("upleft")) targetPos = new Pair<>(y-coord,x-coord);
            if(direction.equals("downleft")) targetPos = new Pair<>(y+coord,x-coord);
            Game.gShoot(targetPos,this);
        }
        return;
    }

    @Override
    public void destruct() {
        Game.destroyATBD(this,previousAttacker);
    }
}
