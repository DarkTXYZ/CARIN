import java.util.Map;

public class Shop {
    private static Shop instance;
    private int currency;
    Map<Integer,Boolean> status;
    /** update shop*/
    public void  updateStatus(){
        for(int a:status.keySet()){
            if(a>=currency) {
                status.replace(a,true);
            }else status.replace(a,false);
        }
    }
    private Shop(){}
    public static Shop getInstance(){
        if(Shop.instance == null) Shop.instance = new Shop();
        return Shop.instance;
    }


    private void updateCurrency(){
        currency ++;
    }
    public void modCurrency(int mod){
        currency+=mod;
    }
    public int getCurrency(){return currency;}

}
