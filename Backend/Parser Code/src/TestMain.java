public class TestMain {
    public static void main(String[] args) throws SyntaxErrorException, TokenizeErrorException {
        Evaluator g = GeneticEvaluator.getInstance();
        VirusDummy v = new VirusDummy("timeUnit = timeUnit + 1 " +
                "if(timeUnit % 4) then {  " +
                "    atbdLoc = antibody " +
                "    if(atbdLoc / 10 - 1) then { " +
                "        if (atbdLoc % 10 - 7) then move upleft " +
                "        else if (atbdLoc % 10 - 6) then move left " +
                "        else if (atbdLoc % 10 - 5) then move downleft " +
                "        else if (atbdLoc % 10 - 4) then move down " +
                "        else if (atbdLoc % 10 - 3) then move downright " +
                "        else if (atbdLoc % 10 - 2) then move right " +
                "        else if (atbdLoc % 10 - 1) then move upright " +
                "        else move up " +
                "    } else if(atbdLoc) then { " +
                "        if (atbdLoc % 10 - 7) then move downright " +
                "        else if (atbdLoc % 10 - 6) then move right " +
                "        else if (atbdLoc % 10 - 5) then move upright " +
                "        else if (atbdLoc % 10 - 4) then move up " +
                "        else if (atbdLoc % 10 - 3) then move upleft " +
                "        else if (atbdLoc % 10 - 2) then move left " +
                "        else if (atbdLoc % 10 - 1) then move downleft " +
                "        else move down  " +
                "    } else { " +
                "        dir = random % 8 " +
                "        if (dir - 6) then move upleft " +
                "        else if (dir - 5) then move left " +
                "        else if (dir - 4) then move downleft " +
                "        else if (dir - 3) then move down " +
                "        else if (dir - 2) then move downright " +
                "        else if (dir - 1) then move right " +
                "        else if (dir) then move upright " +
                "        else move up " +
                "    } " +
                "} else { " +
                "    atbdLoc = antibody " +
                "    if(atbdLoc / 10 - 3) then { " +
                " " +
                "    } else if(atbdLoc) then { " +
                "        if (atbdLoc % 10 - 7) then shoot upleft " +
                "        else if (atbdLoc % 10 - 6) then shoot left " +
                "        else if (atbdLoc % 10 - 5) then shoot downleft " +
                "        else if (atbdLoc % 10 - 4) then shoot down " +
                "        else if (atbdLoc % 10 - 3) then shoot downright " +
                "        else if (atbdLoc % 10 - 2) then shoot right " +
                "        else if (atbdLoc % 10 - 1) then shoot upright " +
                "        else shoot up " +
                "    } else { " +
                "         " +
                "    } " +
                "}");
        VirusDummy w = new VirusDummy("if(0) then move up else {move down move up shoot left}");
//        v.setProgram(g.evaluate(v));
        w.setProgram(g.evaluate(w));
        int cnt = 0;
        while(cnt < 10) {
            w.execute();
            cnt++;
        }
//        w.execute();
    }
}
