package HospitalFliick;

public class Preson {
    private String name;
    private int age;


    public Preson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Preson() {

    }


    @Override
    public String toString() {
        return "Preson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
