import java.util.HashMap;

public class Virus extends UnitImpl {

    public Virus(){
        System.out.println("Virus created");
        geneticCode = "dfalt";
    }
    public Virus(int atk, int lifeSteal, int hp, String gene,int attackRange){
        this.Atk = atk;
        this.maxHp = hp;
        this.Hp = hp;
        this.lifeSteal = lifeSteal;
        geneticCode = gene;
        bindings = new HashMap<>();
        this.attackRange = attackRange;
        try {
            setProgram(GeneticEvaluator.getInstance().evaluate( this));
        }catch (Exception e) {System.out.println("genethingy");}
    }
    public Virus(Unit template){
        Hp = template.getHp();
        maxHp = template.getMaxHp();
        Atk = template.getAtk();
        geneticCode = template.getGene();
        lifeSteal = template.getLifeSteal();
        bindings = new HashMap<>();
        this.attackRange = template.getAttackRange();
        try {
            setProgram(GeneticEvaluator.getInstance().evaluate( this));
        }catch (Exception e) {System.out.println("genethingy");}

    }

    @Override
    public void destruct() {
        previousAttacker.setHP(previousAttacker.getLifeSteal());
        Game.destroyVirus(this);
    }

    @Override
    public void shoot(String direction) {
        int target = Game.senseNearby(this,direction);
        if(target == 0) return;
        if(target%10 == 1){return;}
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
}
