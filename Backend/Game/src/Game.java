import java.util.ArrayList;
import java.util.List;

public class Game {
    static int m = 3,n = 3;
    static  Unit[][] field= new Unit[m][n];
    GeneticEvaluator g  = GeneticEvaluator.getInstance();
    Shop shop = Shop.getInstance();
    static List<Unit> order = new ArrayList<>();
    Pair<Integer,Integer> Objective;
    int virusLimit;



    public void spawn(Unit unit, Pair<Integer,Integer> position){
      int x = position.fst(); int y = position.snd();
      if(x>m || y>n) {
         System.out.println("out of range");
          return;
      }
      if(field[x][y] != null) {
          field[x][y] = unit;
          order.add(unit);
      }

      else System.out.println("This tile already has a unit");
    }

    public void remove(Pair<Integer,Integer> position){
        int x = position.fst(); int y = position.snd();
        order.remove(field[x][y]);
        field[x][y] = null;
    }
    public static void destroy(Unit unit, Unit spawn){
        Pair<Integer,Integer> pos = unit.getPosition();
        int x = pos.fst(); int y = pos.snd();
        field[x][y] = null;
        order.remove(unit);
        Unit v = new Virus(spawn);
        order.add(v);
        field[x][y] = v;

    }

    public void spawnVirus(){}


    public void senseClosestVirus(Unit unit){}
    public void senseClosestATBD(Unit unit){}
    public void senseNearby(Unit unit, String direction){}

    public void update(){

    }
    public void updateShop(){
        shop.updateStatus();
    }

    public static void main(String[] args) {
        Unit s = new Virus(70,"yas");
        Unit ss = new Virus();
        Unit a = new ATBD();
        Unit b = new ATBD();
        s.attack(a);
        ss.attack(b);
        b.destruct();
        a.destruct();
        
    }

}
