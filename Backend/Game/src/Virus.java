import java.util.Map;

public class Virus implements Unit{
    Map<String,Integer> bindings;
    Pair<Integer,Integer> position;

    int Hp = 690;
    int Atk = 69;
    int cost;
    String geneticCode = "iam groot";
    //program

    public Virus(){
        System.out.println("iam here");
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


    public void move(Pair<Integer,Integer> position){
    }
    public void shoot(String direction){}

    public void attack(Unit a){
        a.takingDamage(this);
    }
    public void destruct(){}

    public void setHp(){}
    public void setAttack(){}
    public int getHp(){
        return Hp;
    }

    public int getAtk(){
        return Atk;
    }
    public String getGene(){return geneticCode;}


    public void takingDamage(Unit attacker){
        Hp-=attacker.getAtk();
    }

    public Pair<Integer,Integer> getPosition(){
        return position;
    }
}
