package Instances;

public class Instances {

    private static Instances mcInstance = null;

    public synchronized static Instances getMCInstance(){
        if (Instances.mcInstance == null){
            Instances.mcInstance = new Instances();
        }
        return Instances.mcInstance;
    }

    private Instances(){

    }


    public void testOutput(){
        System.out.println("SingleTon-Class created!");
    }



}
