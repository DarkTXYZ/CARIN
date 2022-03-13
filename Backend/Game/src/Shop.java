import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    private static Shop instance;
    private int currency;
    Map<Integer,Boolean> status;

    /** update shop*/
    public void  updateStatus(){
        for(int a:status.keySet()){

            if(currency>= a) {
                status.replace(a,true);

            }else {
                status.replace(a,false);

            }

        }
    }
    private Shop(int[] cost){
        status = new LinkedHashMap<>();
        for(int i = 0;i<cost.length;i++){
            status.put(cost[i],false);
        }
    }
    public static Shop getInstance(int[] cost){
        if(Shop.instance == null) Shop.instance = new Shop(cost);
        return Shop.instance;
    }
    public static void updateCost(int[] cost){
        instance.status = new LinkedHashMap<>();
        for(int i = 0;i<cost.length;i++){
            instance.status.put(cost[i],false);
        }
    }
    
    public Map<Integer, Boolean> getMap(){
        return status;
    }

    public void setCurrency(int mod){
        currency+=mod;
    }
    public int getCurrency(){return currency;}
    public List<Boolean> getStatus(){
        List<Boolean> ret = new ArrayList<>();
        for (int i:status.keySet()){
            ret.add(status.get(i));
        }
        return ret;
    }
    public List<Integer> getcostList(){
        List<Integer> ret = new ArrayList<>();
        for(int a:status.keySet()){
            ret.add(a);
        }
        return ret;
    }
}
