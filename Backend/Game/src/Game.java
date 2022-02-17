
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Game {

    static int m = 4,n = 4;
    static Unit[][] field= new Unit[m][n];

    static int initialATBDCredits,atbdPlacementCost,initVirusHP,initATBDHP
            , initVirusATK , initVirusLifeSteal, initATBDATK, initATBDLifeSteal
            , atbdMoveCost;
    static double virusSpawnRate;
    static Shop shop = Shop.getInstance();
    static GeneticEvaluator g  = GeneticEvaluator.getInstance();
    static List<Unit> order = new ArrayList<>();
    static List<Unit> virusOrder = new ArrayList<>();
    static List<Unit> atbdOrder = new ArrayList<>();
    static Pair<Integer,Integer> Objective;
    static int virusLimit;


    public static void addATBD(Unit a,Pair<Integer,Integer> position){
        int x = position.fst(); int y = position.snd();
        if(x>m || y>n) {
            System.out.println("out of range");
            return;
        }
        if(Objects.equals(field[x][y],null)) {
            add(a,position);
            atbdOrder.add(a);
        }else System.out.println("can't add; This tile already has a unit");

    }
    public static void addVirus(Unit v,Pair<Integer,Integer> position){
        int x = position.fst(); int y = position.snd();
        if(x>m || y>n) {
            System.out.println("out of range");
            return;
        }
        if(Objects.equals(field[x][y],null)) {
            add(v,position);
            virusOrder.add(v);
        }else System.out.println("can't add; This tile already has a unit");

    }
    //this method should init unit
    public static void add(Unit unit, Pair<Integer,Integer> position){
        int x = position.fst(); int y = position.snd();
          unit.setPos(position);
          field[x][y] = unit;
          order.add(unit);
    }

    public static void remove(Pair<Integer,Integer> position){
        int x = position.fst(); int y = position.snd();
        order.remove(field[x][y]);
        virusOrder.remove(field[x][y]);
        atbdOrder.remove(field[x][y]);
        field[x][y] = null;
    }
    public static void destroyATBD(Unit unit, Unit spawn){
        Pair<Integer,Integer> pos = unit.getPosition();
        remove(pos);
        Unit v = new Virus(spawn);
        addVirus(v,pos);
    }
    public static void destroyVirus(Unit unit){
        Pair<Integer,Integer> pos = unit.getPosition();
        remove(pos);
    }
    public static void visualize(){
        System.out.println("-------------------------------");
        for(int i =0; i<m;i++){
            for (int j= 0;j<n;j++){
                if(Objects.equals(field[i][j],null)) {
                    System.out.print("|  e  |");
                }else
                    System.out.print("|"+field[i][j].getGene()+"|");
            }
            System.out.print("\n");
        }
        System.out.println("list order"+order.toString());
        System.out.println("virus order" +virusOrder.toString());
        System.out.println("atbd order"+atbdOrder.toString());
        System.out.println("-------------------------------");
    }

    public void spawnVirus(){}

    public Unit getVirusFromPos(Pair<Integer,Integer> pos){
        for(int i = 0 ; i < virusOrder.size() ; ++i){
            if(pos == virusOrder.get(i).getPosition()){
                return virusOrder.get(i);
            }
        }
        return  null;
    }

    public Unit senseClosestVirus(ATBD unit){
        Pair<Integer,Integer> ans = new Pair<>(0,0);
        double minDistance = 0;
        for(int i = 0 ; i < order.size() ; ++i){
            Pair<Integer,Integer> curPosition = order.get(i).getPosition();
            double calPosition = Math.sqrt(Math.pow(unit.getPosition().fst() - curPosition.fst(), 2) + Math.pow(unit.getPosition().snd() - curPosition.snd(), 2));
            minDistance = Math.min(minDistance , calPosition);
            if(calPosition == 0) continue;
            if(minDistance > calPosition){
                ans = order.get(i).getPosition();
            }
        }
        return getVirusFromPos(ans);
    }

    public Unit getATBDFromPos(Pair<Integer,Integer> pos){
        for(int i = 0 ; i < atbdOrder.size() ; ++i){
            if(pos == atbdOrder.get(i).getPosition()){
                return atbdOrder.get(i);
            }
        }
        return  null;
    }
    
    public Unit senseClosestATBD(Unit unit){
        Pair<Integer,Integer> ans = new Pair<>(0,0);
        double minDistance = 0;
        for(int i = 0 ; i < order.size() ; ++i){
            Pair<Integer,Integer> curPosition = order.get(i).getPosition();
            double calPosition = Math.sqrt(Math.pow(unit.getPosition().fst() - curPosition.fst(), 2) + Math.pow(unit.getPosition().snd() - curPosition.snd(), 2));
            minDistance = Math.min(minDistance , calPosition);
            if(calPosition == 0) continue;
            if(minDistance > calPosition){
                ans = order.get(i).getPosition();
            }
        }
        return  getATBDFromPos(ans);
    }
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
            m = s.nextInt();
            n = s.nextInt();
            field= new Unit[m][n];
            if( m <= 0 || n <= 0){ throw new IOException(); }
            System.out.println("m : " + m);
            System.out.println("n : " + n);
            System.out.println("--------2--------");
            System.out.print("Virus spawn rate : ");
            virusSpawnRate = s.nextDouble();
            if ( virusSpawnRate <= 0 || virusSpawnRate > 1 ){ throw new IOException(); }
            System.out.println(virusSpawnRate);
            System.out.println("--------3--------");
            System.out.print("Initial antibody credits : ");
            initialATBDCredits = s.nextInt();
            if ( initialATBDCredits <= 0 ){ throw new IOException(); }
            System.out.println(initialATBDCredits);
            System.out.print("ATBD placement cost : ");
            atbdPlacementCost = s.nextInt();
            if ( atbdPlacementCost > initialATBDCredits ){ throw new IOException(); }
            System.out.println(atbdPlacementCost);
            System.out.println("--------4--------");
            System.out.print("Initial Virus Health : ");
            initVirusHP = s.nextInt();
            if( initVirusHP <= 0 ){ throw new IOException(); }
            System.out.println(initVirusHP);
            System.out.print("Initial ATBD Health : ");
            initATBDHP = s.nextInt();
            if( initATBDHP <= 0 ){ throw new IOException(); }
            System.out.println(initATBDHP);
            System.out.println("--------5--------");
            System.out.print("Virus Attack Damage : ");
            initVirusATK = s.nextInt();
            if( initVirusATK <= 0 ){ throw new IOException(); }
            System.out.println(initVirusATK);
            System.out.print("Virus Lifesteal : ");
            initVirusLifeSteal = s.nextInt();
            if( initVirusLifeSteal <= 0 ){ throw new IOException(); }
            System.out.println(initVirusLifeSteal);
            System.out.println("--------6--------");
            System.out.print("Antibody Attack Damage : ");
            initATBDATK = s.nextInt();
            if( initATBDATK <= 0 ){ throw new IOException(); }
            System.out.println(initATBDATK);
            System.out.print("Antibody Lifesteal : ");
            initATBDLifeSteal = s.nextInt();
            if( initATBDLifeSteal <= 0 ){ throw new IOException(); }
            System.out.println(initATBDLifeSteal);
            System.out.println("--------7--------");
            System.out.print("Antibody Move Cost : ");
            atbdMoveCost = s.nextInt();
            if( atbdMoveCost <= 0 || atbdMoveCost > initialATBDCredits ){ throw new IOException(); }
            System.out.println(atbdMoveCost);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        Game g = new Game();
            Unit s = new Virus(70,"yasss");
            Pair<Integer,Integer> z = new Pair<>(0,0);
            g.addVirus(s,z);

            Unit ss = new Virus();
            Pair<Integer,Integer> x = new Pair<>(1,0);
            g.addVirus(ss,x);

            Unit a = new ATBD();
            Pair<Integer,Integer> c = new Pair<>(2,2);
            g.addATBD(a,c);

            Unit b = new ATBD();
            Pair<Integer,Integer> v = new Pair<>(3,3);
            g.addATBD(b,v);
            Game.visualize();

            s.attack(a);
            ss.attack(b);
            b.destruct();
            a.destruct();
            s.destruct();
            Game.visualize();
    }

}
