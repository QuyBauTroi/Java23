package Bai6_3;

public class Train extends Vehicle{
    private double speed = 40;
    private  double distance;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double claculateTimeArrive(){
        return distance/speed;
    }
}
