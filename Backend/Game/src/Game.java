import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    public static Game instance;
    static Unit gangster = new Virus(75, 50, 250, 1, 4, "{}");
    static Unit pistolDude = new Virus(150, 100, 200, 3, 5, "{}");
    static Unit sniper = new Virus(150, 20, 160, 1, 6, "{}");
    static Unit[] viruses = {gangster, pistolDude, sniper};
    static int virustemplate = viruses.length;
    static Unit Merci = new ATBD_(1696969, 20, 10000, 20, 1, 1, 1, "{}");
    static Unit Ana = new ATBD_(80, 50, 600, 12, 2, 2, 1, "{}");
    static Unit Lucio = new ATBD_(150, 50, 1000, 18, 1, 3, 1, "{}");
    static Unit[] Atbds = {Merci, Ana, Lucio};
    static int atbdtemplate = Atbds.length;
    static int[] cost = new int[3];
    static int pause = 0;
    static int speed = 1;
    //ganster
    static int dfv1_atk = 30;
    static int dfv1_ls = 10;
    static int dfv1_hp = 115;
    static int dfv1_atkRange = 1;
    //pistoldude
    static int dfv2_atk = 45;
    static int dfv2_ls = 10;
    static int dfv2_hp = 120;
    static int dfv2_atkRange = 2;
    //sniper
    static int dfv3_atk = 140;
    static int dfv3_ls = 5;
    static int dfv3_hp = 60;
    static int dfv3_atkRange = 6;
    //merci
    static int dfm_atk = 60;
    static int dfm_ls = 20;
    static int dfm_hp = 140;
    static int dfm_cost = 70;
    static int dfm_atkRange = 1;
    static int dfm_moveCost = 25;
    //ana
    static int dfa_atk = 45;
    static int dfa_ls = 15;
    static int dfa_hp = 140;
    static int dfa_cost = 200;
    static int dfa_atkRange = 5;
    static int dfa_moveCost = 45;
    //lucio
    static int dfl_atk = 40;
    static int dfl_ls = 30;
    static int dfl_hp = 390;
    static int dfl_cost = 250;
    static int dfl_atkRange = 2;
    static int dfl_moveCost = 50;
    //shop n movecost
    static int dfShop_cur = 230;
    static int dfv1_gain = 42;
    static int dfv2_gain = 54;
    static int dfv3_gain = 80;
    static int dfObjective =100;
    static int[] dfvatk = {dfv1_atk, dfv2_atk, dfv3_atk};
    static int[] dfvls = {dfv1_ls, dfv2_ls, dfv3_ls};
    static int[] dfvhp = {dfv1_hp, dfv2_hp, dfv3_hp};
    static int[] dfvatkR = {dfv1_atkRange, dfv2_atkRange, dfv3_atkRange};
    static int[] dfvgain = {dfv1_gain, dfv2_gain, dfv3_gain};
    static int[] dfaatk = {dfm_atk, dfa_atk, dfl_atk};
    static int[] dfals = {dfm_ls, dfa_ls, dfl_ls};
    static int[] dfahp = {dfm_hp, dfa_hp, dfl_hp};
    static int[] dfaatkR = {dfm_atkRange, dfa_atkRange, dfl_atkRange};
    static int[] dfacost = {dfm_cost, dfa_cost, dfl_cost};
    static int[] dfamoveCost = {dfm_moveCost, dfa_moveCost, dfl_moveCost};
    protected final String inFile = "src/configfile.in";
    protected final String geneinFile = "src/geneticcodeInput.in";
    protected final String geneDefault = "src/geneticcodeDefault.in";
    protected String[] geneATBD = new String[3]; // init genetic code for each ATBD
    protected String[] geneVirus = new String[3]; // init genetic code for each Virus
    protected String[] defaultGeneATBD = new String[3];
    protected String[] defaultGeneVirus = new String[3];
    Comparator<String> comp = (o1, o2) -> {
        String[] arr1 = o1.split(" ");
        String[] arr2 = o2.split(" ");
        if (Integer.parseInt(arr1[0]) == Integer.parseInt(arr2[0])) {
            return Integer.parseInt(arr1[1]) - Integer.parseInt(arr2[1]);
        }
        return Integer.parseInt(arr1[0]) - Integer.parseInt(arr2[0]);
    };
    Queue<Pair<Unit, Pair<Integer, Integer>>> deadList = new LinkedList<>();
    int lowestcost = Integer.MAX_VALUE;
    int highestcost = 0;
    boolean how2Play = false;
    boolean waitingRestart = false;
    private double spawnCount = 0;
    private double virusSpawnRate;
    private SortedSet<String> emptySlot = new TreeSet<>(comp);
    private List<Unit> order;
    private List<Unit> virusOrder;
    private List<Unit> atbdOrder;
    private Objective gObjective;
    private int virusLimit;
    private int limitCount;
    private Shop shop;
    private int m, n;
    private Unit[][] field;
    private int initialATBDCredits, atbdPlacementCost, initVirusHP, initATBDHP, initVirusATK, initVirusLifeSteal, initATBDATK, initATBDLifeSteal, atbdMoveCost, atbdCreditsDrop;
    private Game() {
    }

    public static Game getInstance() {
        if (Game.instance == null) Game.instance = new Game();
        return Game.instance;
    }

    public static void main(String[] args) {
        while (true) {


            Game carin = Game.getInstance();
            carin.how2Play = false;
            Controller.sendGameState(0);
            Controller.sendPlaceState(0);

            while (!carin.how2Play) {
                carin.GetInput();
            }
            carin.Initialize();

            try {
                carin.Update();
            } catch (GameOverException e) {
                //tell api
                List<Integer> posx = new ArrayList<>();
                List<Integer> posy = new ArrayList<>();
                List<Integer> hp = new ArrayList<>();
                List<Integer> maxHp = new ArrayList<>();
                List<Integer> skin = new ArrayList<>();
                int cur = carin.shop.getCurrency();
                int[] obj = {carin.gObjective.fst(), carin.gObjective.snd()};
                carin.shop.updateStatus();
                List<Boolean> shopStat = carin.shop.getStatus();
                for (Unit u : carin.order) {
                    maxHp.add(u.getMaxHp());
                    hp.add(u.getHp());
                    posx.add(u.getPosition().snd());
                    posy.add(u.getPosition().fst());
                    skin.add(u.getSkin());
                }
                List<Integer> cost = carin.shop.getcostList();
                Controller.sendGameData(carin.m, carin.n, 3, shopStat, cur, cost, posx, posy, hp, maxHp, skin, obj[0], obj[1]);
                System.out.println(e.getMessage());

            } catch (GameWinException e) {
                //tell api
                List<Integer> posx = new ArrayList<>();
                List<Integer> posy = new ArrayList<>();
                List<Integer> hp = new ArrayList<>();
                List<Integer> maxHp = new ArrayList<>();
                List<Integer> skin = new ArrayList<>();
                int cur = carin.shop.getCurrency();
                int[] obj = {carin.gObjective.fst(), carin.gObjective.snd()};
                carin.shop.updateStatus();
                List<Boolean> shopStat = carin.shop.getStatus();
                for (Unit u : carin.order) {
                    maxHp.add(u.getMaxHp());
                    hp.add(u.getHp());
                    posx.add(u.getPosition().snd());
                    posy.add(u.getPosition().fst());
                    skin.add(u.getSkin());
                }
                List<Integer> cost = carin.shop.getcostList();
                Controller.sendGameData(carin.m, carin.n, 2, shopStat, cur, cost, posx, posy, hp, maxHp, skin, obj[0], obj[1]);
                System.out.println(e.getMessage());
            }
            carin.waitingRestart = false;
            Controller.sendPlaceState(0);
            while (!carin.waitingRestart) {
                carin.GetInput();
            }

        }
    }

    public void initObjective(int maxElim) {
        gObjective = new Objective(0, maxElim);
    }

    //tell if we win
    public void notifyReachElim() {
        System.out.println("All viruses have been eliminated");

    }

    public Pair<Integer, Integer> randomTile() throws GameOverException {
        //write random here
        Random r = new Random();
        int area;
        System.out.println("empty" + emptySlot.size());
        if (emptySlot.size() == 0 && !atbdOrder.isEmpty()) return new Pair<>(-1, -1);
        if (emptySlot.size() == 0) {
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
        System.out.println("y = " + y + " x = " + x);
        return new Pair<>(y, x);
    }

    public void updateDeadlist() {
        while (!deadList.isEmpty()) {
            Pair<Unit, Pair<Integer, Integer>> u = deadList.poll();
            addVirus(u.fst(), u.snd());
        }
    }

    public void addATBD(Unit a, Pair<Integer, Integer> position) {
        if (a.getCost() > shop.getCurrency()) return;
        int y = position.fst();
        int x = position.snd();
        visualize();
        System.out.println("field" + m + " " + n);
        System.out.println("y" + y + " x" + x);
        if (y >= m || x >= n || y < 0 || x < 0) {
            System.out.println("out of range");
            return;
        }
        if (Objects.equals(field[y][x], null)) {
            add(a, position);
            atbdOrder.add(a);
            shop.setCurrency(-a.getCost());
        } else System.out.println("can't add; This tile already has a gameUnit");

    }

    public void addVirus(Unit v, Pair<Integer, Integer> position) {
        int y = position.fst();
        int x = position.snd();
        if (y >= m || x >= n || y < 0 || x < 0) {
            System.out.println("out of range");
            return;
        }
        if (Objects.equals(field[y][x], null)) {
            add(v, position);
            virusOrder.add(v);
        } else System.out.println("can't add; This tile already has a gameUnit");

    }

    //this method should init gameUnit
    public void add(Unit unit, Pair<Integer, Integer> position) {
        int y = position.fst();
        int x = position.snd();
        unit.setPos(position);
        field[y][x] = unit;
        order.add(unit);
        String s = y + " " + x;
        emptySlot.remove(s);
    }

    public void remove(Pair<Integer, Integer> position) {

        int y = position.fst();
        int x = position.snd();
        virusOrder.remove(field[y][x]);
        atbdOrder.remove(field[y][x]);
        field[y][x] = null;
        String s = y + " " + x;
        emptySlot.add(s);

    }

    public void moveATBD(Unit u, Pair<Integer, Integer> destination) {
        if (u.getHp() == 1) return;
        u.setHP(-u.getMoveCost());
        if (!Objects.equals(u.getClass().getName(), "ATBD_")) return;
        try {
            move(u, destination);
        } catch (Exception e) {
            System.out.println("can't execute move");
        }
    }

    public void move(Unit u, Pair<Integer, Integer> destination) throws UnexecutableCommandException {
        //field [x][y]
        int y = destination.fst();
        int x = destination.snd();
        if (y < 0 || x < 0) throw new UnexecutableCommandException("Out of field range");
        int oldy = u.getPosition().fst();
        int oldx = u.getPosition().snd();
        if (y >= m || x >= n) {
            return;
        }
        if (Objects.equals(field[y][x], null)) {
            Unit temp = field[oldy][oldx];
            temp.setPos(destination);
            field[y][x] = temp;
            field[oldy][oldx] = null;
            String s = oldy + " " + oldx;
            emptySlot.add(s);
            String newS = y + " " + x;
            emptySlot.remove(newS);
        } else {
            throw new UnexecutableCommandException("gameUnit occupied in destination tile");
        }
    }

    public void destroyATBD(Unit unit, Unit spawn) {
        Pair<Integer, Integer> pos = unit.getPosition();
        remove(pos);
        Unit u = new Virus(spawn);
        u.setHP(-u.getHp()/2);
        deadList.add(new Pair<>(u, pos));
    }

    public void destroyVirus(Unit unit) {
        Pair<Integer, Integer> pos = unit.getPosition();
        remove(pos);
        shop.setCurrency(unit.getCost());
        gObjective.modfst(1);
    }
    //field

    public void visualize() {
        System.out.println("-------------------------------");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (Objects.equals(field[i][j], null)) {
                    System.out.print("| " + i + "," + j + " |");
                } else
                    System.out.print("|" + field[i][j].getClass().getName() + "|");
            }
            System.out.println(" ");
        }
        System.out.println("Empty tile: " + emptySlot);
        System.out.println("list order" + order.toString());
        System.out.println("virus order" + virusOrder.toString());
        System.out.println("atbd order" + atbdOrder.toString());
        System.out.println("Shop currency: " + shop.getCurrency());
        System.out.println("Objective :" + gObjective.fst() + "/" + gObjective.snd());
        System.out.println("-------------------------------");
    }

    public int senseClosestEnemy(Unit u, int type) {
        List<Unit> toItr;
        if (type == 2) {
            toItr = atbdOrder;
        } else if (type == 1) {
            toItr = virusOrder;
        } else return -69;
        int hy = u.getPosition().fst();
        int hx = u.getPosition().snd();
        int tempans = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        for (Unit unit : toItr) {
            int y = unit.getPosition().fst();
            int x = unit.getPosition().snd();
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
        if (ans == Integer.MAX_VALUE) ans = 0;
        return ans;
    }

    public int senseClosestVirus(Unit u) {
        return senseClosestEnemy(u, 1);
    }

    public int senseClosestATBD(Unit u) {
        return senseClosestEnemy(u, 2);
    }

    public int senseNearby(Unit u, String direction) {
        int hy = u.getPosition().fst();
        int hx = u.getPosition().snd();
        int x = hx;
        int y = hy;
        switch (direction) {
            case "right" -> {
                x++;
                while (x < n) {
                    if (!Objects.equals((field[y][x]), null)) {
                        if (field[y][x].getClass().getName().equals("Virus")) return (x - hx) * 10 + 1;
                        else return (x - hx) * 10 + 2;
                    } else {
                        x++;
                    }
                }
                return 0;
            }
            case "left" -> {
                x--;
                while (x >= 0) {
                    if (!Objects.equals((field[y][x]), null)) {
                        if (field[y][x].getClass().getName().equals("Virus")) return (hx - x) * 10 + 1;
                        else return (hx - x) * 10 + 2;
                    } else {
                        x--;
                    }
                }
                return 0;
            }
            case "up" -> {  // << mark
                y--;
                while (y >= 0) {
                    if (!Objects.equals((field[y][x]), null)) {
                        if (field[y][x].getClass().getName().equals("Virus")) return (hy - y) * 10 + 1;
                        else return (hy - y) * 10 + 2;
                    } else {
                        y--;
                    }
                }
                return 0;
            }
            case "down" -> {
                y++;
                while (y < m) {
                    if (!Objects.equals((field[y][x]), null)) {
                        if (field[y][x].getClass().getName().equals("Virus")) return (y - hy) * 10 + 1;
                        else return (y - hy) * 10 + 2;
                    } else {
                        y++;
                    }
                }
                return 0;
            }
            case "upright" -> {
                y--;
                x++;
                while (y >= 0 && x < n) {
                    if (!Objects.equals((field[y][x]), null)) {
                        if (field[y][x].getClass().getName().equals("Virus")) return (hy - y) * 10 + 1;
                        else return (hy - y) * 10 + 2;
                    } else {
                        y--;
                        x++;
                    }
                }
                return 0;
            }
            case "downright" -> {
                y++;
                x++;
                while (y < m && x < n) {
                    if (!Objects.equals((field[y][x]), null)) {
                        if (field[y][x].getClass().getName().equals("Virus")) return (y - hy) * 10 + 1;
                        else return (y - hy) * 10 + 2;
                    } else {
                        y++;
                        x++;
                    }
                }
                return 0;
            }
            case "downleft" -> {
                y++;
                x--;
                while (y < m && x >= 0) {
                    if (!Objects.equals((field[y][x]), null)) {
                        if (field[y][x].getClass().getName().equals("Virus")) return (y - hy) * 10 + 1;
                        else return (y - hy) * 10 + 2;
                    } else {
                        y++;
                        x--;
                    }
                }
                return 0;
            }
            case "upleft" -> {
                y--;
                x--;
                while (y >= 0 && x >= 0) {
                    if (!Objects.equals((field[y][x]), null)) {
                        if (field[y][x].getClass().getName().equals("Virus")) return (hy - y) * 10 + 1;
                        else return (hy - y) * 10 + 2;
                    } else {
                        y--;
                        x--;
                    }
                }
                return 0;
            }
        }
        return -99;
    }

    public void gShoot(Pair<Integer, Integer> pos, Unit u) {
        int y = pos.fst();
        int x = pos.snd();
        if (y < 0 || x < 0 || y >= m || x >= n) System.out.println("can't shoot out of range"); // should not happen
        field[y][x].takingDamage(u);
    }

    Unit createNewVirus(int n) {
        return new Virus(viruses[n]);
    }

    Unit createNewATBD(int n) {
        return new ATBD_(Atbds[n]);
    }

    @SuppressWarnings("unchecked")
    public void Initialize() {
        config(inFile);
        geneticReader(geneinFile);
        geneticDefaultReader(geneDefault);
        for (int i = 0; i < virustemplate; i++) {
            viruses[i].setDf(dfvatk[i], dfvls[i], dfvhp[i], dfvgain[i], dfvatkR[i], 1);
            Atbds[i].setDf(dfaatk[i], dfals[i], dfahp[i], dfacost[i], dfaatkR[i], dfamoveCost[i]);
        }

        for (int i = 0; i < virustemplate; i++) {
            viruses[i].configMod(initVirusATK, initVirusLifeSteal, initVirusHP, atbdCreditsDrop, atbdMoveCost, geneVirus[i]);
            if (Objects.equals(viruses[i].getProgram(), null)) {
                viruses[i].configMod(0, 0, 0, 0, 0, defaultGeneVirus[i]);
            }
            Atbds[i].configMod(initATBDATK, initATBDLifeSteal, initATBDHP, atbdPlacementCost, atbdMoveCost, geneATBD[i]);
            if (Objects.equals(Atbds[i].getProgram(), null)) {
                Atbds[i].configMod(0, 0, 0, 0, 0, defaultGeneATBD[i]);
            }
        }
        for (int i = 0; i < atbdtemplate; i++) {
            cost[i] = Atbds[i].getCost();
            if (lowestcost > cost[i]) lowestcost = cost[i];
            if (highestcost < cost[i]) highestcost = cost[i];
        }
        shop = Shop.getInstance(cost, highestcost);
        shop.setdf(dfShop_cur);
        shop.setCurrency(initialATBDCredits);
        field = new Unit[m][n];
        order = new ArrayList<>();
        virusOrder = new ArrayList<>();
        atbdOrder = new ArrayList<>();

        Shop.updateCost(cost,highestcost);
        initObjective(dfObjective);
        virusLimit= gObjective.snd();
        limitCount =0;
        emptySlot = new TreeSet<>(comp);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String s = i + " " + j;
                emptySlot.add(s);
            }
        }
        System.out.println(emptySlot);

        JSONObject data = new JSONObject();
        data.put("pauseState", 0);
        Controller.putData("http://localhost:8080/input/put/pausestate", data);
        data.clear();
        data.put("speedState", 0);
        Controller.putData("http://localhost:8080/input/put/speedstate", data);


    }

    @SuppressWarnings("unchecked")
    public void GetInput() {
        // ????????????????

        Controller.getInput();

        int placeState = Controller.getInputData("placeState");
        int moveState = Controller.getInputData("moveState");
        int pauseState = Controller.getInputData("pauseState");
        int speedState = Controller.getInputData("speedState");

        if (placeState == 2) {
            Controller.getInput();

            JSONObject data = new JSONObject();
            data.put("placeState", 0);
            Controller.putData("http://localhost:8080/input/put/placestate", data);

            int skin = Controller.getInputData("job");
            int posx = Controller.getInputData("posX_place");
            int posy = Controller.getInputData("posY_place");

            System.out.println(skin);

            System.out.println("SPAWzN");
            // SPAWN ATBD
            addATBD(createNewATBD(skin - 1), new Pair<>(posy, posx));
        }

        if (moveState == 3) {
            Controller.getInput();
            JSONObject data = new JSONObject();
            data.put("moveState", 0);
            Controller.putData("http://localhost:8080/input/put/movestate", data);
            int ogX = Controller.getInputData("ogX");
            int ogY = Controller.getInputData("ogY");
            int posx = Controller.getInputData("posX_move");
            int posy = Controller.getInputData("posY_move");
            moveATBD(field[ogY][ogX], new Pair<>(posy, posx));
        }

        if (placeState == 69) {
            how2Play = true;
            Controller.sendPlaceState(0);
        }
        if (placeState == 999) {
            waitingRestart = true;
            Controller.sendPlaceState(0);
        }

        if (pauseState == 1) {
            pause = 1;
        } else {
            pause = 0;
        }

        if (speedState == 1) {
            speed = 2;
        } else {
            speed = 1;
        }
    }

    public void Update() throws GameOverException, GameWinException {

        int totalTime = 0;
        long prevTime = System.currentTimeMillis();
        int rand;
        while (gObjective.snd() - gObjective.fst() > 0) {
            GetInput();
            if (pause == 1) {
                continue;
            }

            int periodTime;
            if (speed == 1)
                periodTime = 1600;
            else
                periodTime = 800;

            long curTime = System.currentTimeMillis();
            totalTime += curTime - prevTime;
            prevTime = curTime;

            if (totalTime > periodTime) {
                totalTime = 0;
                Iterator<Unit> it = order.iterator();
                while (it.hasNext()) {
                    Unit y = it.next();
                    try {
                        y.execute();
                    } catch (DeadException e) {
                        it.remove();
                    }
                    List<Integer> posx = new ArrayList<>();
                    List<Integer> posy = new ArrayList<>();
                    List<Integer> hp = new ArrayList<>();
                    List<Integer> maxHp = new ArrayList<>();
                    List<Integer> skin = new ArrayList<>();
                    int cur = shop.getCurrency();
                    int[] obj = {gObjective.fst(), gObjective.snd()};
                    shop.updateStatus();
                    List<Boolean> shopStat = shop.getStatus();
                    for (Unit u : order) {
                        maxHp.add(u.getMaxHp());
                        hp.add(u.getHp());
                        posx.add(u.getPosition().snd());
                        posy.add(u.getPosition().fst());
                        skin.add(u.getSkin());
                    }
                    List<Integer> cost = shop.getcostList();
                    Controller.sendGameData(m, n, 1, shopStat, cur, cost, posx, posy, hp, maxHp, skin, obj[0], obj[1]);
                }
                updateDeadlist();

                List<Integer> posx = new ArrayList<>();
                List<Integer> posy = new ArrayList<>();
                List<Integer> hp = new ArrayList<>();
                List<Integer> maxHp = new ArrayList<>();
                List<Integer> skin = new ArrayList<>();
                int cur = shop.getCurrency();
                int[] obj = {gObjective.fst(), gObjective.snd()};
                shop.updateStatus();
                List<Boolean> shopStat = shop.getStatus();
                for (Unit u : order) {
                    maxHp.add(u.getMaxHp());
                    hp.add(u.getHp());
                    posx.add(u.getPosition().snd());
                    posy.add(u.getPosition().fst());
                    skin.add(u.getSkin());
                }
                List<Integer> cost = shop.getcostList();
                Controller.sendGameData(m, n, 1, shopStat, cur, cost, posx, posy, hp, maxHp, skin, obj[0], obj[1]);

                if (emptySlot.size() == 0 && atbdOrder.isEmpty()) throw new GameOverException("fullfield");
                if (emptySlot.size() == 0 && virusOrder.isEmpty()) throw new GameWinException("EZ");
                if (limitCount < virusLimit) {
                    double period = 1 / virusSpawnRate;
                    Random r = new Random();
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
                            spawnCount = spawnCount - 1.5 * rand;
                            limitCount++;
                        }
                        if (randomVirus == 2) {
                            addVirus(createNewVirus(2), randomTile());
                            spawnCount = spawnCount - 2 * rand;
                            limitCount++;
                        }
                    } else {
                        spawnCount++;
                    }
                }
                Game.getInstance().visualize();
                if (shop.getCurrency() < lowestcost && atbdOrder.size() == 0) {
                    throw new GameOverException("You lose");
                }

            }
            List<Integer> posx = new ArrayList<>();
            List<Integer> posy = new ArrayList<>();
            List<Integer> hp = new ArrayList<>();
            List<Integer> maxHp = new ArrayList<>();
            List<Integer> skin = new ArrayList<>();
            int cur = shop.getCurrency();
            int[] obj = {gObjective.fst(), gObjective.snd()};
            shop.updateStatus();
            List<Boolean> shopStat = shop.getStatus();
            for (Unit u : order) {
                maxHp.add(u.getMaxHp());
                hp.add(u.getHp());
                posx.add(u.getPosition().snd());
                posy.add(u.getPosition().fst());
                skin.add(u.getSkin());
            }
            List<Integer> cost = shop.getcostList();
            Controller.sendGameData(m, n, 1, shopStat, cur, cost, posx, posy, hp, maxHp, skin, obj[0], obj[1]);
        }
        System.out.println("Ezgaem");
        throw new GameWinException("You win");
    }

    public void geneticReader(String geneinFile) {
        try (FileReader fr = new FileReader(geneinFile);
             Scanner s = new Scanner(fr)) {
            int geneATBDCount = 0;
            int geneVirusCount = 0;
            StringBuilder temp = new StringBuilder();
            while (geneATBDCount < 3) {
                while (true) {
                    String lastS = s.nextLine();
                    if (lastS.equals("-")) break;
                    temp.append(" ").append(lastS);
                }
                geneATBD[geneATBDCount] = temp.toString();
                geneATBDCount++;
                temp = new StringBuilder();
            }
            while (geneVirusCount < 3) {
                while (true) {
                    String lastS = s.nextLine();
                    if (lastS.equals("-")) break;
                    temp.append(" ").append(lastS);
                }
                geneVirus[geneVirusCount] = temp.toString();
                geneVirusCount++;
                temp = new StringBuilder();
            }
            System.out.println("ATBD A : " + geneATBD[0] + " end");
            System.out.println("ATBD B : " + geneATBD[1] + " end");
            System.out.println("ATBD C : " + geneATBD[2] + " end");
            System.out.println("Virus A : " + geneVirus[0] + " end");
            System.out.println("Virus B : " + geneVirus[1] + " end");
            System.out.println("Virus C : " + geneVirus[2] + " end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void geneticDefaultReader(String geneinFile) {
        try (FileReader fr = new FileReader(geneinFile);
             Scanner s = new Scanner(fr)) {
            int geneATBDCount = 0;
            int geneVirusCount = 0;
            StringBuilder temp = new StringBuilder();
            while (geneATBDCount < 3) {
                while (true) {
                    String lastS = s.nextLine();
                    if (lastS.equals("-")) break;
//                    System.out.println( temp);
                    temp.append(" ").append(lastS);
                }
                defaultGeneATBD[geneATBDCount] = temp.toString();
                geneATBDCount++;
                temp = new StringBuilder();
            }
            while (geneVirusCount < 3) {
                while (true) {
                    String lastS = s.nextLine();
                    if (lastS.equals("-")) break;
                    temp.append(" ").append(lastS);
                }
                defaultGeneVirus[geneVirusCount] = temp.toString();
                geneVirusCount++;
                temp = new StringBuilder();
            }

            System.out.println("dATBD A : " + defaultGeneATBD[0] + " end");
            System.out.println("dATBD B : " + defaultGeneATBD[1] + " end");
            System.out.println("dATBD C : " + defaultGeneATBD[2] + " end");
            System.out.println("dVirus A : " + defaultGeneVirus[0] + " end");
            System.out.println("dVirus B : " + defaultGeneVirus[1] + " end");
            System.out.println("dVirus C : " + defaultGeneVirus[2] + " end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void config(String inFile) {
        try (FileReader fr = new FileReader(inFile);
             Scanner s = new Scanner(fr)) {
            System.out.println("--------1--------");
            m = s.nextInt();
            n = s.nextInt();
            if (m <= 0 || n <= 0 || m + n == 2) {
                throw new IOException();
            }
            System.out.println("m : " + m);
            System.out.println("n : " + n);
            System.out.println("--------2--------");
            System.out.print("Virus spawn rate : ");
            virusSpawnRate = s.nextDouble();
            if (virusSpawnRate <= 0 || virusSpawnRate > 1) {
                throw new IOException();
            }
            System.out.println(virusSpawnRate);
            System.out.println("--------3--------");
            System.out.print("Initial antibody credits : ");
            initialATBDCredits = s.nextInt();
            if (initialATBDCredits < 0) {
                throw new IOException();
            }
            System.out.println(initialATBDCredits);
            System.out.print("ATBD placement cost : ");
            atbdPlacementCost = s.nextInt();
            if (atbdPlacementCost + dfm_cost <= 0) {
                throw new IOException();
            }
            if (initialATBDCredits + dfShop_cur < atbdPlacementCost + dfm_cost) {
                throw new IOException();
            }
            System.out.println(atbdPlacementCost);
            System.out.println("--------4--------");
            System.out.print("Initial Virus Health : ");
            initVirusHP = s.nextInt();
            if (initVirusHP + dfv3_hp <= 0) {
                throw new IOException();
            }
            System.out.println(initVirusHP);
            System.out.print("Initial ATBD Health : ");
            initATBDHP = s.nextInt();
            if (initATBDHP + dfa_hp <= 0) {
                throw new IOException();
            }
            System.out.println(initATBDHP);
            System.out.println("--------5--------");
            System.out.print("Virus Attack Damage : ");
            initVirusATK = s.nextInt();
            if (initVirusATK + dfv1_atk <= 0) {
                throw new IOException();
            }
            System.out.println(initVirusATK);
            System.out.print("Virus Lifesteal : ");
            initVirusLifeSteal = s.nextInt();
            if (initVirusLifeSteal + dfv3_ls <= 0) {
                throw new IOException();
            }
            System.out.println(initVirusLifeSteal);
            System.out.println("--------6--------");
            System.out.print("Antibody Attack Damage : ");
            initATBDATK = s.nextInt();
            if (initATBDATK + dfl_atk <= 0) {
                throw new IOException();
            }
            System.out.println(initATBDATK);
            System.out.print("Antibody Lifesteal : ");
            initATBDLifeSteal = s.nextInt();
            if (initATBDLifeSteal + dfa_ls <= 0) {
                throw new IOException();
            }
            System.out.println(initATBDLifeSteal);
            System.out.println("--------7--------");
            System.out.print("Antibody Move Cost : ");
            atbdMoveCost = s.nextInt();
            if (atbdMoveCost + dfm_moveCost < 0) {
                throw new IOException();
            }
            System.out.println(atbdMoveCost);
            System.out.println("--------8--------");
            System.out.print("Antibody Credits Drop : ");
            atbdCreditsDrop = s.nextInt();
            System.out.println(atbdCreditsDrop);
            if (atbdCreditsDrop + dfv1_gain <= 0) throw new IOException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}