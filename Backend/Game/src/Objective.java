public class Objective extends Pair<Integer,Integer>{
    public Objective(Integer fst, Integer snd) {
        super(fst, snd);
    }
    public void modfst(int mod){
        fst+=mod;
        if(fst == snd) Game.getInstance().notifyReachElim();
    }
}
