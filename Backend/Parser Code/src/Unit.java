import java.util.Map;

public interface Unit {
    /**
     * Move unit to the input direction one space
     *
     * @param direction the direction that the unit move
     */
    void move(String direction);

    /**
     * Make the unit shoot to the input direction
     *
     * @param direction the direction that the unit shoot
     */
    void shoot(String direction);

    /**
     * Return the genetic code of the unit
     *
     * @return the unit's genetic code
     */
    String getGeneticCode();

    /**
     * Return the binding of the unit
     *
     * @return bindings
     */
    Map<String, Integer> getBindings();

    /**
     * Execute the program of the unit
     */
    void execute();
}
