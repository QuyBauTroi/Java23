package HospitalFliick;

public class Patient extends Preson{
    private int patientID;
    private String timeCheckIn;



    public Patient(String name, int age, int patientID, String timeCheckIn) {
        super(name, age);
        this.patientID = patientID;
        this.timeCheckIn = timeCheckIn;
    }



    @Override
    public String toString() {
        return "Patient{" +
                "patientID=" + patientID +
                ", timeCheckIn='" + timeCheckIn + '\'' +
                '}';
    }
}
