import org.json.simple.JSONObject;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Game {
    private Game(){}
    public static Game instance;
    public static Game getInstance(){
        if(Game.instance == null) Game.instance = new Game();
        return Game.instance;
    }

    protected  final String inFile = "src/configfile.in";
    protected  final String geneinFile = "src/geneticcodeInput.in";
    private double spawnCount = 0;
    private double virusSpawnRate;

    Comparator<String> comp = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            String[] arr1 = o1.split(" ");
            String[] arr2 = o2.split(" ");
            if(Integer.parseInt(arr1[0])==Integer.parseInt(arr2[0])){
                return Integer.parseInt(arr1[1])-Integer.parseInt(arr2[1]);
            }
            return Integer.parseInt(arr1[0])-Integer.parseInt(arr2[0]);
        }
    };

    private SortedSet<String> emptySlot = new TreeSet<>(comp);

    private GeneticEvaluator g  = GeneticEvaluator.getInstance();
    private List<Unit> order;
    private List<Unit> virusOrder;
    private List<Unit> atbdOrder;
    private Objective gObjective;
    private int virusLimit;
    private int limitCount;
    private Shop shop;
    protected String[] geneATBD = new String[3]; // init genetic code for each ATBD
    protected String[] geneVirus = new String[3]; // init genetic code for each Virus
    public  void initObjective(int maxElim){
        gObjective = new Objective(0,maxElim);
    }
    //tell if we win
    public  void notifyReachElim(){
        System.out.println("All viruses have been eliminated");

    }
    public  Pair<Integer,Integer> randomTile()throws GameOverException{
            //write random here
            Random r = new Random();
            int area;
            System.out.println("empty"+emptySlot.size());
            if(emptySlot.size() == 0 && !atbdOrder.isEmpty()) return new Pair<>(-1,-1);
            if(emptySlot.size() == 0 && atbdOrder.isEmpty()){
                throw new GameOverException("You lose");
            }
            if (emptySlot.size() == 1) {
                area = 1;
            } else {
                area = emptySlot.size() / 2;
            }
            int rand = r.nextInt(area);
            String s = emptySlot.toArray(new String[emptySlot.size()])[rand];
            String[] arr = s.split(" ");
            int y = Integer.parseInt(arr[0]);
            int x = Integer.parseInt(arr[1]);
            System.out.println("y = " + y+" x = "+x);
            return new Pair<>(y, x);
    }
    Queue<Pair<Unit,Pair<Integer,Integer>>>deadList = new LinkedList<>();
    public  void updateDeadlist(){
        while(!deadList.isEmpty()){
            Pair<Unit,Pair<Integer,Integer>> u = deadList.poll();
            addVirus(u.fst(),u.snd());
        }
    }

    public  void addATBD(Unit a, Pair<Integer,Integer> position){
        if(a.getCost()>shop.getCurrency()) return;
        int y = position.fst(); int x = position.snd();
        if(x>m || y>n|| y<0 || x<0) {
            System.out.println("out of range");
            return;
        }
        if(Objects.equals(field[y][x],null)) {
            add(a,position);
            atbdOrder.add(a);
            shop.setCurrency(-a.getCost());
        }else System.out.println("can't add; This tile already has a gameUnit");

    }
    public void addVirus(Unit v, Pair<Integer,Integer> position){
        int x = position.fst(); int y = position.snd();
        if(x>m || y>n || y<0 || x<0) {
            System.out.println("out of range");
            return;
        }
        if(Objects.equals(field[x][y],null)) {
            add(v,position);
            virusOrder.add(v);
        }else System.out.println("can't add; This tile already has a gameUnit");

    }
    //this method should init gameUnit
    public  void add(Unit unit, Pair<Integer,Integer> position){
        int y = position.fst(); int x = position.snd();
        unit.setPos(position);
        field[y][x] = unit;
        order.add(unit);
        String s = String.valueOf(y)+" "+String.valueOf(x);
        emptySlot.remove(s);
    }

    public  void remove(Pair<Integer,Integer> position){

        int y = position.fst(); int x = position.snd();
//        order.remove(field[y][x]);
        virusOrder.remove(field[y][x]);
        atbdOrder.remove(field[y][x]);
        field[y][x] = null;
        String s = String.valueOf(y)+" "+String.valueOf(x);
        emptySlot.add(s);

    }
    public  void moveATBD(Unit u,Pair<Integer,Integer> destination){
        if(u.getHp() == 1) return;
        u.setHP(-u.getMoveCost());
        if(!Objects.equals(u.getClass().getName(),"ATBD_")) return;
        try {
            move(u,destination);
        }catch (Exception e){
            System.out.println("can't execute move");
        }
    }
    public  void move (Unit u, Pair<Integer,Integer> destination) throws UnexecutableCommandException{
        //field [x][y]
        int y = destination.fst(); int x = destination.snd();
        if(y< 0|| x<0) throw new UnexecutableCommandException("Out of field range");
        int oldy = u.getPosition().fst(); int oldx = u.getPosition().snd();
        if(y>=m||x>=n) {return;}
        if(Objects.equals(field[y][x],null)){
            Unit temp = field[oldy][oldx];
            temp.setPos(destination);
            field[y][x] = temp;
            field[oldy][oldx] = null;
            String s = String.valueOf(oldy)+" "+String.valueOf(oldx);
            emptySlot.add(s);
            String newS = String.valueOf(y)+" "+String.valueOf(x);
            emptySlot.remove(newS);
        }else{
            throw new UnexecutableCommandException("gameUnit occupied in destination tile");
        }
    }
    public  void destroyATBD(Unit unit, Unit spawn){
        Pair<Integer,Integer> pos = unit.getPosition();
        remove(pos);
        Unit u = new Virus(spawn);
        deadList.add(new Pair<>(u,pos));
    }
    public  void destroyVirus(Unit unit){
        Pair<Integer,Integer> pos = unit.getPosition();
        remove(pos);
        shop.setCurrency(unit.getCost());
        gObjective.modfst(1);
    }
    public  void visualize(){
        System.out.println("-------------------------------");
        for(int i =0; i<m;i++){
            for (int j= 0;j<n;j++){
                if(Objects.equals(field[i][j],null)) {
                    System.out.print("| "+i+","+j+" |");
                }else
                    System.out.print("|"+field[i][j].getClass().getName()+"|");
            }
            System.out.print(" ");
        }
        System.out.println("Empty tile: "+emptySlot);
        System.out.println("list order"+order.toString());
        System.out.println("virus order" +virusOrder.toString());
        System.out.println("atbd order"+atbdOrder.toString());
        System.out.println("Shop currency: "+shop.getCurrency());
        System.out.println("Objective :" + gObjective.fst()+"/"+ gObjective.snd());
        System.out.println("-------------------------------");
    }
//    public Unit getVirusFromPos(Pair<Integer,Integer> pos){
//        for(int i = 0 ; i < virusOrder.size() ; ++i){
//            if(pos == virusOrder.get(i).getPosition()){
//                return virusOrder.get(i);
//            }
//        }
//        return  null;
//    }
//
//    public Unit senseClosestVirus(ATBD unit){
//        Pair<Integer,Integer> ans = new Pair<>(0,0);
//        double minDistance = 0;
//        for(int i = 0 ; i < order.size() ; ++i){
//            Pair<Integer,Integer> curPosition = order.get(i).getPosition();
//            double calPosition = Math.sqrt(Math.pow(unit.getPosition().fst() - curPosition.fst(), 2) + Math.pow(unit.getPosition().snd() - curPosition.snd(), 2));
//            minDistance = Math.min(minDistance , calPosition);
//            if(calPosition == 0) continue;
//            if(minDistance > calPosition){
//                ans = order.get(i).getPosition();
//            }
//        }
//        return getVirusFromPos(ans);
//    }
//

//    public Unit getATBDFromPos(Pair<Integer,Integer> pos){
//        for(int i = 0 ; i < atbdOrder.size() ; ++i){
//            if(pos == atbdOrder.get(i).getPosition()){
//                return atbdOrder.get(i);
//            }
//        }
//        return  null;
//    }
//
//    public Unit senseClosestATBD(Unit unit){
//        Pair<Integer,Integer> ans = new Pair<>(0,0);
//        double minDistance = 0;
//        for(int i = 0 ; i < order.size() ; ++i){
//            Pair<Integer,Integer> curPosition = order.get(i).getPosition();
//            double calPosition = Math.sqrt(Math.pow(unit.getPosition().fst() - curPosition.fst(), 2) + Math.pow(unit.getPosition().snd() - curPosition.snd(), 2));
//            minDistance = Math.min(minDistance , calPosition);
//            if(calPosition == 0) continue;
//            if(minDistance > calPosition){
//                ans = order.get(i).getPosition();
//            }
//        }
//        return  getATBDFromPos(ans);
//    }
    public int senseClosestEnemy(Unit u, int type){
        List<Unit> toItr = null;
        if(type == 2){
            toItr = atbdOrder;
        }else if(type == 1){
            toItr = virusOrder;
        }else return -69;
        int hy = u.getPosition().fst(); int hx = u.getPosition().snd();
        int tempans = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        for(int i = 0 ; i < toItr.size() ; ++i) {
            int y = toItr.get(i).getPosition().fst();
            int x = toItr.get(i).getPosition().snd();
            int xdiff = x - hx;
            int ydiff = y - hy;
            if (Math.abs(xdiff) == Math.abs(ydiff)) { //diagonal
                if (xdiff < 0 && ydiff < 0) {//upleft
                    tempans = xdiff * -10 + 8;
                }
                if (xdiff > 0 && ydiff > 0) {// downright
                    tempans = xdiff * 10 + 4;
                }
                if (xdiff > 0 && ydiff < 0) { //upright
                    tempans = xdiff * 10 + 2;
                }
                if (xdiff < 0 && ydiff > 0) { // downleft
                    tempans = ydiff * 10 + 6;
                }
            } else { //line
                if (ydiff == 0 && xdiff > 0) { // right
                    tempans = xdiff * 10 + 3;
                }
                if (ydiff == 0 && xdiff < 0) {// left
                    tempans = xdiff * -10 + 7;
                }
                if (xdiff == 0 && ydiff < 0) {// up
                    tempans = ydiff * -10 + 1;
                }
                if (xdiff == 0 && ydiff > 0) {// down
                    tempans = ydiff * 10 + 5;
                }
            }
            ans = Math.min(ans, tempans);
        }
        if(ans == Integer.MAX_VALUE) ans = 0;
        return ans;
    }

    public  int senseClosestVirus(Unit u) {
        return senseClosestEnemy(u,1);
//        int hy = u.getPosition().fst(); int hx = u.getPosition().snd();
//        int tempans = Integer.MAX_VALUE;
//        int ans = Integer.MAX_VALUE;
//        for(int i = 0 ; i < virusOrder.size() ; ++i) {
//            int y = virusOrder.get(i).getPosition().fst();
//            int x = virusOrder.get(i).getPosition().snd();
//            int xdiff = x-hx;
//            int ydiff = y-hy;
//            if(Math.abs(xdiff) == Math.abs(ydiff)){ //diagonal
//                if(xdiff < 0 && ydiff<0 ){//upleft
//                    tempans =  xdiff*-10+8;
//                }
//                if(xdiff>0 && ydiff > 0) {// downright
//                    tempans =  xdiff*10+4;
//                }
//                if(xdiff>0 && ydiff <0 ){ //upright
//                    tempans = xdiff*10+2;
//                }
//                if(xdiff<0 && ydiff>0 ){ // downleft
//                    tempans = ydiff*10+6;
//                }
//            }else { //line
//                if(ydiff == 0 && xdiff>0){ // right
//                    tempans = xdiff*10+3;
//                }
//                if(ydiff == 0 && xdiff<0){// left
//                    tempans = xdiff*-10+7;
//                }
//                if(xdiff == 0 &&ydiff<0){// up
//                    tempans = ydiff*-10+1;
//                }
//                if(xdiff == 0 && ydiff>0){// down
//                    tempans = ydiff*10+5;
//                }
//            }
//            ans = Math.min(ans,tempans);


//
//            Pair<Integer,Integer> xydiff = new Pair<>(pos.fst()-virusPos.fst(), pos.snd()-virusPos.snd());
//            if( Math.abs(xydiff.fst()) == Math.abs(xydiff.snd())){ // diagonal
//                if(Objects.equals(xydiff.fst(), xydiff.snd())){
//                    if(xydiff.fst() < 0 && xydiff.snd() < 0){ // up left
//
//                    }
//                    if(xydiff.fst() > 0 && xydiff.snd() > 0){ // down right
//
//                    }
//                }
//
//                if( xydiff.fst() > xydiff.snd()){ // up right
//
//                }
//                if( xydiff.fst() < xydiff.snd()){ // down left
//
//                }
//            }else{ // straight
//                if ( xydiff.fst() > xydiff.snd() && xydiff.snd() == 0){ // right
//
//                }
//                if ( xydiff.fst() < xydiff.snd() && xydiff.snd() == 0){ // left
//
//                }
//                if ( xydiff.fst() > xydiff.snd() && xydiff.fst() == 0){ // up
//
//                }
//                if ( xydiff.fst() < xydiff.snd() && xydiff.fst() == 0){ // down
//
//                }
//            }
//        }
//    }
//        if(ans == Integer.MAX_VALUE) ans =0;
//        return ans;
    }
    public  int senseClosestATBD(Unit u){
        return senseClosestEnemy(u,2);
//        int hy = u.getPosition().fst(); int hx = u.getPosition().snd();
//        int tempans = Integer.MAX_VALUE;
//        int ans = Integer.MAX_VALUE;
//        for(int i = 0 ; i < atbdOrder.size() ; ++i) {
//            int y = atbdOrder.get(i).getPosition().fst();
//            int x = atbdOrder.get(i).getPosition().snd();
//            int xdiff = x - hx;
//            int ydiff = y - hy;
//            if (Math.abs(xdiff) == Math.abs(ydiff)) { //diagonal
//                if (xdiff < 0 && ydiff < 0) {//upleft
//                    tempans = xdiff * -10 + 8;
//                }
//                if (xdiff > 0 && ydiff > 0) {// downright
//                    tempans = xdiff * 10 + 4;
//                }
//                if (xdiff > 0 && ydiff < 0) { //upright
//                    tempans = xdiff * 10 + 2;
//                }
//                if (xdiff < 0 && ydiff > 0) { // downleft
//                    tempans = ydiff * 10 + 6;
//                }
//            } else { //line
//                if (ydiff == 0 && xdiff > 0) { // right
//                    tempans = xdiff * 10 + 3;
//                }
//                if (ydiff == 0 && xdiff < 0) {// left
//                    tempans = xdiff * -10 + 7;
//                }
//                if (xdiff == 0 && ydiff < 0) {// up
//                    tempans = ydiff * -10 + 1;
//                }
//                if (xdiff == 0 && ydiff > 0) {// down
//                    tempans = ydiff * 10 + 5;
//                }
//            }
//            ans = Math.min(ans, tempans);
//        }
//        if(ans == Integer.MAX_VALUE) ans = 0;
//        return ans;
    }
    public  int senseNearby(Unit u, String direction){
        int ans =  0;
        int hy = u.getPosition().fst(); int hx = u.getPosition().snd();
        int x = hx; int y = hy;
        if(direction.equals("right")){
            x++;
            while (x<n){
                if(!Objects.equals((field[y][x]),null)){
                    if(field[y][x].getClass().getName().equals("Virus")) return (x-hx)*10+1;
                    else return (x-hx)*10+2;
                }else{
                    x++;
                }
            }
            return 0;
        }
        else if(direction.equals("left")){
            x--;
            while (x>=0){
                if(!Objects.equals((field[y][x]),null)){
                    if(field[y][x].getClass().getName().equals("Virus")) return (hx-x)*10+1;
                    else return (hx-x)*10+2;
                }else{
                    x--;
                }
            }
            return 0;
        }
        else if(direction.equals("up")){ // << mark
            y--;
            while (y>=0){
                if(!Objects.equals((field[y][x]),null)){
                    if(field[y][x].getClass().getName().equals("Virus")) return (hy-y)*10+1;
                    else return (hy-y)*10+2;
                }else{
                    y--;
                }
            }
            return 0;
        }
        else if(direction.equals("down")){
            y++;
            while (y<m){
                if(!Objects.equals((field[y][x]),null)){
                    if(field[y][x].getClass().getName().equals("Virus")) return (y-hy)*10+1;
                    else return (y-hy)*10+2;
                }else{
                    y++;
                }
            }
            return 0;
        }
        else if(direction.equals("upright")){
            y--; x++;
            while (y>=0 && x<n){
                if(!Objects.equals((field[y][x]),null)){
                    if(field[y][x].getClass().getName().equals("Virus")) return (hy-y)*10+1;
                    else return (hy-y)*10+2;
                }else{
                    y--; x++;
                }
            }
            return 0;
        }
        else if(direction.equals("downright")){
            y++; x++;
            while (y<m && x<n){
                if(!Objects.equals((field[y][x]),null)){
                    if(field[y][x].getClass().getName().equals("Virus")) return (y-hy)*10+1;
                    else return (y-hy)*10+2;
                }else{
                    y++; x++;
                }
            }
            return 0;
        }
        else if(direction.equals("downleft")){
            y++; x--;
            while (y<m && x>=0){
                if(!Objects.equals((field[y][x]),null)){
                    if(field[y][x].getClass().getName().equals("Virus")) return (y-hy)*10+1;
                    else return (y-hy)*10+2;
                }else{
                    y++; x--;
                }
            }
            return 0;
        }
        else if(direction.equals("upleft")){
            y--; x--;
            while (y>=0 && x>=0){
                if(!Objects.equals((field[y][x]),null)){
                    if(field[y][x].getClass().getName().equals("Virus")) return (hy-y)*10+1;
                    else return (hy-y)*10+2;
                }else{
                    y--; x--;
                }
            }
            return 0;
        }
        return -99;
    }
    public  void gShoot(Pair<Integer,Integer> pos, Unit u){
        int y = pos.fst(); int x = pos.snd();
        if(y<0 || x < 0 || y>=m || x>=n) System.out.println("can't shoot out of range"); // should not happen
        field[y][x].takingDamage(u);
    }

    public  void updateShop(){
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

    Unit createNewVirus(int n){
        Unit virus = new Virus(viruses[n]);
        return virus;
    }
     Unit createNewATBD(int n){
        Unit a = new ATBD_(Atbds[n]);
        return a;
    }

    /*
    gangster HP = 100
    gangster attack = 40
    gangster ls = 15
    gangster atkRange = 1

    pistolDude HP = 100
    pistolDude attack = 50
    pistolDude ls = 30
    pistolDude atkRange = 2

    sniper HP = 60
    sniper attack = 80
    sniper ls = 5
    sniper atkRange = 5

    Merci attack = 60
    Merci HP = 200
    Merci ls = 40
    Merci atkRange = 1
    Merci cost = 100

    Ana HP = 130
    Ana attack = 85
    Ana ls = 35
    Ana atkRange = 6
    Ana cost = 200

    Lucio HP = 450
    Lucio attack = 40
    Lucio ls = 45
    Lucio atkRange = 1
    Lucio cost = 230

    ATBD credit gain = [95 , 140 , 190]
    ATBD move cost = [25 ,45 ,50]
    Initial ATBD Credit = 150
     */

    private int m,n;
    private Unit[][] field;

    private int initialATBDCredits,atbdPlacementCost,initVirusHP,initATBDHP
            , initVirusATK , initVirusLifeSteal, initATBDATK, initATBDLifeSteal
            , atbdMoveCost , atbdCreditsDrop;



    static Unit gangster = new Virus(75,50,250,1,4,"{}");
    static Unit pistolDude = new Virus(150,100,200,3,5,"{}");
    static Unit sniper = new Virus(150,20,160,1,6,"{}");
    static Unit[] viruses = {gangster,pistolDude,sniper};

    static Unit Merci = new ATBD_(1696969,20,10000,20,1,1,1,"{}");
    static Unit Ana = new ATBD_(80,50,600,12,2,2,1,"{}");
    static Unit Lucio = new ATBD_(150,50,1000,18,1,3,1,"{}");
    static Unit[] Atbds = {Merci,Ana,Lucio};

    static int virustemplate = viruses.length;
    static int atbdtemplate = Atbds.length;
    static int[] cost = new int[3];


    int lowestcost = Integer.MAX_VALUE;
    int highestcost = 0;
    public void Initialize() {
        config(inFile);
        geneticReader(geneinFile);

        for (int i = 0; i < virustemplate; i++) {
            viruses[i].setDf(dfvatk[i],dfvls[i],dfvhp[i],dfvgain[i],dfvatkR[i],1);
            Atbds[i].setDf(dfaatk[i],dfals[i],dfahp[i],dfacost[i],dfaatkR[i],dfamoveCost[i]);
        }

        for (int i = 0; i < virustemplate; i++) {
            viruses[i].configMod(initVirusATK, initVirusLifeSteal, initVirusHP,atbdCreditsDrop, atbdMoveCost,geneVirus[i]);
            Atbds[i].configMod(initATBDATK, initVirusLifeSteal, initATBDHP, atbdPlacementCost, atbdMoveCost,geneATBD[i]);
        }
        for (int i = 0; i < atbdtemplate; i++) {
            cost[i] = Atbds[i].getCost();
            if(lowestcost>cost[i]) lowestcost = cost[i];
            if(highestcost<cost[i]) highestcost = cost[i];
        }
        shop = Shop.getInstance(cost,highestcost);
        shop.setdf(dfShop_cur);
        shop.setCurrency(initialATBDCredits);
        field = new Unit[m][n];
        order = new ArrayList<>();
        virusOrder = new ArrayList<>();
        atbdOrder = new ArrayList<>();

        Shop.updateCost(cost,highestcost);

        System.out.println(shop.getMap().keySet());
        initObjective(100);
        virusLimit= gObjective.snd();
        limitCount =0;
        emptySlot = new TreeSet<>(comp);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String s = String.valueOf(i) + " " + String.valueOf(j);
                emptySlot.add(s);
            }
        }
        System.out.println(emptySlot);

        JSONObject data = new JSONObject();
        data.put("pauseState" , 0);
        Controller.putData("http://localhost:8080/input/put/pausestate" , data);
        data.clear();
        data.put("speedState" , 0);
        Controller.putData("http://localhost:8080/input/put/speedstate" , data);


    }
    public  void GetInput(){
        // ????????????????

        Controller.getInput();

        int placeState = Controller.getInputData("placeState");
        int moveState = Controller.getInputData("moveState");
        int pauseState = Controller.getInputData("pauseState");
        int speedState = Controller.getInputData("speedState");

        if(placeState == 2) {
            Controller.getInput();

            JSONObject data = new JSONObject();
            data.put("placeState" , 0);
            Controller.putData("http://localhost:8080/input/put/placestate" , data);

            int skin = Controller.getInputData("job");
            int posx = Controller.getInputData("posX_place");
            int posy = Controller.getInputData("posY_place");

            System.out.println(skin);

            System.out.println("SPAWzN");
            // SPAWN ATBD
            addATBD(createNewATBD(skin-1),new Pair<>(posy,posx));
//            List<Integer> posx2 =new ArrayList<>();
//            List<Integer> posy2 =new ArrayList<>();
//            List<Integer> hp =new ArrayList<>();
//            List<Integer> maxHp =new ArrayList<>();
//            List<Integer> skin2 =new ArrayList<>();
//            int cur = shop.getCurrency();
//            int[] obj = {gObjective.fst(),gObjective.snd()};
//            shop.updateStatus();
//            List<Boolean> shopStat = shop.getStatus();
//            for(Unit u: order){
//                maxHp.add(u.getMaxHp());
//                hp.add(u.getHp());
//                posx2.add(u.getPosition().snd());
//                posy2.add(u.getPosition().fst());
//                skin2.add(u.getSkin());
//            }
//            List<Integer> cost = shop.getcostList();
//            Controller.sendGameData(n,m,1,shopStat,cur,cost ,posx2,posy2,hp,maxHp,skin2,obj[0],obj[1]);
        }

        if(moveState == 3) {
            Controller.getInput();

            JSONObject data = new JSONObject();
            data.put("moveState" , 0);
            Controller.putData("http://localhost:8080/input/put/movestate" , data);
            int ogX = Controller.getInputData("ogX");
            int ogY = Controller.getInputData("ogY");
            int posx = Controller.getInputData("posX_move");
            int posy = Controller.getInputData("posY_move");
            moveATBD(field[ogY][ogX],new Pair<>(posy,posx));
        }

        if(placeState == 69) {
            how2Play = true;
            Controller.sendPlaceState(0);
        }
        if(placeState == 999) {
            waitingRestart = true;
            Controller.sendPlaceState(0);
        }

        if(pauseState == 1){
            pause = 1;
        } else {
            pause = 0;
        }

        if(speedState == 1) {
            speed = 2;
        } else {
            speed = 1;
        }
    }



    static int pause = 0;
    static int speed = 1;
    public  void Update() throws GameOverException,GameWinException{

        int totalTime = 0;
        long prevTime = System.currentTimeMillis();
        int rand;
        while(gObjective.snd() - gObjective.fst() > 0){
            GetInput();
            if(pause == 1) {
                continue;
            }

            int periodTime = 0;
            if(speed == 1)
                periodTime = 1600;
            else
                periodTime = 800;

            long curTime = System.currentTimeMillis();
            totalTime += curTime - prevTime;
            prevTime = curTime;

            if(totalTime > periodTime) {
                totalTime = 0;
                Iterator<Unit> it = order.iterator();
                while (it.hasNext()){
                    Unit y = it.next();
                    try {
                        y.execute();
                    }catch (DeadException e){
                        it.remove();
                    }
                    List<Integer> posx =new ArrayList<>();
                    List<Integer> posy =new ArrayList<>();
                    List<Integer> hp =new ArrayList<>();
                    List<Integer> maxHp =new ArrayList<>();
                    List<Integer> skin =new ArrayList<>();
                    int cur = shop.getCurrency();
                    int[] obj = {gObjective.fst(),gObjective.snd()};
                    shop.updateStatus();
                    List<Boolean> shopStat = shop.getStatus();
//            System.out.println("shopStat"+ shopStat);
                    for(Unit u: order){
                        maxHp.add(u.getMaxHp());
                        hp.add(u.getHp());
                        posx.add(u.getPosition().snd());
                        posy.add(u.getPosition().fst());
                        skin.add(u.getSkin());
                    }
                    List<Integer> cost = shop.getcostList();
                    Controller.sendGameData(n,m,1,shopStat,cur,cost ,posx,posy,hp,maxHp,skin,obj[0],obj[1]);

//                    try{
//                        Thread.sleep(100);
//                    } catch (Exception ignored){
//
//                    }
                }
                updateDeadlist();

                List<Integer> posx =new ArrayList<>();
                List<Integer> posy =new ArrayList<>();
                List<Integer> hp =new ArrayList<>();
                List<Integer> maxHp =new ArrayList<>();
                List<Integer> skin =new ArrayList<>();
                int cur = shop.getCurrency();
                int[] obj = {gObjective.fst(),gObjective.snd()};
                shop.updateStatus();
                List<Boolean> shopStat = shop.getStatus();
//            System.out.println("shopStat"+ shopStat);
                for(Unit u: order){
                    maxHp.add(u.getMaxHp());
                    hp.add(u.getHp());
                    posx.add(u.getPosition().snd());
                    posy.add(u.getPosition().fst());
                    skin.add(u.getSkin());
                }
                List<Integer> cost = shop.getcostList();
                Controller.sendGameData(n,m,1,shopStat,cur,cost ,posx,posy,hp,maxHp,skin,obj[0],obj[1]);

                if(emptySlot.size() == 0 && atbdOrder.isEmpty()) throw new GameOverException("fullfield");
                if(limitCount<virusLimit) {

//                    double period = 1 / virusSpawnRate;
//                    if (spawnCount >= period) {
//                        rand = (int) (Math.random() * 3);
//                        if (rand == 0) {
//                            addVirus(createNewVirus(0), randomTile());
//                            spawnCount = spawnCount - rand;
//                            limitCount++;
//
//                        }
//                        if (rand == 1) {
//                            addVirus(createNewVirus(1), randomTile());
//                            spawnCount = spawnCount - 2 * rand;
//                            limitCount++;
//                        }
//                        if (rand == 2) {
//                            addVirus(createNewVirus(2), randomTile());
//                            spawnCount = spawnCount - 3 * rand;
//                            limitCount++;
//                        }
//                    } else {
//                        spawnCount++;
//                    }

                    // Second Type

                    double period = 1 / virusSpawnRate;
//                    int spawnChance = (int) (1000.0 * virusSpawnRate);
                    Random r = new Random();
//                    int isSpawn = r.nextInt(1001);
                    if (spawnCount >= period) {
                        int virusType = 1;
                        rand = (int) (Math.random() * 3);
                        if ((double) gObjective.fst() / gObjective.snd() > 0.2)
                            virusType++;
                        if ((double) gObjective.fst() / gObjective.snd() > 0.35)
                            virusType++;

                        int randomVirus = r.nextInt(virusType);
                        if (randomVirus == 0) {
                            addVirus(createNewVirus(0), randomTile());
                            spawnCount = spawnCount - rand;
                            limitCount++;
                        }
                        if (randomVirus == 1) {
                            addVirus(createNewVirus(1), randomTile());
                            spawnCount = spawnCount - 2 * rand;
                            limitCount++;
                        }
                        if (randomVirus == 2) {
                            addVirus(createNewVirus(2), randomTile());
                            spawnCount = spawnCount - 3 * rand;
                            limitCount++;
                        }
                    } else {
                        spawnCount++;
                    }
                }


                Game.getInstance().visualize();
                if(shop.getCurrency()<lowestcost&&atbdOrder.size() == 0){
                    throw new GameOverException("You lose");
                }

            }




            List<Integer> posx =new ArrayList<>();
            List<Integer> posy =new ArrayList<>();
            List<Integer> hp =new ArrayList<>();
            List<Integer> maxHp =new ArrayList<>();
            List<Integer> skin =new ArrayList<>();
            int cur = shop.getCurrency();
            int[] obj = {gObjective.fst(),gObjective.snd()};
            shop.updateStatus();
            List<Boolean> shopStat = shop.getStatus();
//            System.out.println("shopStat"+ shopStat);
            for(Unit u: order){
                maxHp.add(u.getMaxHp());
                hp.add(u.getHp());
                posx.add(u.getPosition().snd());
                posy.add(u.getPosition().fst());
                skin.add(u.getSkin());
            }
            List<Integer> cost = shop.getcostList();
            Controller.sendGameData(n,m,1,shopStat,cur,cost ,posx,posy,hp,maxHp,skin,obj[0],obj[1]);




//            System.out.println(maxHp);
//            System.out.println(hp);
//            System.out.println(posx);
//            System.out.println(posy);
//            System.out.println(skin);

//            Thread.sleep(1000/speed);
            //fetch api


        }
        System.out.println("Ezgaem");
        throw new GameWinException("You win");
    }

    boolean how2Play = false;
    boolean waitingRestart = false;


    boolean lose = false;
    boolean win = false;
    public void reset(){

    }


    public static void main(String[] args) {
        while (true) {


            Game gay = Game.getInstance();
            gay.how2Play = false;
            Controller.sendGameState(0);
            Controller.sendPlaceState(0);

            while (!gay.how2Play){
                gay.GetInput();
            }
            gay.Initialize();

            try {
                gay.Update();
            } catch (GameOverException e) {
                gay.lose = true;
                //tell api
                List<Integer> posx =new ArrayList<>();
                List<Integer> posy =new ArrayList<>();
                List<Integer> hp =new ArrayList<>();
                List<Integer> maxHp =new ArrayList<>();
                List<Integer> skin =new ArrayList<>();
                int cur = gay.shop.getCurrency();
                int[] obj = {gay.gObjective.fst(),gay.gObjective.snd()};
                gay.shop.updateStatus();
                List<Boolean> shopStat = gay.shop.getStatus();
                for(Unit u: gay.order){
                    maxHp.add(u.getMaxHp());
                    hp.add(u.getHp());
                    posx.add(u.getPosition().snd());
                    posy.add(u.getPosition().fst());
                    skin.add(u.getSkin());
                }
                List<Integer> cost = gay.shop.getcostList();
                Controller.sendGameData(gay.n,gay.m,3,shopStat,cur,cost ,posx,posy,hp,maxHp,skin,obj[0],obj[1]);


                System.out.println(e.getMessage());

            }catch (GameWinException e){
                gay.win = true;
                //tell api
                List<Integer> posx =new ArrayList<>();
                List<Integer> posy =new ArrayList<>();
                List<Integer> hp =new ArrayList<>();
                List<Integer> maxHp =new ArrayList<>();
                List<Integer> skin =new ArrayList<>();
                int cur = gay.shop.getCurrency();
                int[] obj = {gay.gObjective.fst(),gay.gObjective.snd()};
                gay.shop.updateStatus();
                List<Boolean> shopStat = gay.shop.getStatus();
                for(Unit u: gay.order){
                    maxHp.add(u.getMaxHp());
                    hp.add(u.getHp());
                    posx.add(u.getPosition().snd());
                    posy.add(u.getPosition().fst());
                    skin.add(u.getSkin());
                }
                List<Integer> cost = gay.shop.getcostList();
                Controller.sendGameData(gay.n,gay.m,2,shopStat,cur,cost ,posx,posy,hp,maxHp,skin,obj[0],obj[1]);


                System.out.println(e.getMessage());
            }
            gay.waitingRestart = false;

            Controller.sendPlaceState(0);
            while (!gay.waitingRestart) {
                gay.GetInput();
            }

        }
    }

    public void geneticReader(String geneinFile){
        try(FileReader fr = new FileReader(geneinFile);
            Scanner s = new Scanner(fr)){
            int geneATBDCount = 0;
            int geneVirusCount = 0;
            String temp = "";
            while( geneATBDCount < 3){
                while (true){
                    String lastS = s.nextLine();
                    if( lastS.equals("-")) break;
//                    System.out.println( temp);
                    temp = temp + " " + lastS;
                }
                geneATBD[geneATBDCount] = temp;
                geneATBDCount++;
                temp = "";
            }
            while ( geneVirusCount < 3){
                while (true){
                    String lastS = s.nextLine();
                    if( lastS.equals("-")) break;
                    temp = temp + " " + lastS;
                }
                geneVirus[geneVirusCount] = temp;
                geneVirusCount++;
                temp = "";
            }
            System.out.println("ATBD A : " + geneATBD[0] + " end");
            System.out.println("ATBD B : " + geneATBD[1] + " end");
            System.out.println("ATBD C : " + geneATBD[2] + " end");
            System.out.println("Virus A : " + geneVirus[0] + " end");
            System.out.println("Virus B : " + geneVirus[1] + " end");
            System.out.println("Virus C : " + geneVirus[2] + " end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void config(String inFile){
        try(FileReader fr = new FileReader(inFile);
            Scanner s = new Scanner(fr)){
            System.out.println("--------1--------");
            m = s.nextInt();
            n = s.nextInt();
            if( m <= 0 || n <= 0 || m+n ==2){ throw new IOException(); }
            System.out.println("m : " + m);
            System.out.println("n : " + n);
            System.out.println("--------2--------");
            System.out.print("Virus spawn rate : ");
            virusSpawnRate = s.nextDouble();
            if ( virusSpawnRate <= 0 || virusSpawnRate>1){ throw new IOException(); }
            System.out.println(virusSpawnRate);
            System.out.println("--------3--------");
            System.out.print("Initial antibody credits : ");
            initialATBDCredits = s.nextInt();
            if ( initialATBDCredits < 0 ){ throw new IOException(); }
            System.out.println(initialATBDCredits);
            System.out.print("ATBD placement cost : ");
            atbdPlacementCost = s.nextInt();
            if ( atbdPlacementCost + dfm_cost <=0 ){ throw new IOException(); }
            if(initialATBDCredits+dfShop_cur<atbdPlacementCost+dfm_cost) {throw new IOException();}
            System.out.println(atbdPlacementCost);
            System.out.println("--------4--------");
            System.out.print("Initial Virus Health : ");
            initVirusHP = s.nextInt();
            if( initVirusHP +dfv3_hp<=0 ){ throw new IOException(); }
            System.out.println(initVirusHP);
            System.out.print("Initial ATBD Health : ");
            initATBDHP = s.nextInt();
            if( initATBDHP +dfa_hp<=0 ){ throw new IOException(); }
            System.out.println(initATBDHP);
            System.out.println("--------5--------");
            System.out.print("Virus Attack Damage : ");
            initVirusATK = s.nextInt();
            if( initVirusATK +dfv1_atk <=0 ){ throw new IOException(); }
            System.out.println(initVirusATK);
            System.out.print("Virus Lifesteal : ");
            initVirusLifeSteal = s.nextInt();
            if( initVirusLifeSteal < 0 ){ throw new IOException(); }
            System.out.println(initVirusLifeSteal);
            System.out.println("--------6--------");
            System.out.print("Antibody Attack Damage : ");
            initATBDATK = s.nextInt();
            if( initATBDATK +dfl_atk<=0){ throw new IOException(); }
            System.out.println(initATBDATK);
            System.out.print("Antibody Lifesteal : ");
            initATBDLifeSteal = s.nextInt();
            if( initATBDLifeSteal < 0 ){ throw new IOException(); }
            System.out.println(initATBDLifeSteal);
            System.out.println("--------7--------");
            System.out.print("Antibody Move Cost : ");
            atbdMoveCost = s.nextInt();
            if( atbdMoveCost +dfm_moveCost <0 ){ throw new IOException(); }
            System.out.println(atbdMoveCost);
            System.out.println("--------8--------");
            System.out.print("Antibody Credits Drop : ");
            atbdCreditsDrop = s.nextInt();
            System.out.println(atbdCreditsDrop);
            if( atbdCreditsDrop + dfv1_gain <=0 ) throw new IOException();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    //ganster
    static int dfv1_atk = 30;
    static int dfv1_ls  = 10;
    static int dfv1_hp  = 115;
    static int dfv1_cost = 1;
    static int dfv1_atkRange = 1;

    //pistoldude
    static int dfv2_atk = 45;
    static int dfv2_ls = 10;
    static int dfv2_hp = 120;
    static int dfv2_cost = 1;
    static int dfv2_atkRange =2;

    //sniper
    static int dfv3_atk = 80;
    static int dfv3_ls = 5;
    static int dfv3_hp = 60;
    static int dfv3_gene;
    static int dfv3_cost = 1;
    static int dfv3_atkRange=6;
    static int dfv3_skin;


    //merci
    static int dfm_atk = 60;
    static int dfm_ls = 20;
    static int dfm_hp = 140;
    static int dfm_gene;
    static int dfm_cost = 70;
    static int dfm_atkRange = 1;
    static int dfm_skin;
    static int dfm_moveCost =25;
    //ana
    static int dfa_atk = 45;
    static int dfa_ls = 15;
    static int dfa_hp = 140;
    static int dfa_gene;
    static int dfa_cost = 240-30-10;
    static int dfa_atkRange = 5;
    static int dfa_skin;
    static int dfa_moveCost =45 ;
    //lucio
    static int dfl_atk = 40;
    static int dfl_ls = 30;
    static int dfl_hp = 390;
    static int dfl_gene;
    static int dfl_cost = 250;
    static int dfl_atkRange =2;
    static int dfl_skin;
    static int dfl_moveCost =50;
    //shop n movecost
    static int dfShop_cur = 230;
    //field

    static int dfv1_gain = 42;
    static int dfv2_gain = 54;
    static int dfv3_gain = 80;

    static int[] dfvatk= {dfv1_atk,dfv2_atk,dfv3_atk};
    static int[] dfvls= {dfv1_ls,dfv2_ls,dfv3_ls};
    static int[] dfvhp= {dfv1_hp,dfv2_hp,dfv3_hp};
    static int[] dfvatkR = {dfv1_atkRange,dfv2_atkRange,dfv3_atkRange};
    static int[] dfvgain = {dfv1_gain,dfv2_gain,dfv3_gain};

    static int[] dfaatk = {dfm_atk,dfa_atk,dfl_atk};
    static int[] dfals = {dfm_ls,dfa_ls,dfl_ls};
    static int[] dfahp = {dfm_hp,dfa_hp,dfl_hp};
    static int[] dfaatkR = {dfm_atkRange,dfa_atkRange,dfl_atkRange};
    static int[] dfacost = {dfm_cost,dfa_cost,dfl_cost};
    static int[] dfamoveCost = {dfm_moveCost,dfa_moveCost,dfl_moveCost};

}