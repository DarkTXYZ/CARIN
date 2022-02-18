
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
    static Objective Objective;
    static int virusLimit;

    public static void initObjective(int maxElim){
        Objective = new Objective(0,maxElim);
    }
    //tell if we win
    public static void notifyReachElim(){
        System.out.println("All viruses have been eliminated");
    }

    public static void addATBD(Unit a, Pair<Integer,Integer> position){
        int x = position.fst(); int y = position.snd();
        if(x>m || y>n) {
            System.out.println("out of range");
            return;
        }
        if(Objects.equals(field[x][y],null)) {
            add(a,position);
            atbdOrder.add(a);
            shop.modCurrency(-a.getCost());
        }else System.out.println("can't add; This tile already has a gameUnit");

    }
    public static void addVirus(Unit v, Pair<Integer,Integer> position){
        int x = position.fst(); int y = position.snd();
        if(x>m || y>n) {
            System.out.println("out of range");
            return;
        }
        if(Objects.equals(field[x][y],null)) {
            add(v,position);
            virusOrder.add(v);
        }else System.out.println("can't add; This tile already has a gameUnit");

    }
    //this method should init gameUnit
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
    public static void move (Unit u, Pair<Integer,Integer> destination) throws UnexecutableCommandException{
        //field [x][y]
        int x = destination.fst(); int y = destination.snd();
        int oldx = u.getPosition().fst(); int oldy = u.getPosition().snd();
        if(x>=m||y>=n) {return;}
        if(Objects.equals(field[x][y],null)){
            Unit temp = field[oldx][oldy];
            temp.setPos(destination);
            field[x][y] = temp;
            field[oldx][oldy] = null;
        }else{
            throw new UnexecutableCommandException("gameUnit occupied in destination tile");
        }
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
        shop.modCurrency(2);
        Objective.modfst(1);
    }
    public static void visualize(){
        System.out.println("-------------------------------");
        for(int i =0; i<m;i++){
            for (int j= 0;j<n;j++){
                if(Objects.equals(field[i][j],null)) {
                    System.out.print("|  e  |");
                }else
                    System.out.print("|"+field[i][j].getClass().getName()+"|");
            }
            System.out.print("\n");
        }
        System.out.println("list order"+order.toString());
        System.out.println("virus order" +virusOrder.toString());
        System.out.println("atbd order"+atbdOrder.toString());
        System.out.println("Shop currency: "+shop.getCurrency());
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

    public Unit senseClosestVirus(ATBD gameUnit){
        Pair<Integer,Integer> ans = new Pair<>(0,0);
        double minDistance = 0;
        for(int i = 0 ; i < order.size() ; ++i){
            Pair<Integer,Integer> curPosition = order.get(i).getPosition();
            double calPosition = Math.sqrt(Math.pow(gameUnit.getPosition().fst() - curPosition.fst(), 2) + Math.pow(gameUnit.getPosition().snd() - curPosition.snd(), 2));
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

    /*
        createNewVirus(atk , hp , lifesteal , gene , pos){
            Unit temp = new Virus(as;ldkas;ldkasldaks;);
            addvirus();
            temp = null;
            1. field[][]
            2. order
            3. virusordere
        }
    */


    static Unit createNewVirus(int n){
        Unit virus = new Virus(viruses[n]);
        return virus;
    }
    static Unit createNewATBD(int n){
        Unit a = new ATBD(10,10,10,"testA",10);
        return a;
    }



    static Unit gangster = new Virus(75,50,250,"move right");
    static Unit pistolDude = new Virus(50,100,200,"move left");
    static Unit sniper = new Virus(150,20,160,"snipe");
    static Unit[] viruses = {gangster,pistolDude,sniper};


    protected static final String inFile = "src/configfile.in";

    public static void main(String[] args) {
        config(inFile);
        shop.modCurrency(initialATBDCredits);
        for(int i =0;i<3;i++){
            viruses[i].configMod(initVirusATK,initVirusLifeSteal,initVirusHP,atbdPlacementCost,atbdMoveCost);
            //atbd[i]
        }

        addVirus(createNewVirus(0),new Pair<>(2,1));
        addVirus(createNewVirus(1),new Pair<>(2,3));
        Objective = new Objective(0,2);
        visualize();
        for(Unit u:order){
            u.execute();
        }
        visualize();








//            Unit v1 = new Virus(initVirusATK, initVirusLifeSteal, initVirusHP, "kuay");
//            Pair<Integer,Integer> z = new Pair<>(0,0);
//            g.addVirus(v1,z);
//
//            Unit v2 = new Virus(initVirusATK, initVirusLifeSteal, initVirusHP, "sud");
//            Pair<Integer,Integer> x = new Pair<>(1,0);
//            g.addVirus(v2,x);
//
//            Unit a1 = new ATBD(initATBDATK, initVirusLifeSteal, initATBDHP, "eiei");
//            Pair<Integer,Integer> c = new Pair<>(2,2);
//            g.addATBD(a1,c);
//
//            Unit b = new ATBD(initATBDATK, initVirusLifeSteal, initATBDHP, "weebo");
//            Pair<Integer,Integer> v = new Pair<>(3,3);
//            g.addATBD(b,v);
//            Game.visualize();
//
//            v1.attack(a1);
//            v2.attack(b);
//            b.destruct();
//            v1.destruct();
//            Game.visualize();
//            Pair<Integer,Integer> des1 = new Pair<>(3,3);
//            Pair<Integer,Integer> des2 = new Pair<>(2,1);
//            System.out.println("before move");
//            v1.move(des1);
//            v1.move(des2);
//            System.out.println(v1.getGene());
//            visualize();
//            v2.move(des1);
//            v2.move(des2);
//        System.out.println("after move");
//            visualize();
//            field[des1.fst()][des1.snd()].destruct();
//            visualize();
    }




    public static void config(String inFile){
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
    }

}
