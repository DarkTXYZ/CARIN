import java.util.Map;

public interface Unit {

    /**
     * Move unit to the input direction one space
     * @param direction the direction that the unit move
     */
    void move(String direction);

    /**
     * Make the unit shoot to the input direction
     * @param direction the direction that the unit shoot
     */
    void shoot(String direction);

    String getGeneticCode();

    Map<String,Integer> getBindings();

    void execute();
}
