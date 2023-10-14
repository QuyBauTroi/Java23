package Bai6_3;

import Bai6_3.Vehicle;

class Bus extends Vehicle {
    private double speed = 20;
    private double distance;



    public Bus() {

    }

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

    @Override
    public String toString() {
        return "Bus{" +
                "speed=" + speed +
                ", distance=" + distance +
                '}';
    }
}