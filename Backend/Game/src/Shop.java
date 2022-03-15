import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    private static Shop instance;
    private int currency;
    Map<Integer,Boolean> status;
    int maxCurrency;

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
    private Shop(int[] cost,int max){
        status = new LinkedHashMap<>();
        maxCurrency = max*3;
        for(int i = 0;i<cost.length;i++){
            status.put(cost[i],false);
        }
    }
    public static Shop getInstance(int[] cost,int max){
        if(Shop.instance == null) Shop.instance = new Shop(cost,max);
        return Shop.instance;
    }
    public static void updateCost(int[] cost,int max){
        instance.maxCurrency = max*3;
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
        if(currency>maxCurrency) currency = maxCurrency;
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
    public void setdf(int cur){
        currency = cur;
        if(currency>maxCurrency) currency = maxCurrency;
    }
}
