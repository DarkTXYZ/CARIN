
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Game {

    static int m = 20,n = 20;
    static Unit[][] field= new Unit[m][n];
    protected static final String inFile = "src/configfile.in";
    static int initialATBDCredits,atbdPlacementCost,initVirusHP,initATBDHP
            , initVirusATK , initVirusLifeSteal, initATBDATK, initATBDLifeSteal
            , atbdMoveCost , atbdCreditsDrop;
    static double spawnCount = 0;
    static double virusSpawnRate;
    static SortedSet<String> emptySlot = new TreeSet<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            String[] arr1 = o1.split(" ");
            String[] arr2 = o2.split(" ");
            if(Integer.parseInt(arr1[0])==Integer.parseInt(arr2[0])){
                return Integer.parseInt(arr1[1])-Integer.parseInt(arr2[1]);
            }
            return Integer.parseInt(arr1[0])-Integer.parseInt(arr2[0]);
        }
    });

    static GeneticEvaluator g  = GeneticEvaluator.getInstance();
    static List<Unit> order = new ArrayList<>();
    static List<Unit> virusOrder = new ArrayList<>();
    static List<Unit> atbdOrder = new ArrayList<>();
    static Objective gObjective;
    static int virusLimit;
    static Shop shop;

    public static void initObjective(int maxElim){
        gObjective = new Objective(0,maxElim);
    }
    //tell if we win
    public static void notifyReachElim(){
        System.out.println("All viruses have been eliminated");
    }
    public static Pair<Integer,Integer> randomTile()throws GameOverException{
        try {
            //write random here
            Random r = new Random();
            int area;
            if (emptySlot.size() == 1) {
                area = 1;
            } else {
                area = emptySlot.size() / 2;
            }
            int rand = r.nextInt(area);

            String s = emptySlot.toArray(new String[emptySlot.size()])[rand];
//            int length = s.length()/2;
//            if(length %2 ==1&&s.length()>2) length++;
//            //1 2
//            //2 1
//            // 01 0
//            int runner = 0;
//            String posY =""; String posX="";
//            while (runner<s.length()) {
//                if (runner < length) {
//                    posY += s.charAt(runner);
//                }else{
//                    posX+=s.charAt(runner);
//                }
//                runner++;
//            }
            String[] arr = s.split(" ");
            int y = Integer.parseInt(arr[0]);
            int x = Integer.parseInt(arr[1]);
            System.out.println("y = " + y+" x = "+x);
            return new Pair<>(y, x);
        }catch (Exception e){
            throw new GameOverException("Game over");
        }
    }
    static Queue<Pair<Unit,Pair<Integer,Integer>>>deadList = new LinkedList<>();
    public static void updateDeadlist(){
        while(!deadList.isEmpty()){
            Pair<Unit,Pair<Integer,Integer>> u = deadList.poll();
            addVirus(u.fst(),u.snd());
        }
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
            shop.setCurrency(-a.getCost());
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
        int y = position.fst(); int x = position.snd();
          unit.setPos(position);
          field[y][x] = unit;
          order.add(unit);
          String s = String.valueOf(y)+" "+String.valueOf(x);
          emptySlot.remove(s);
    }

    public static void remove(Pair<Integer,Integer> position){

        int y = position.fst(); int x = position.snd();
//        order.remove(field[y][x]);
        virusOrder.remove(field[y][x]);
        atbdOrder.remove(field[y][x]);
        field[y][x] = null;
        String s = String.valueOf(y)+" "+String.valueOf(x);
        emptySlot.add(s);

    }
    public static void moveATBD(Unit u,Pair<Integer,Integer> destination){
        u.setHP(-atbdMoveCost);
        try {
            move(u,destination);
        }catch (Exception e){
            System.out.println("can't execute move");
        }
    }
    public static void move (Unit u, Pair<Integer,Integer> destination) throws UnexecutableCommandException{
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
    public static void destroyATBD(Unit unit, Unit spawn){
        Pair<Integer,Integer> pos = unit.getPosition();
        remove(pos);
        Unit u = new Virus(spawn);
        deadList.add(new Pair<>(u,pos));
    }
    public static void destroyVirus(Unit unit){
        Pair<Integer,Integer> pos = unit.getPosition();
        remove(pos);
        shop.setCurrency(atbdCreditsDrop);
        gObjective.modfst(1);
    }
    public static void visualize(){
        System.out.println("-------------------------------");
        for(int i =0; i<m;i++){
            for (int j= 0;j<n;j++){
                if(Objects.equals(field[i][j],null)) {
                    System.out.print("| "+i+","+j+" |");
                }else
                    System.out.print("|"+field[i][j].getClass().getName()+"|");
            }
            System.out.print("\n");
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

    public static int senseClosestVirus(Unit u){
        int hy = u.getPosition().fst(); int hx = u.getPosition().snd();
        int tempans = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        for(int i = 0 ; i < virusOrder.size() ; ++i) {
            int y = virusOrder.get(i).getPosition().fst();
            int x = virusOrder.get(i).getPosition().snd();
            int xdiff = x-hx;
            int ydiff = y-hy;
            if(Math.abs(xdiff) == Math.abs(ydiff)){ //diagonal
                if(xdiff < 0 && ydiff<0 ){//upleft
                    tempans =  xdiff*-10+8;
                }
                if(xdiff>0 && ydiff > 0) {// downright
                    tempans =  xdiff*10+4;
                }
                if(xdiff>0 && ydiff <0 ){ //upright
                    tempans = xdiff*10+2;
                }
                if(xdiff<0 && ydiff>0 ){ // downleft
                    tempans = ydiff*10+6;
                }
            }else { //line
                if(ydiff == 0 && xdiff>0){ // right
                    tempans = xdiff*10+3;
                }
                if(ydiff == 0 && xdiff<0){// left
                    tempans = xdiff*-10+7;
                }
                if(xdiff == 0 &&ydiff<0){// up
                    tempans = ydiff*-10+1;
                }
                if(xdiff == 0 && ydiff>0){// down
                    tempans = ydiff*10+5;
                }
            }
            ans = Math.min(ans,tempans);
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
        }
        if(ans == Integer.MAX_VALUE) ans =0;
        return ans;
    }
    public static int senseClosestATBD(Unit u){
        int hy = u.getPosition().fst(); int hx = u.getPosition().snd();
        int tempans = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        for(int i = 0 ; i < atbdOrder.size() ; ++i) {
            int y = atbdOrder.get(i).getPosition().fst();
            int x = atbdOrder.get(i).getPosition().snd();
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
    public static int senseNearby(Unit u, String direction){
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
    public static void gShoot(Pair<Integer,Integer> pos, Unit u){
        int y = pos.fst(); int x = pos.snd();
        if(y<0 || x < 0 || y>=m || x>=n) System.out.println("can't shoot out of range"); // should not happen
        field[y][x].takingDamage(u);
    }

    public static void updateShop(){
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
        Unit a = new ATBD_(Atbds[n]);
        return a;
    }

    static Unit gangster = new Virus(75,50,250,"move right",1,3);
    static Unit pistolDude = new Virus(50,100,200,"move left",1,4);
    static Unit sniper = new Virus(150,20,160,"move downright",1,5);
    static Unit[] viruses = {gangster,pistolDude,sniper};

    static Unit Merci = new ATBD_(696969,20,1,    "virusLoc = virus " +
            "if (virusLoc / 10 - 1) " +
            "then  " +
            "  if (virusLoc % 10 - 7) then move upleft " +
            "  else if (virusLoc % 10 - 6) then move left " +
            "  else if (virusLoc % 10 - 5) then move downleft " +
            "  else if (virusLoc % 10 - 4) then move down " +
            "  else if (virusLoc % 10 - 3) then move downright " +
            "  else if (virusLoc % 10 - 2) then move right " +
            "  else if (virusLoc % 10 - 1) then move upright " +
            "  else move up " +
            "else if (virusLoc) " +
            "then  " +
            "  if (virusLoc % 10 - 7) then shoot upleft " +
            "  else if (virusLoc % 10 - 6) then shoot left " +
            "  else if (virusLoc % 10 - 5) then shoot downleft " +
            "  else if (virusLoc % 10 - 4) then shoot down " +
            "  else if (virusLoc % 10 - 3) then shoot downright " +
            "  else if (virusLoc % 10 - 2) then shoot right " +
            "  else if (virusLoc % 10 - 1) then shoot upright " +
            "  else shoot up " +
            "else {} "
            ,20,1,0);
    static Unit Ana = new ATBD_(80,50,600,"anaaa",12,2,1);
    static Unit Lucio = new ATBD_(150,50,1000,"lucio",18,1,2);
    static Unit[] Atbds = {Merci,Ana,Lucio};

    static int virustemplate = viruses.length;
    static int atbdtemplate = Atbds.length;
    static int[] cost = {Merci.getCost(),Ana.getCost(),Lucio.getCost()};





    public static void Initialize(){
        config(inFile);
        shop = Shop.getInstance(cost);
        shop.setCurrency(initialATBDCredits);
        for(int i =0;i<virustemplate;i++){
            viruses[i].configMod(initVirusATK,initVirusLifeSteal,initVirusHP,atbdPlacementCost,atbdMoveCost);
            Atbds[i].configMod(initATBDATK,initVirusLifeSteal,initATBDHP,atbdPlacementCost,atbdMoveCost);
        }
        for (int i = 0;i<atbdtemplate;i++){
            cost[i] = Atbds[i].getCost();
        }
        Shop.updateCost(cost);
        System.out.println(shop.getMap().keySet());
        initObjective(10);
        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){
                String s = String.valueOf(i)+" "+String.valueOf(j);
                emptySlot.add(s);
            }
        }
        addATBD(createNewATBD(0),new Pair<>(2,2));
//        addATBD(createNewATBD(0),new Pair<>(,10));
//        addATBD(createNewATBD(0),new Pair<>(19,19));
        System.out.println(emptySlot);
    }
    public static void GetInput(){
        // ????????????????
    }

    public static void Update() throws InterruptedException, GameOverException {
        int rand;
        while(gObjective.snd() - gObjective.fst() > 0){
            /*if(ซื้อตัว){
                //เสกมา
            }
            */
            //fetch continue
            Iterator<Unit> it = order.iterator();
            while (it.hasNext()){
                Unit u = it.next();
                try {
                    u.execute();
                }catch (DeadException e){
                    it.remove();
                }
            }
            updateDeadlist();
            if( spawnCount >= 1 ){
                rand = (int)(Math.random() * 3);
                if( rand == 0 ){
                    addVirus(createNewVirus(0), randomTile());
                    spawnCount = spawnCount - rand;
                    addATBD(createNewATBD(1),randomTile());
                }
                if( rand == 1 ){
                    addVirus(createNewVirus(1), randomTile());
                    spawnCount = spawnCount - 2*rand;
                }
                if( rand == 2 ){
                    addVirus(createNewVirus(2), randomTile());
                    spawnCount = spawnCount - 3*rand;
                }
            }else{
                spawnCount++;
            }
            visualize();
            List<Integer> posx =new ArrayList<>();
            List<Integer> posy =new ArrayList<>();
            List<Integer> hp =new ArrayList<>();
            List<Integer> maxHp =new ArrayList<>();
            List<Integer> skin =new ArrayList<>();
            int cur = shop.getCurrency();
            int[] obj = {gObjective.fst(),gObjective.snd()};
            System.out.println("Shop Update");
            shop.updateStatus();
            List<Boolean> shopStat = shop.getStatus();
            System.out.println(shopStat);

            for(Unit u: order){
                maxHp.add(u.getMaxHp());
                hp.add(u.getHp());
                posx.add(u.getPosition().snd());
                posy.add(u.getPosition().fst());
                skin.add(u.getSkin());
            }
//            System.out.println(maxHp);
//            System.out.println(hp);
//            System.out.println(posx);
//            System.out.println(posy);
//            System.out.println(skin);

            Thread.sleep(1000);
            //fetch api
        }
        System.out.println("Ezgaem");
    }

    public static void main(String[] args) {
        Initialize();

//        GetInput();
        try {
            Update();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());}

//        System.out.println(Game.senseClosestVirus(field[2][2])); //13
//        System.out.println(Game.senseClosestATBD(field[2][1])); //13
//        System.out.println(Game.senseClosestATBD(field[2][3])); //17
//        System.out.println(Game.senseClosestATBD(field[4][0])); //22
//        System.out.println(Game.senseClosestATBD(field[0][4])); // 26

//        addVirus(createNewVirus(0),new Pair<>(2,1));
//        addVirus(createNewVirus(1),new Pair<>(2,3));
//        addATBD(createNewATBD(1),new Pair<>(5,2));
//        addVirus(createNewVirus(1),new Pair<>(2,4));
//        addVirus(createNewVirus(1),new Pair<>(3,0));
//        addVirus(createNewVirus(1),new Pair<>(0,4));
//        addATBD(createNewATBD(1),new Pair<>(2,2));

//        addVirus(createNewVirus(0),randomTile());
//        addVirus(createNewVirus(1),randomTile());
//        addATBD(createNewATBD(1),randomTile());
//        addVirus(createNewVirus(1),randomTile());
//        addVirus(createNewVirus(1),randomTile());
//        addVirus(createNewVirus(1),randomTile());
//        addATBD(createNewATBD(1),randomTile());
//          addVirus(createNewVirus(1), new Pair<>(1,1));
//          addATBD(createNewATBD(1),new Pair<>(1,4));




//        Objective = new Objective(0,2);
//        visualize();
//        System.out.println(senseNearby(field[0][4],"downleft")); //22
//        System.out.println(senseNearby(field[5][2],"downright")); // 0
//        System.out.println(senseNearby(field[2][1],"right")); //12
//        System.out.println(senseNearby(field[3][0],"upright")); //11
//        System.out.println(senseNearby(field[2][2],"right")); //11
//        System.out.println(senseNearby(field[2][2],"left")); //11
//        System.out.println(senseNearby(field[2][2],"down")); //32

        //corner case :
            //topleft - left => pass , upleft => pass , up => pass
            //topright - right => pass , upright => pass , up ==> pass
            //bottomleft - left => pass , downleft => pass , downright => pass , upleft => pass
            //bottomright - right => pass , upright => pass , downright => pass , downleft => pass
        //side case :
            //left side - left => pass , upleft => pass , downleft => pass
            //right side - right => pass , upright => pass , downright => pass
            //upper side - up => pass , upleft => pass , upright => pass
            //bottom side - down => pass , downleft => pass , downright => pass








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
            System.out.println("--------8--------");
            System.out.print("Antibody Credits Drop : ");
            atbdCreditsDrop = s.nextInt();
            System.out.println(atbdCreditsDrop);
            if( atbdCreditsDrop <= 0 ) throw new IOException();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

}
