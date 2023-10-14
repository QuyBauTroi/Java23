package HospitalFliick;

public class Doctor extends Preson{
    private String specialistInfor;
    private double workHour;


    public Doctor(String name, int age, String specialistInfor, double workHour) {
        super(name, age);
        this.specialistInfor = specialistInfor;
        this.workHour = workHour;
    }

    public Doctor() {
        super();
    }

    public String getSpecialistInfor() {
        return specialistInfor;
    }

    public void setSpecialistInfor(String specialistInfor) {
        this.specialistInfor = specialistInfor;
    }

    public double getWorkHour() {
        return workHour;
    }

    public void setWorkHour(double workHour) {
        this.workHour = workHour;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "specialistInfor='" + specialistInfor + '\'' +
                ", workHour=" + workHour +
                '}';
    }
}
