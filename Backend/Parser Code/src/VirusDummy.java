import java.util.HashMap;
import java.util.Map;

public class VirusDummy implements Unit {

    String geneticCode;
    Map<String, Integer> bindings;
    Executable program;

    public VirusDummy(String src) {
        this.geneticCode = src;
        this.bindings = new HashMap<>();
    }

    @Override
    public void move(String direction) {
        System.out.println("move " + direction);
    }

    @Override
    public void shoot(String direction) {
        System.out.println("shoot " + direction);
    }

    @Override
    public String getGeneticCode() {
        return geneticCode;
    }

    @Override
    public Map<String, Integer> getBindings() {
        return bindings;
    }

    public void setProgram(Executable program) {
        this.program = program;
    }

    @Override
    public void execute() {
        program.execute();
    }

}
