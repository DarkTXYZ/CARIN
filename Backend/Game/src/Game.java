
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Game {
    static int m = 3,n = 3;
    static  Unit[][] field= new Unit[m][n];
    GeneticEvaluator g  = GeneticEvaluator.getInstance();
    Shop shop = Shop.getInstance();
    static List<Unit> order = new ArrayList<>();
    Pair<Integer,Integer> Objective;
    int virusLimit;
    public Game(){}



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
    public static void visulaize(){
        System.out.println("-------------------------------");
        for(int i =0; i<m;i++){
            for (int j= 0;j<m;i++){
                System.out.print("|"+field[i][j].getGene()+"|");
            }
            System.out.print("\n");
        }
        System.out.println("-------------------------------");
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






    protected static final String inFile = "src/configfile.in";

    public static void main(String[] args) {
        try(FileReader fr = new FileReader(inFile);
            Scanner s = new Scanner(fr)){
            System.out.println("--------1--------");
            int m = s.nextInt();
            int n = s.nextInt();
            if( m <= 0 || n <= 0){ throw new IOException(); }
            System.out.println("m : " + m);
            System.out.println("n : " + n);
            System.out.println("--------2--------");
            System.out.print("Virus spawn rate : ");
            double virusSpawnRate = s.nextDouble();
            if ( virusSpawnRate <= 0 || virusSpawnRate > 1 ){ throw new IOException(); }
            System.out.println(virusSpawnRate);
            System.out.println("--------3--------");
            System.out.print("Initial antibody credits : ");
            int initialATBDCredits = s.nextInt();
            if ( initialATBDCredits <= 0 ){ throw new IOException(); }
            System.out.println(initialATBDCredits);
            System.out.print("ATBD placement cost : ");
            int atbdPlacementCost = s.nextInt();
            if ( atbdPlacementCost > initialATBDCredits ){ throw new IOException(); }
            System.out.println(atbdPlacementCost);
            System.out.println("--------4--------");
            System.out.print("Initial Virus Health : ");
            int initVirusHP = s.nextInt();
            if( initVirusHP <= 0 ){ throw new IOException(); }
            System.out.println(initVirusHP);
            System.out.print("Initial ATBD Health : ");
            int initATBDHP = s.nextInt();
            if( initATBDHP <= 0 ){ throw new IOException(); }
            System.out.println(initATBDHP);
            System.out.println("--------5--------");
            System.out.print("Virus Attack Damage : ");
            int initVirusATK = s.nextInt();
            if( initVirusATK <= 0 ){ throw new IOException(); }
            System.out.println(initVirusATK);
            System.out.print("Virus Lifesteal : ");
            int initVirusLifeSteal = s.nextInt();
            if( initVirusLifeSteal <= 0 ){ throw new IOException(); }
            System.out.println(initVirusLifeSteal);
            System.out.println("--------6--------");
            System.out.print("Antibody Attack Damage : ");
            int initATBDATK = s.nextInt();
            if( initATBDATK <= 0 ){ throw new IOException(); }
            System.out.println(initATBDATK);
            System.out.print("Antibody Lifesteal : ");
            int initATBDLifeSteal = s.nextInt();
            if( initATBDLifeSteal <= 0 ){ throw new IOException(); }
            System.out.println(initATBDLifeSteal);
            System.out.println("--------7--------");
            System.out.print("Antibody Move Cost : ");
            int atbdMoveCost = s.nextInt();
            if( atbdMoveCost <= 0 || atbdMoveCost > initialATBDCredits ){ throw new IOException(); }
            System.out.println(atbdMoveCost);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        Game g = new Game();
            Unit s = new Virus(70,"yas");
            Pair<Integer,Integer> z = new Pair<>(0,0);
            g.spawn(s,z);

            Unit ss = new Virus();
            Pair<Integer,Integer> x = new Pair<>(1,0);
            g.spawn(ss,x);

            Unit a = new ATBD();
            Pair<Integer,Integer> c = new Pair<>(2,0);
            g.spawn(a,c);

            Unit b = new ATBD();
            Pair<Integer,Integer> v = new Pair<>(3,0);
            g.spawn(b,v);
            Game.visulaize();

            s.attack(a);
            ss.attack(b);
            b.destruct();
            a.destruct();

            Game.visulaize();
    }

}
