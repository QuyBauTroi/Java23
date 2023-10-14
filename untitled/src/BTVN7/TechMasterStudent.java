package BTVN7;

public abstract class TechMasterStudent {
    private  String name;
    private  String object;

    public TechMasterStudent(String name, String object) {
        this.name = name;
        this.object = object;
    }

    public  abstract double getScore();

    public String getAcademicAbility() {
        if (getScore() < 5)
            return "Yeu";
        else if (getScore()>= 5 && getScore()<6.5)
            return "Trung Binh";
        else if (getScore()>= 6.5 && getScore()<7.5)
            return "Kha";
        else if (getScore()>=7.5 && getScore()<= 10)
            return "Gioi";
        return null;
    }
    public void printInfo(){
        System.out.println("Hoc sinh: "+ name+ "- Nghe nghiep: "+ object+"- Diem thanh phan: "
                + getScore()+ "- Hoc luc: "+getAcademicAbility());
    }


}
