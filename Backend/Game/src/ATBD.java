import java.util.Map;

public class ATBD extends UnitImpl{
    @Override
    public void destruct() {
        Game.destroyATBD(this,previousAttacker);
    }
}
