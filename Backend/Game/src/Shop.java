import java.util.Map;

public class Shop {
    private static Shop instance;
    int currency;
    Map<ATBD,Boolean> status;
    /** update shop*/
    public void  updateStatus(){
        for(ATBD a:status.keySet()){
            if(a.getCost()>=currency) {
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

}
