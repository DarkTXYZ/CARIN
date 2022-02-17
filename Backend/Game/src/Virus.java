import java.util.Map;

public class Virus extends UnitImpl{

    public Virus(){
        System.out.println("Virus created");
        geneticCode = "dfaultvirus";
    }
    public Virus(int atk,String gene){
        this.Atk = atk;
        geneticCode = gene;

    }
    public Virus(Unit template){
        Hp = template.getHp();
        Atk = template.getAtk();
        geneticCode = template.getGene();

    }

    @Override
    public void destruct() {

    }
    
}
